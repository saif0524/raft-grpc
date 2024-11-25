import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import grpc.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class RaftNode extends RaftServiceGrpc.RaftServiceImplBase {
    private static final Logger logger = Logger.getLogger(RaftNode.class.getName());
    
    enum NodeState {
        FOLLOWER,
        CANDIDATE,
        LEADER
    }
    
    private final int nodeId;
    private final Map<Integer, String> peerAddresses;
    private NodeState state;
    private int currentTerm;
    private Integer votedFor;
    private List<LogEntry> log;
    private int commitIndex;
    private int lastApplied;
    private long lastHeartbeat;
    private final long electionTimeout;
    private Set<Integer> votesReceived;
    private Integer leaderId;
    private final ScheduledExecutorService scheduler;
    
    public RaftNode(int nodeId, Map<Integer, String> peerAddresses) {
        this.nodeId = nodeId;
        this.peerAddresses = peerAddresses;
        this.state = NodeState.FOLLOWER;
        this.currentTerm = 0;
        this.votedFor = null;
        this.log = new ArrayList<>();
        this.commitIndex = 0;
        this.lastApplied = 0;
        this.electionTimeout = ThreadLocalRandom.current().nextLong(150, 301);
        this.lastHeartbeat = System.currentTimeMillis();
        this.votesReceived = new HashSet<>();
        this.leaderId = null;
        this.scheduler = Executors.newScheduledThreadPool(1);
        
        // Start election timer
        scheduler.scheduleAtFixedRate(this::checkElectionTimeout, 0, 50, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public void requestVote(RequestVoteRequest request, StreamObserver<RequestVoteResponse> responseObserver) {
        logger.info("Process " + nodeId + " receives RPC RequestVote from Process " + request.getCandidateId());
        
        RequestVoteResponse.Builder response = RequestVoteResponse.newBuilder();
        
        if (request.getTerm() < currentTerm) {
            response.setTerm(currentTerm).setVoteGranted(false);
        } else {
            if (request.getTerm() > currentTerm) {
                currentTerm = request.getTerm();
                state = NodeState.FOLLOWER;
                votedFor = null;
            }
            
            if (votedFor == null || votedFor == request.getCandidateId()) {
                int lastLogIdx = log.size() - 1;
                int lastLogTerm = lastLogIdx >= 0 ? log.get(lastLogIdx).getTerm() : 0;
                
                if (request.getLastLogTerm() > lastLogTerm ||
                    (request.getLastLogTerm() == lastLogTerm &&
                     request.getLastLogIndex() >= lastLogIdx)) {
                    votedFor = request.getCandidateId();
                    lastHeartbeat = System.currentTimeMillis();
                    response.setTerm(currentTerm).setVoteGranted(true);
                } else {
                    response.setTerm(currentTerm).setVoteGranted(false);
                }
            } else {
                response.setTerm(currentTerm).setVoteGranted(false);
            }
        }
        
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
    
    @Override
    public void appendEntries(AppendEntriesRequest request, StreamObserver<AppendEntriesResponse> responseObserver) {
        logger.info("Process " + nodeId + " receives RPC AppendEntries from Process " + request.getLeaderId());
        
        AppendEntriesResponse.Builder response = AppendEntriesResponse.newBuilder();
        
        if (request.getTerm() < currentTerm) {
            response.setTerm(currentTerm).setSuccess(false);
        } else {
            lastHeartbeat = System.currentTimeMillis();
            leaderId = request.getLeaderId();
            
            if (request.getTerm() > currentTerm) {
                currentTerm = request.getTerm();
                state = NodeState.FOLLOWER;
                votedFor = null;
            }
            
            response.setTerm(currentTerm).setSuccess(true);
        }
        
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
    
    private void checkElectionTimeout() {
        if (state == NodeState.LEADER) {
            sendHeartbeat();
            return;
        }
        
        long timeSinceLastHeartbeat = System.currentTimeMillis() - lastHeartbeat;
        if (timeSinceLastHeartbeat > electionTimeout) {
            startElection();
        }
    }
    
    private void startElection() {
        state = NodeState.CANDIDATE;
        currentTerm++;
        votedFor = nodeId;
        votesReceived = new HashSet<>(Collections.singleton(nodeId));
        lastHeartbeat = System.currentTimeMillis();
        
        logger.info("Process " + nodeId + " starting election for term " + currentTerm);
        
        for (Map.Entry<Integer, String> peer : peerAddresses.entrySet()) {
            if (peer.getKey() != nodeId) {
                CompletableFuture.runAsync(() -> {
                    try {
                        ManagedChannel channel = ManagedChannelBuilder
                            .forTarget(peer.getValue())
                            .usePlaintext()
                            .build();
                        
                        RaftServiceGrpc.RaftServiceBlockingStub stub = RaftServiceGrpc.newBlockingStub(channel);
                        
                        logger.info("Process " + nodeId + " sends RPC RequestVote to Process " + peer.getKey());
                        
                        RequestVoteRequest request = RequestVoteRequest.newBuilder()
                            .setTerm(currentTerm)
                            .setCandidateId(nodeId)
                            .setLastLogIndex(log.size() - 1)
                            .setLastLogTerm(log.isEmpty() ? 0 : log.get(log.size() - 1).getTerm())
                            .build();
                            
                        RequestVoteResponse response = stub.requestVote(request);
                        
                        if (response.getVoteGranted()) {
                            synchronized (votesReceived) {
                                votesReceived.add(peer.getKey());
                                if (votesReceived.size() > peerAddresses.size() / 2) {
                                    becomeLeader();
                                }
                            }
                        }
                        
                        if (response.getTerm() > currentTerm) {
                            currentTerm = response.getTerm();
                            state = NodeState.FOLLOWER;
                            votedFor = null;
                        }
                        
                        channel.shutdown();
                    } catch (Exception e) {
                        logger.warning("Error sending RequestVote to " + peer.getKey() + ": " + e.getMessage());
                    }
                });
            }
        }
    }
    
    private void becomeLeader() {
        if (state == NodeState.CANDIDATE) {
            state = NodeState.LEADER;
            leaderId = nodeId;
            logger.info("Process " + nodeId + " became leader for term " + currentTerm);
            sendHeartbeat();
        }
    }
    
    private void sendHeartbeat() {
        for (Map.Entry<Integer, String> peer : peerAddresses.entrySet()) {
            if (peer.getKey() != nodeId) {
                CompletableFuture.runAsync(() -> {
                    try {
                        ManagedChannel channel = ManagedChannelBuilder
                            .forTarget(peer.getValue())
                            .usePlaintext()
                            .build();
                        
                        RaftServiceGrpc.RaftServiceBlockingStub stub = RaftServiceGrpc.newBlockingStub(channel);
                        
                        logger.info("Process " + nodeId + " sends RPC AppendEntries to Process " + peer.getKey());
                        
                        AppendEntriesRequest request = AppendEntriesRequest.newBuilder()
                            .setTerm(currentTerm)
                            .setLeaderId(nodeId)
                            .setPrevLogIndex(log.size() - 1)
                            .setPrevLogTerm(log.isEmpty() ? 0 : log.get(log.size() - 1).getTerm())
                            .setLeaderCommit(commitIndex)
                            .build();
                            
                        AppendEntriesResponse response = stub.appendEntries(request);
                        
                        if (response.getTerm() > currentTerm) {
                            currentTerm = response.getTerm();
                            state = NodeState.FOLLOWER;
                            votedFor = null;
                        }
                        
                        channel.shutdown();
                    } catch (Exception e) {
                        logger.warning("Error sending AppendEntries to " + peer.getKey() + ": " + e.getMessage());
                    }
                });
            }
        }
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        int nodeId = Integer.parseInt(System.getenv().getOrDefault("NODE_ID", "0"));
        String serverAddress = System.getenv().getOrDefault("SERVER_ADDRESS", "0.0.0.0:50051");
        
        Map<Integer, String> peerAddresses = new HashMap<>();
        
        // Parse peer addresses from environment variables
        System.getenv().forEach((key, value) -> {
            if (key.startsWith("PEER_")) {
                int peerId = Integer.parseInt(key.split("_")[1]);
                peerAddresses.put(peerId, value);
            }
        });
        
        // Create and start the server
        String[] hostPort = serverAddress.split(":");
        Server server = ServerBuilder.forPort(Integer.parseInt(hostPort[1]))
            .addService(new RaftNode(nodeId, peerAddresses))
            .build();
            
        server.start();
        logger.info("Process " + nodeId + " started on " + serverAddress);
        server.awaitTermination();
    }
}
