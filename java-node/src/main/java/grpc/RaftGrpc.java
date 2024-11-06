package grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.67.1)",
    comments = "Source: raft.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class RaftGrpc {

  private RaftGrpc() {}

  public static final java.lang.String SERVICE_NAME = "Raft";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.RaftNodeProto.VoteRequest,
      grpc.RaftNodeProto.VoteResponse> getRequestVoteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RequestVote",
      requestType = grpc.RaftNodeProto.VoteRequest.class,
      responseType = grpc.RaftNodeProto.VoteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.RaftNodeProto.VoteRequest,
      grpc.RaftNodeProto.VoteResponse> getRequestVoteMethod() {
    io.grpc.MethodDescriptor<grpc.RaftNodeProto.VoteRequest, grpc.RaftNodeProto.VoteResponse> getRequestVoteMethod;
    if ((getRequestVoteMethod = RaftGrpc.getRequestVoteMethod) == null) {
      synchronized (RaftGrpc.class) {
        if ((getRequestVoteMethod = RaftGrpc.getRequestVoteMethod) == null) {
          RaftGrpc.getRequestVoteMethod = getRequestVoteMethod =
              io.grpc.MethodDescriptor.<grpc.RaftNodeProto.VoteRequest, grpc.RaftNodeProto.VoteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RequestVote"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.RaftNodeProto.VoteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.RaftNodeProto.VoteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RaftMethodDescriptorSupplier("RequestVote"))
              .build();
        }
      }
    }
    return getRequestVoteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.RaftNodeProto.AppendEntriesRequest,
      grpc.RaftNodeProto.AppendEntriesResponse> getAppendEntriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AppendEntries",
      requestType = grpc.RaftNodeProto.AppendEntriesRequest.class,
      responseType = grpc.RaftNodeProto.AppendEntriesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.RaftNodeProto.AppendEntriesRequest,
      grpc.RaftNodeProto.AppendEntriesResponse> getAppendEntriesMethod() {
    io.grpc.MethodDescriptor<grpc.RaftNodeProto.AppendEntriesRequest, grpc.RaftNodeProto.AppendEntriesResponse> getAppendEntriesMethod;
    if ((getAppendEntriesMethod = RaftGrpc.getAppendEntriesMethod) == null) {
      synchronized (RaftGrpc.class) {
        if ((getAppendEntriesMethod = RaftGrpc.getAppendEntriesMethod) == null) {
          RaftGrpc.getAppendEntriesMethod = getAppendEntriesMethod =
              io.grpc.MethodDescriptor.<grpc.RaftNodeProto.AppendEntriesRequest, grpc.RaftNodeProto.AppendEntriesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AppendEntries"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.RaftNodeProto.AppendEntriesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.RaftNodeProto.AppendEntriesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RaftMethodDescriptorSupplier("AppendEntries"))
              .build();
        }
      }
    }
    return getAppendEntriesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RaftStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RaftStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RaftStub>() {
        @java.lang.Override
        public RaftStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RaftStub(channel, callOptions);
        }
      };
    return RaftStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RaftBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RaftBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RaftBlockingStub>() {
        @java.lang.Override
        public RaftBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RaftBlockingStub(channel, callOptions);
        }
      };
    return RaftBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RaftFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RaftFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RaftFutureStub>() {
        @java.lang.Override
        public RaftFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RaftFutureStub(channel, callOptions);
        }
      };
    return RaftFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * RPC for requesting votes in an election
     * </pre>
     */
    default void requestVote(grpc.RaftNodeProto.VoteRequest request,
        io.grpc.stub.StreamObserver<grpc.RaftNodeProto.VoteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRequestVoteMethod(), responseObserver);
    }

    /**
     * <pre>
     * RPC for sending heartbeats and log entries
     * </pre>
     */
    default void appendEntries(grpc.RaftNodeProto.AppendEntriesRequest request,
        io.grpc.stub.StreamObserver<grpc.RaftNodeProto.AppendEntriesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAppendEntriesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Raft.
   */
  public static abstract class RaftImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return RaftGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Raft.
   */
  public static final class RaftStub
      extends io.grpc.stub.AbstractAsyncStub<RaftStub> {
    private RaftStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RaftStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RaftStub(channel, callOptions);
    }

    /**
     * <pre>
     * RPC for requesting votes in an election
     * </pre>
     */
    public void requestVote(grpc.RaftNodeProto.VoteRequest request,
        io.grpc.stub.StreamObserver<grpc.RaftNodeProto.VoteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRequestVoteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * RPC for sending heartbeats and log entries
     * </pre>
     */
    public void appendEntries(grpc.RaftNodeProto.AppendEntriesRequest request,
        io.grpc.stub.StreamObserver<grpc.RaftNodeProto.AppendEntriesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAppendEntriesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Raft.
   */
  public static final class RaftBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<RaftBlockingStub> {
    private RaftBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RaftBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RaftBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * RPC for requesting votes in an election
     * </pre>
     */
    public grpc.RaftNodeProto.VoteResponse requestVote(grpc.RaftNodeProto.VoteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRequestVoteMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * RPC for sending heartbeats and log entries
     * </pre>
     */
    public grpc.RaftNodeProto.AppendEntriesResponse appendEntries(grpc.RaftNodeProto.AppendEntriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAppendEntriesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Raft.
   */
  public static final class RaftFutureStub
      extends io.grpc.stub.AbstractFutureStub<RaftFutureStub> {
    private RaftFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RaftFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RaftFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * RPC for requesting votes in an election
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.RaftNodeProto.VoteResponse> requestVote(
        grpc.RaftNodeProto.VoteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRequestVoteMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * RPC for sending heartbeats and log entries
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.RaftNodeProto.AppendEntriesResponse> appendEntries(
        grpc.RaftNodeProto.AppendEntriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAppendEntriesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQUEST_VOTE = 0;
  private static final int METHODID_APPEND_ENTRIES = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQUEST_VOTE:
          serviceImpl.requestVote((grpc.RaftNodeProto.VoteRequest) request,
              (io.grpc.stub.StreamObserver<grpc.RaftNodeProto.VoteResponse>) responseObserver);
          break;
        case METHODID_APPEND_ENTRIES:
          serviceImpl.appendEntries((grpc.RaftNodeProto.AppendEntriesRequest) request,
              (io.grpc.stub.StreamObserver<grpc.RaftNodeProto.AppendEntriesResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRequestVoteMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.RaftNodeProto.VoteRequest,
              grpc.RaftNodeProto.VoteResponse>(
                service, METHODID_REQUEST_VOTE)))
        .addMethod(
          getAppendEntriesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.RaftNodeProto.AppendEntriesRequest,
              grpc.RaftNodeProto.AppendEntriesResponse>(
                service, METHODID_APPEND_ENTRIES)))
        .build();
  }

  private static abstract class RaftBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RaftBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.RaftNodeProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Raft");
    }
  }

  private static final class RaftFileDescriptorSupplier
      extends RaftBaseDescriptorSupplier {
    RaftFileDescriptorSupplier() {}
  }

  private static final class RaftMethodDescriptorSupplier
      extends RaftBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    RaftMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (RaftGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RaftFileDescriptorSupplier())
              .addMethod(getRequestVoteMethod())
              .addMethod(getAppendEntriesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
