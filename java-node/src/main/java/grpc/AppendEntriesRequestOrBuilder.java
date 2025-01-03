// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: raft.proto

package grpc;

public interface AppendEntriesRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:AppendEntriesRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 term = 1;</code>
   */
  int getTerm();

  /**
   * <code>int32 leaderId = 2;</code>
   */
  int getLeaderId();

  /**
   * <code>int32 prevLogIndex = 3;</code>
   */
  int getPrevLogIndex();

  /**
   * <code>int32 prevLogTerm = 4;</code>
   */
  int getPrevLogTerm();

  /**
   * <code>repeated .LogEntry entries = 5;</code>
   */
  java.util.List<grpc.LogEntry> 
      getEntriesList();
  /**
   * <code>repeated .LogEntry entries = 5;</code>
   */
  grpc.LogEntry getEntries(int index);
  /**
   * <code>repeated .LogEntry entries = 5;</code>
   */
  int getEntriesCount();
  /**
   * <code>repeated .LogEntry entries = 5;</code>
   */
  java.util.List<? extends grpc.LogEntryOrBuilder> 
      getEntriesOrBuilderList();
  /**
   * <code>repeated .LogEntry entries = 5;</code>
   */
  grpc.LogEntryOrBuilder getEntriesOrBuilder(
      int index);

  /**
   * <code>int32 leaderCommit = 6;</code>
   */
  int getLeaderCommit();
}
