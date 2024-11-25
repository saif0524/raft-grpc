package grpc;

import grpc.RaftNodeProto.*;
import grpc.RaftGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RaftNode extends RaftGrpc.RaftImplBase {
    private final int nodeId;
    private int term = 0;
    private String role = "follower";
    private String votedFor = null;
    private final List<LogEntry> log = new ArrayList<>();
    private String leaderId = null;
    private long lastHeartbeat = System.currentTimeMillis();
    private final long electionTimeout;

    private static final long HEARTBEAT_INTERVAL = 100; // 100 ms
    private static final long ELECTION_TIMEOUT_MIN = 150; // 150 ms
    private static final long ELECTION_TIMEOUT_MAX = 300; // 300 ms

    public RaftNode(int nodeId) {
        this.nodeId = nodeId;
        Random random = new Random();
        this.electionTimeout = ELECTION_TIMEOUT_MIN + random.nextInt((int)(ELECTION_TIMEOUT_MAX - ELECTION_TIMEOUT_MIN));

        // Start background threads
        startHeartbeatMonitor();
        startElectionMonitor();
    }

    private void startHeartbeatMonitor() {
        new Thread(() -> {
            while (true) {
                if ("leader".equals(role)) {
                    sendHeartbeats();
                }
                try {
                    Thread.sleep(HEARTBEAT_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void startElectionMonitor() {
        new Thread(() -> {
            while (true) {
                long timeSinceLastHeartbeat = System.currentTimeMillis() - lastHeartbeat;
                if ("follower".equals(role) && timeSinceLastHeartbeat > electionTimeout) {
                    startElection();
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendHeartbeats() {
        System.out.println("Node " + nodeId + " (Leader) sends heartbeats.");
        // Logic to send heartbeat to other nodes in the cluster
        // Here you'd establish a connection to each node and call AppendEntries RPC
    }

    private void startElection() {
        role = "candidate";
        term += 1;
        votedFor = Integer.toString(nodeId);
        lastHeartbeat = System.currentTimeMillis();
        System.out.println("Node " + nodeId + " starts election in term " + term);

        // Logic to request votes from other nodes
    }

    @Override
    public void requestVote(VoteRequest request, StreamObserver<VoteResponse> responseObserver) {
        VoteResponse.Builder response = VoteResponse.newBuilder();
        if (request.getTerm() > term) {
            term = request.getTerm();
            votedFor = request.getCandidateId();
            response.setVoteGranted(true).setTerm(term);
            System.out.println("Node " + nodeId + " votes for Node " + request.getCandidateId() + " in term " + request.getTerm());
        } else {
            response.setVoteGranted(false).setTerm(term);
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void appendEntries(AppendEntriesRequest request, StreamObserver<AppendEntriesResponse> responseObserver) {
        AppendEntriesResponse.Builder response = AppendEntriesResponse.newBuilder();
        if (request.getTerm() >= term) {
            term = request.getTerm();
            role = "follower";
            leaderId = request.getLeaderId();
            lastHeartbeat = System.currentTimeMillis();  // Reset heartbeat timer on receiving heartbeat
            log.addAll(request.getEntriesList());  // Append new entries to the log
            response.setSuccess(true).setTerm(term);
            System.out.println("Node " + nodeId + " received heartbeat from Leader " + request.getLeaderId());
        } else {
            response.setSuccess(false).setTerm(term);
        }
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 0) {
            System.out.println("Error: Please provide a nodeId as an argument.");
            return;
        }
        int nodeId = Integer.parseInt(args[0]);
        int port = 5000 + nodeId;

        RaftNode raftNode = new RaftNode(nodeId);
        Server server = ServerBuilder.forPort(port)
                .addService(raftNode)
                .build()
                .start();

        System.out.println("RaftNode " + nodeId + " started, listening on " + port);
        server.awaitTermination();
    }
}
