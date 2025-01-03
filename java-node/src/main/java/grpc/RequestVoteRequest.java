// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: raft.proto

package grpc;

/**
 * Protobuf type {@code RequestVoteRequest}
 */
public  final class RequestVoteRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:RequestVoteRequest)
    RequestVoteRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use RequestVoteRequest.newBuilder() to construct.
  private RequestVoteRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private RequestVoteRequest() {
    term_ = 0;
    candidateId_ = 0;
    lastLogIndex_ = 0;
    lastLogTerm_ = 0;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private RequestVoteRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {

            term_ = input.readInt32();
            break;
          }
          case 16: {

            candidateId_ = input.readInt32();
            break;
          }
          case 24: {

            lastLogIndex_ = input.readInt32();
            break;
          }
          case 32: {

            lastLogTerm_ = input.readInt32();
            break;
          }
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return grpc.Raft.internal_static_RequestVoteRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return grpc.Raft.internal_static_RequestVoteRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            grpc.RequestVoteRequest.class, grpc.RequestVoteRequest.Builder.class);
  }

  public static final int TERM_FIELD_NUMBER = 1;
  private int term_;
  /**
   * <code>int32 term = 1;</code>
   */
  public int getTerm() {
    return term_;
  }

  public static final int CANDIDATEID_FIELD_NUMBER = 2;
  private int candidateId_;
  /**
   * <code>int32 candidateId = 2;</code>
   */
  public int getCandidateId() {
    return candidateId_;
  }

  public static final int LASTLOGINDEX_FIELD_NUMBER = 3;
  private int lastLogIndex_;
  /**
   * <code>int32 lastLogIndex = 3;</code>
   */
  public int getLastLogIndex() {
    return lastLogIndex_;
  }

  public static final int LASTLOGTERM_FIELD_NUMBER = 4;
  private int lastLogTerm_;
  /**
   * <code>int32 lastLogTerm = 4;</code>
   */
  public int getLastLogTerm() {
    return lastLogTerm_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (term_ != 0) {
      output.writeInt32(1, term_);
    }
    if (candidateId_ != 0) {
      output.writeInt32(2, candidateId_);
    }
    if (lastLogIndex_ != 0) {
      output.writeInt32(3, lastLogIndex_);
    }
    if (lastLogTerm_ != 0) {
      output.writeInt32(4, lastLogTerm_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (term_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, term_);
    }
    if (candidateId_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, candidateId_);
    }
    if (lastLogIndex_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(3, lastLogIndex_);
    }
    if (lastLogTerm_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(4, lastLogTerm_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof grpc.RequestVoteRequest)) {
      return super.equals(obj);
    }
    grpc.RequestVoteRequest other = (grpc.RequestVoteRequest) obj;

    boolean result = true;
    result = result && (getTerm()
        == other.getTerm());
    result = result && (getCandidateId()
        == other.getCandidateId());
    result = result && (getLastLogIndex()
        == other.getLastLogIndex());
    result = result && (getLastLogTerm()
        == other.getLastLogTerm());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + TERM_FIELD_NUMBER;
    hash = (53 * hash) + getTerm();
    hash = (37 * hash) + CANDIDATEID_FIELD_NUMBER;
    hash = (53 * hash) + getCandidateId();
    hash = (37 * hash) + LASTLOGINDEX_FIELD_NUMBER;
    hash = (53 * hash) + getLastLogIndex();
    hash = (37 * hash) + LASTLOGTERM_FIELD_NUMBER;
    hash = (53 * hash) + getLastLogTerm();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static grpc.RequestVoteRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.RequestVoteRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.RequestVoteRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.RequestVoteRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.RequestVoteRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.RequestVoteRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.RequestVoteRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static grpc.RequestVoteRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static grpc.RequestVoteRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static grpc.RequestVoteRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static grpc.RequestVoteRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static grpc.RequestVoteRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(grpc.RequestVoteRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code RequestVoteRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:RequestVoteRequest)
      grpc.RequestVoteRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return grpc.Raft.internal_static_RequestVoteRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return grpc.Raft.internal_static_RequestVoteRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              grpc.RequestVoteRequest.class, grpc.RequestVoteRequest.Builder.class);
    }

    // Construct using grpc.RequestVoteRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      term_ = 0;

      candidateId_ = 0;

      lastLogIndex_ = 0;

      lastLogTerm_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return grpc.Raft.internal_static_RequestVoteRequest_descriptor;
    }

    @java.lang.Override
    public grpc.RequestVoteRequest getDefaultInstanceForType() {
      return grpc.RequestVoteRequest.getDefaultInstance();
    }

    @java.lang.Override
    public grpc.RequestVoteRequest build() {
      grpc.RequestVoteRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public grpc.RequestVoteRequest buildPartial() {
      grpc.RequestVoteRequest result = new grpc.RequestVoteRequest(this);
      result.term_ = term_;
      result.candidateId_ = candidateId_;
      result.lastLogIndex_ = lastLogIndex_;
      result.lastLogTerm_ = lastLogTerm_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return (Builder) super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof grpc.RequestVoteRequest) {
        return mergeFrom((grpc.RequestVoteRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(grpc.RequestVoteRequest other) {
      if (other == grpc.RequestVoteRequest.getDefaultInstance()) return this;
      if (other.getTerm() != 0) {
        setTerm(other.getTerm());
      }
      if (other.getCandidateId() != 0) {
        setCandidateId(other.getCandidateId());
      }
      if (other.getLastLogIndex() != 0) {
        setLastLogIndex(other.getLastLogIndex());
      }
      if (other.getLastLogTerm() != 0) {
        setLastLogTerm(other.getLastLogTerm());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      grpc.RequestVoteRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (grpc.RequestVoteRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int term_ ;
    /**
     * <code>int32 term = 1;</code>
     */
    public int getTerm() {
      return term_;
    }
    /**
     * <code>int32 term = 1;</code>
     */
    public Builder setTerm(int value) {
      
      term_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 term = 1;</code>
     */
    public Builder clearTerm() {
      
      term_ = 0;
      onChanged();
      return this;
    }

    private int candidateId_ ;
    /**
     * <code>int32 candidateId = 2;</code>
     */
    public int getCandidateId() {
      return candidateId_;
    }
    /**
     * <code>int32 candidateId = 2;</code>
     */
    public Builder setCandidateId(int value) {
      
      candidateId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 candidateId = 2;</code>
     */
    public Builder clearCandidateId() {
      
      candidateId_ = 0;
      onChanged();
      return this;
    }

    private int lastLogIndex_ ;
    /**
     * <code>int32 lastLogIndex = 3;</code>
     */
    public int getLastLogIndex() {
      return lastLogIndex_;
    }
    /**
     * <code>int32 lastLogIndex = 3;</code>
     */
    public Builder setLastLogIndex(int value) {
      
      lastLogIndex_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 lastLogIndex = 3;</code>
     */
    public Builder clearLastLogIndex() {
      
      lastLogIndex_ = 0;
      onChanged();
      return this;
    }

    private int lastLogTerm_ ;
    /**
     * <code>int32 lastLogTerm = 4;</code>
     */
    public int getLastLogTerm() {
      return lastLogTerm_;
    }
    /**
     * <code>int32 lastLogTerm = 4;</code>
     */
    public Builder setLastLogTerm(int value) {
      
      lastLogTerm_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 lastLogTerm = 4;</code>
     */
    public Builder clearLastLogTerm() {
      
      lastLogTerm_ = 0;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:RequestVoteRequest)
  }

  // @@protoc_insertion_point(class_scope:RequestVoteRequest)
  private static final grpc.RequestVoteRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new grpc.RequestVoteRequest();
  }

  public static grpc.RequestVoteRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RequestVoteRequest>
      PARSER = new com.google.protobuf.AbstractParser<RequestVoteRequest>() {
    @java.lang.Override
    public RequestVoteRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new RequestVoteRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<RequestVoteRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<RequestVoteRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public grpc.RequestVoteRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

