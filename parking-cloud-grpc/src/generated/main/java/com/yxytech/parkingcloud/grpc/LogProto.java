// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: log.proto

package com.yxytech.parkingcloud.grpc;

public final class LogProto {
  private LogProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_log_LogRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_log_LogRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_log_LogReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_log_LogReply_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_log_MDCLogRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_log_MDCLogRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_log_MDCLogRequest_MdcEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_log_MDCLogRequest_MdcEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\tlog.proto\022\003log\"\035\n\nLogRequest\022\017\n\007messag" +
      "e\030\001 \001(\t\"\033\n\010LogReply\022\017\n\007message\030\001 \001(\t\"e\n\r" +
      "MDCLogRequest\022(\n\003mdc\030\001 \003(\0132\033.log.MDCLogR" +
      "equest.MdcEntry\032*\n\010MdcEntry\022\013\n\003key\030\001 \001(\t" +
      "\022\r\n\005value\030\002 \001(\t:\0028\0012]\n\003Log\022\'\n\003Log\022\017.log." +
      "LogRequest\032\r.log.LogReply\"\000\022-\n\006MDCLog\022\022." +
      "log.MDCLogRequest\032\r.log.LogReply\"\000B+\n\035co" +
      "m.yxytech.parkingcloud.grpcB\010LogProtoP\001b" +
      "\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_log_LogRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_log_LogRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_log_LogRequest_descriptor,
        new java.lang.String[] { "Message", });
    internal_static_log_LogReply_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_log_LogReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_log_LogReply_descriptor,
        new java.lang.String[] { "Message", });
    internal_static_log_MDCLogRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_log_MDCLogRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_log_MDCLogRequest_descriptor,
        new java.lang.String[] { "Mdc", });
    internal_static_log_MDCLogRequest_MdcEntry_descriptor =
      internal_static_log_MDCLogRequest_descriptor.getNestedTypes().get(0);
    internal_static_log_MDCLogRequest_MdcEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_log_MDCLogRequest_MdcEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
