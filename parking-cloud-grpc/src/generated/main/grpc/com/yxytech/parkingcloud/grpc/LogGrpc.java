package com.yxytech.parkingcloud.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 * <pre>
 * The greeting service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.7.0)",
    comments = "Source: log.proto")
public final class LogGrpc {

  private LogGrpc() {}

  public static final String SERVICE_NAME = "log.Log";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yxytech.parkingcloud.grpc.LogRequest,
      com.yxytech.parkingcloud.grpc.LogReply> METHOD_LOG =
      io.grpc.MethodDescriptor.<com.yxytech.parkingcloud.grpc.LogRequest, com.yxytech.parkingcloud.grpc.LogReply>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "log.Log", "Log"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yxytech.parkingcloud.grpc.LogRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yxytech.parkingcloud.grpc.LogReply.getDefaultInstance()))
          .setSchemaDescriptor(new LogMethodDescriptorSupplier("Log"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.yxytech.parkingcloud.grpc.MDCLogRequest,
      com.yxytech.parkingcloud.grpc.LogReply> METHOD_MDCLOG =
      io.grpc.MethodDescriptor.<com.yxytech.parkingcloud.grpc.MDCLogRequest, com.yxytech.parkingcloud.grpc.LogReply>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "log.Log", "MDCLog"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yxytech.parkingcloud.grpc.MDCLogRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.yxytech.parkingcloud.grpc.LogReply.getDefaultInstance()))
          .setSchemaDescriptor(new LogMethodDescriptorSupplier("MDCLog"))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LogStub newStub(io.grpc.Channel channel) {
    return new LogStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LogBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new LogBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LogFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new LogFutureStub(channel);
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class LogImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void log(com.yxytech.parkingcloud.grpc.LogRequest request,
        io.grpc.stub.StreamObserver<com.yxytech.parkingcloud.grpc.LogReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LOG, responseObserver);
    }

    /**
     */
    public void mDCLog(com.yxytech.parkingcloud.grpc.MDCLogRequest request,
        io.grpc.stub.StreamObserver<com.yxytech.parkingcloud.grpc.LogReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MDCLOG, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_LOG,
            asyncUnaryCall(
              new MethodHandlers<
                com.yxytech.parkingcloud.grpc.LogRequest,
                com.yxytech.parkingcloud.grpc.LogReply>(
                  this, METHODID_LOG)))
          .addMethod(
            METHOD_MDCLOG,
            asyncUnaryCall(
              new MethodHandlers<
                com.yxytech.parkingcloud.grpc.MDCLogRequest,
                com.yxytech.parkingcloud.grpc.LogReply>(
                  this, METHODID_MDCLOG)))
          .build();
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class LogStub extends io.grpc.stub.AbstractStub<LogStub> {
    private LogStub(io.grpc.Channel channel) {
      super(channel);
    }

    private LogStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new LogStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void log(com.yxytech.parkingcloud.grpc.LogRequest request,
        io.grpc.stub.StreamObserver<com.yxytech.parkingcloud.grpc.LogReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_LOG, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void mDCLog(com.yxytech.parkingcloud.grpc.MDCLogRequest request,
        io.grpc.stub.StreamObserver<com.yxytech.parkingcloud.grpc.LogReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_MDCLOG, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class LogBlockingStub extends io.grpc.stub.AbstractStub<LogBlockingStub> {
    private LogBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private LogBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new LogBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.yxytech.parkingcloud.grpc.LogReply log(com.yxytech.parkingcloud.grpc.LogRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_LOG, getCallOptions(), request);
    }

    /**
     */
    public com.yxytech.parkingcloud.grpc.LogReply mDCLog(com.yxytech.parkingcloud.grpc.MDCLogRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MDCLOG, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class LogFutureStub extends io.grpc.stub.AbstractStub<LogFutureStub> {
    private LogFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private LogFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new LogFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yxytech.parkingcloud.grpc.LogReply> log(
        com.yxytech.parkingcloud.grpc.LogRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_LOG, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yxytech.parkingcloud.grpc.LogReply> mDCLog(
        com.yxytech.parkingcloud.grpc.MDCLogRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MDCLOG, getCallOptions()), request);
    }
  }

  private static final int METHODID_LOG = 0;
  private static final int METHODID_MDCLOG = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LogImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LogImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOG:
          serviceImpl.log((com.yxytech.parkingcloud.grpc.LogRequest) request,
              (io.grpc.stub.StreamObserver<com.yxytech.parkingcloud.grpc.LogReply>) responseObserver);
          break;
        case METHODID_MDCLOG:
          serviceImpl.mDCLog((com.yxytech.parkingcloud.grpc.MDCLogRequest) request,
              (io.grpc.stub.StreamObserver<com.yxytech.parkingcloud.grpc.LogReply>) responseObserver);
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

  private static abstract class LogBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LogBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yxytech.parkingcloud.grpc.LogProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Log");
    }
  }

  private static final class LogFileDescriptorSupplier
      extends LogBaseDescriptorSupplier {
    LogFileDescriptorSupplier() {}
  }

  private static final class LogMethodDescriptorSupplier
      extends LogBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LogMethodDescriptorSupplier(String methodName) {
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
      synchronized (LogGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LogFileDescriptorSupplier())
              .addMethod(METHOD_LOG)
              .addMethod(METHOD_MDCLOG)
              .build();
        }
      }
    }
    return result;
  }
}
