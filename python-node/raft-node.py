import grpc
import raft_pb2
import raft_pb2_grpc
from concurrent import futures
import random
import time
import threading
import logging
from enum import Enum

logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(message)s')

class NodeState(Enum):
    FOLLOWER = 1
    CANDIDATE = 2
    LEADER = 3

class RaftNode(raft_pb2_grpc.RaftServiceServicer):
    def __init__(self, node_id, peer_addresses):
        self.id = node_id
        self.peer_addresses = peer_addresses
        self.state = NodeState.FOLLOWER
        self.current_term = 0
        self.voted_for = None
        self.log = []
        self.commit_index = 0
        self.last_applied = 0
        self.election_timeout = random.randint(150, 300) / 1000  # Convert to seconds
        self.last_heartbeat = time.time()
        self.votes_received = set()
        self.leader_id = None
        
        # Start election timer
        self.election_timer = threading.Thread(target=self._run_election_timer)
        self.election_timer.daemon = True
        self.election_timer.start()

    def RequestVote(self, request, context):
        logging.info(f"Process {self.id} receives RPC RequestVote from Process {request.candidateId}")
        
        if request.term < self.current_term:
            return raft_pb2.RequestVoteResponse(term=self.current_term, voteGranted=False)
        
        if request.term > self.current_term:
            self.current_term = request.term
            self.state = NodeState.FOLLOWER
            self.voted_for = None
        
        if (self.voted_for is None or self.voted_for == request.candidateId):
            # Check if candidate's log is at least as up-to-date as receiver's log
            last_log_idx = len(self.log) - 1
            last_log_term = self.log[last_log_idx].term if self.log else 0
            
            if (request.lastLogTerm > last_log_term or 
                (request.lastLogTerm == last_log_term and 
                 request.lastLogIndex >= last_log_idx)):
                self.voted_for = request.candidateId
                self.last_heartbeat = time.time()
                return raft_pb2.RequestVoteResponse(term=self.current_term, voteGranted=True)
        
        return raft_pb2.RequestVoteResponse(term=self.current_term, voteGranted=False)

    def AppendEntries(self, request, context):
        logging.info(f"Process {self.id} receives RPC AppendEntries from Process {request.leaderId}")
        
        if request.term < self.current_term:
            return raft_pb2.AppendEntriesResponse(term=self.current_term, success=False)
        
        self.last_heartbeat = time.time()
        self.leader_id = request.leaderId
        
        if request.term > self.current_term:
            self.current_term = request.term
            self.state = NodeState.FOLLOWER
            self.voted_for = None
        
        # Reset election timeout since we received valid AppendEntries
        self.last_heartbeat = time.time()
        
        return raft_pb2.AppendEntriesResponse(term=self.current_term, success=True)

    def _run_election_timer(self):
        while True:
            time.sleep(0.05)  # Check every 50ms
            
            if self.state == NodeState.LEADER:
                self._send_heartbeat()
                continue
                
            time_since_last_heartbeat = time.time() - self.last_heartbeat
            
            if time_since_last_heartbeat > self.election_timeout:
                self._start_election()

    def _start_election(self):
        self.state = NodeState.CANDIDATE
        self.current_term += 1
        self.voted_for = self.id
        self.votes_received = {self.id}  # Vote for self
        self.last_heartbeat = time.time()
        
        logging.info(f"Process {self.id} starting election for term {self.current_term}")
        
        # Send RequestVote RPCs to all peers
        for peer_id, peer_addr in self.peer_addresses.items():
            if peer_id != self.id:
                try:
                    with grpc.insecure_channel(peer_addr) as channel:
                        stub = raft_pb2_grpc.RaftServiceStub(channel)
                        logging.info(f"Process {self.id} sends RPC RequestVote to Process {peer_id}")
                        response = stub.RequestVote(raft_pb2.RequestVoteRequest(
                            term=self.current_term,
                            candidateId=self.id,
                            lastLogIndex=len(self.log) - 1,
                            lastLogTerm=self.log[-1].term if self.log else 0
                        ))
                        
                        if response.voteGranted:
                            self.votes_received.add(peer_id)
                            
                        if response.term > self.current_term:
                            self.current_term = response.term
                            self.state = NodeState.FOLLOWER
                            self.voted_for = None
                            return
                except Exception as e:
                    logging.error(f"Error sending RequestVote to {peer_id}: {e}")
        
        # Check if we won the election
        if len(self.votes_received) > len(self.peer_addresses) // 2:
            self.state = NodeState.LEADER
            logging.info(f"Process {self.id} became leader for term {self.current_term}")
            self._send_heartbeat()
        else:
            self.state = NodeState.FOLLOWER

    def _send_heartbeat(self):
        for peer_id, peer_addr in self.peer_addresses.items():
            if peer_id != self.id:
                try:
                    with grpc.insecure_channel(peer_addr) as channel:
                        stub = raft_pb2_grpc.RaftServiceStub(channel)
                        logging.info(f"Process {self.id} sends RPC AppendEntries to Process {peer_id}")
                        response = stub.AppendEntries(raft_pb2.AppendEntriesRequest(
                            term=self.current_term,
                            leaderId=self.id,
                            prevLogIndex=len(self.log) - 1,
                            prevLogTerm=self.log[-1].term if self.log else 0,
                            entries=[],
                            leaderCommit=self.commit_index
                        ))
                        
                        if response.term > self.current_term:
                            self.current_term = response.term
                            self.state = NodeState.FOLLOWER
                            self.voted_for = None
                            return
                except Exception as e:
                    logging.error(f"Error sending heartbeat to {peer_id}: {e}")

def serve(node_id, server_address, peer_addresses):
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    raft_node = RaftNode(node_id, peer_addresses)
    raft_pb2_grpc.add_RaftServiceServicer_to_server(raft_node, server)
    server.add_insecure_port(server_address)
    server.start()
    logging.info(f"Process {node_id} started on {server_address}")
    server.wait_for_termination()

if __name__ == '__main__':
    import os
    node_id = int(os.environ.get('NODE_ID', 0))
    server_address = os.environ.get('SERVER_ADDRESS', '[::]:50051')
    peer_addresses = {}  # Will be populated from environment variables
    
    # Parse peer addresses from environment variables
    for env_var in os.environ:
        if env_var.startswith('PEER_'):
            peer_id = int(env_var.split('_')[1])
            peer_addresses[peer_id] = os.environ[env_var]
    
    serve(node_id, server_address, peer_addresses)
