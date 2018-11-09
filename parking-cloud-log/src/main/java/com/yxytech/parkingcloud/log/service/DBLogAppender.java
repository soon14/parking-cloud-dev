package com.yxytech.parkingcloud.log.service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.JdbcAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.filter.MarkerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DBLogAppender {

    @Autowired
    private LogConnectionSource connect;

    @Value("${logs.table-name}")
    private String logsTableName;

    @PostConstruct
    public void init(){
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();

        ColumnConfig[] cc = {
                ColumnConfig.createColumnConfig(config, "level", "%level", null, null, "false", null),
                ColumnConfig.createColumnConfig(config, "remote_ip", "%X{remoteIp}", null, null, "false", null),
                ColumnConfig.createColumnConfig(config, "user_id", "%X{userId}", null, null, "false", null),
                ColumnConfig.createColumnConfig(config, "uri", "%X{uri}", null, null, "false", null),
                ColumnConfig.createColumnConfig(config, "ret_code", "%X{retCode}", null, null, "false", null),
                ColumnConfig.createColumnConfig(config, "executed_time", "%X{executedTime}", null, null, "false", null),
                ColumnConfig.createColumnConfig(config, "query_string", "%X{queryString}", null, null, "false", null),
                ColumnConfig.createColumnConfig(config, "params", "%X{params}", null, null, "false", null),
                ColumnConfig.createColumnConfig(config, "ret", "%X{ret}", null, null, "false", null),
                ColumnConfig.createColumnConfig(config, "message", "%message", null, null, "false", null),
                ColumnConfig.createColumnConfig(config, "time", null, null, "true", null, null)
        } ;

        //配置Marker过滤器(标记过滤器)
        MarkerFilter filter = MarkerFilter.createFilter("dblog", Filter.Result.ACCEPT, Filter.Result.DENY);

        Appender appender = JdbcAppender.createAppender("databaseAppender", "true", filter, connect, "0", logsTableName, cc);
        config.addAppender(appender);

        config.getLoggerConfig(LogGrpcService.class.toString()).addAppender(appender, Level.INFO, null);

        appender.start();
        ctx.updateLoggers(config);
    }
}
