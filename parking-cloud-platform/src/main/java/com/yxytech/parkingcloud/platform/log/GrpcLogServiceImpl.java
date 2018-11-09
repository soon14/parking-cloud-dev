package com.yxytech.parkingcloud.platform.log;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.yxytech.parkingcloud.commons.log.GrpcLogService;
import com.yxytech.parkingcloud.grpc.LogGrpc;
import com.yxytech.parkingcloud.grpc.LogReply;
import com.yxytech.parkingcloud.grpc.MDCLogRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.logging.log4j.LogManager;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class GrpcLogServiceImpl implements GrpcLogService {

    private static ManagedChannel channel;

    @Value("${grpc.log.server.host}")
    private String grpcLogServerHost;

    @Value("${grpc.log.server.port}")
    private Integer grpcLogServerPort;

    @PostConstruct
    public ManagedChannel initChannel() {
        channel = ManagedChannelBuilder.forAddress(grpcLogServerHost, grpcLogServerPort)
                .usePlaintext(true).build();

        return channel;
    }

    @Override
    public void log(String log) {
//        LogGrpc.LogFutureStub stub = LogGrpc.newFutureStub(channel);
//        ListenableFuture<LogReply> f = stub.log(LogRequest.newBuilder().build().newBuilder().setMessage(log).build());
//
//        Futures.addCallback(f, new FutureCallback<LogReply>() {
//            @Override
//            public void onSuccess(@Nullable LogReply result) {
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                LogManager.getLogger("root").error("send grpc log error! [" + log + "]");
//            }
//        });
        LogGrpc.LogFutureStub stub = LogGrpc.newFutureStub(channel);
        Map<String, String> mdc = MDC.getCopyOfContextMap();
        mdc.forEach((k, v) -> {
            if (v == null) {
                mdc.put(k, "");
            }
        });
        MDCLogRequest request = MDCLogRequest.newBuilder().putAllMdc(mdc).build();
        ListenableFuture<LogReply> f = stub.mDCLog(request);

        Futures.addCallback(f, new FutureCallback<LogReply>() {
            @Override
            public void onSuccess(@Nullable LogReply result) {
            }

            @Override
            public void onFailure(Throwable t) {
                LogManager.getLogger("root").error("send grpc log error! [" + log + "]");
            }
        });
    }

    @Override
    public void logWithMDC(String log) {
        LogGrpc.LogFutureStub stub = LogGrpc.newFutureStub(channel);
        MDCLogRequest request = MDCLogRequest.newBuilder().build();
        request.getMdcMap().putAll(MDC.getCopyOfContextMap());
        ListenableFuture<LogReply> f = stub.mDCLog(request);

        Futures.addCallback(f, new FutureCallback<LogReply>() {
            @Override
            public void onSuccess(@Nullable LogReply result) {
            }

            @Override
            public void onFailure(Throwable t) {
                LogManager.getLogger("root").error("send grpc log error! [" + log + "]");
            }
        });
    }
}
