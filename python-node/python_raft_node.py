import grpc
import random
import threading
import time
from concurrent import futures
import raft_pb2
import raft_pb2_grpc

HEARTBEAT_INTERVAL = 0.1  # 100 ms in seconds
ELECTION_TIMEOUT_MIN = 0.15  # 150 ms in seconds
ELECTION_TIMEOUT_MAX = 0.3   # 300 ms in seconds

class RaftNode(raft_pb2_grpc.RaftServicer):
    def __init__(self, node_id, nodes):
        self.node_id = node_id
        self.nodes = nodes
        self.term = 0
        self.role = 'follower'
        self.voted_for = None
        self.log = []
        self.leader_id = None
        self.last_heartbeat = time.time()
        self.election_timeout = random.uniform(ELECTION_TIMEOUT_MIN, ELECTION_TIMEOUT_MAX)
        
        # Start background threads
        threading.Thread(target=self.monitor_heartbeat).start()
        threading.Thread(target=self.check_election_timeout).start()

    def monitor_heartbeat(self):
        while True:
            if self.role == 'leader':
                self.send_heartbeats()
            time.sleep(HEARTBEAT_INTERVAL)

    def send_heartbeats(self):
        print(f"Node {self.node_id} (Leader) sends heartbeats.")
        for node_address in self.nodes:
            if node_address != self.node_id:
                try:
                    # gRPC call to send heartbeat (AppendEntries RPC without new entries)
                    with grpc.insecure_channel(f'localhost:{node_address}') as channel:
                        stub = raft_pb2_grpc.RaftStub(channel)
                        request = raft_pb2.AppendEntriesRequest(term=self.term, leader_id=str(self.node_id), entries=[])
                        response = stub.AppendEntries(request)
                        print(f"Node {self.node_id} sent heartbeat to Node {node_address}")
                except grpc.RpcError:
                    print(f"Node {self.node_id} failed to reach Node {node_address}")

    def check_election_timeout(self):
        while True:
            time_since_heartbeat = time.time() - self.last_heartbeat
            if self.role == 'follower' and time_since_heartbeat > self.election_timeout:
                self.start_election()
            time.sleep(0.05)

    def start_election(self):
        self.role = 'candidate'
        self.term += 1
        self.voted_for = self.node_id
        self.last_heartbeat = time.time()
        self.election_timeout = random.uniform(ELECTION_TIMEOUT_MIN, ELECTION_TIMEOUT_MAX)
        print(f"Node {self.node_id} starts election in term {self.term}")

        # Request votes from other nodes
        for node_address in self.nodes:
            if node_address != self.node_id:
                try:
                    with grpc.insecure_channel(f'localhost:{node_address}') as channel:
                        stub = raft_pb2_grpc.RaftStub(channel)
                        vote_request = raft_pb2.VoteRequest(term=self.term, candidate_id=str(self.node_id))
                        response = stub.RequestVote(vote_request)
                        print(f"Node {self.node_id} received vote response from Node {node_address}")
                except grpc.RpcError:
                    print(f"Node {self.node_id} failed to request vote from Node {node_address}")

    def AppendEntries(self, request, context):
        if request.term >= self.term:
            self.term = request.term
            self.role = 'follower'
            self.leader_id = request.leader_id
            self.last_heartbeat = time.time()  # Reset heartbeat timer on receiving heartbeat
            print(f"Node {self.node_id} received heartbeat from Leader {request.leader_id}")
            return raft_pb2.AppendEntriesResponse(success=True, term=self.term)
        return raft_pb2.AppendEntriesResponse(success=False, term=self.term)

    def RequestVote(self, request, context):
        if request.term > self.term:
            self.term = request.term
            self.voted_for = request.candidate_id
            print(f"Node {self.node_id} votes for Node {request.candidate_id} in term {request.term}")
            return raft_pb2.VoteResponse(vote_granted=True, term=self.term)
        return raft_pb2.VoteResponse(vote_granted=False, term=self.term)

def serve(node_id, nodes):
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    raft_pb2_grpc.add_RaftServicer_to_server(RaftNode(node_id, nodes), server)
    server.add_insecure_port(f'[::]:500{node_id}')
    server.start()
    print(f'Node {node_id} started on port 500{node_id}.')
    server.wait_for_termination()
