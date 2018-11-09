package com.yxytech.parkingcloud.log.service;

import com.yxytech.parkingcloud.grpc.LogGrpc;
import com.yxytech.parkingcloud.grpc.LogReply;
import com.yxytech.parkingcloud.grpc.LogRequest;
import com.yxytech.parkingcloud.grpc.MDCLogRequest;
import com.yxytech.parkingcloud.log.Markers;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;


@GrpcService(LogGrpc.class)
public class LogGrpcService extends LogGrpc.LogImplBase {

    private final static Logger logger = LogManager.getLogger(LogGrpcService.class);

    @Override
    public void log(LogRequest request, StreamObserver<LogReply> responseObserver) {
        logger.info(Markers.DB, request.getMessage());

        responseObserver.onNext(null);
        responseObserver.onCompleted();
    }

    @Override
    public void mDCLog(MDCLogRequest request, StreamObserver<LogReply> responseObserver) {
        MDC.setContextMap(request.getMdcMap());
        logger.info(Markers.DB, "");
        responseObserver.onNext(null);
        responseObserver.onCompleted();
    }
}
