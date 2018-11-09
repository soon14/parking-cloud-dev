package com.yxytech.parkingcloud.commons.log;

public interface GrpcLogService {

    void log(String log);

    void logWithMDC(String log);
}
