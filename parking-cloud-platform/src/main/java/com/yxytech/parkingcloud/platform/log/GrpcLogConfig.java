package com.yxytech.parkingcloud.platform.log;


import com.yxytech.parkingcloud.commons.log.GrpcLogAppender;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.MarkerFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GrpcLogConfig {


    @Autowired
    private GrpcLogServiceImpl grpcLogService;

    @PostConstruct
    public void init() {

        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();

        //配置Marker过滤器(标记过滤器)
        MarkerFilter filter = MarkerFilter.createFilter(LogMarkers.GrpcMarker.getName(), Filter.Result.ACCEPT, Filter.Result.DENY);

        PatternLayout layout = PatternLayout.newBuilder().withPattern(
                "%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level >>>ip: %X{remoteIp} userId: %X{userId} -- %X{method}  %X{uri} %X{retCode} %X{executedTime}ms --" +
                "queryString: %X{queryString} params: %X{params} ret: %X{ret} %n").build();
        Appender appender = new GrpcLogAppender("grpcLogAppender", filter, layout,  grpcLogService);
        config.addAppender(appender);

        LoggerConfig loggerConfig = new LoggerConfig("com.yxytech.parkingcloud.platform", Level.INFO, false);
        loggerConfig.addAppender(appender, Level.INFO, null);

        config.addLogger("com.yxytech.parkingcloud.platform", loggerConfig);

        appender.start();
        ctx.updateLoggers(config);

    }
}
