package com.yxytech.parkingcloud.log.service;

import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class LogConnectionSource implements ConnectionSource {

    @Value("${spring.datasource.driver-class-name}")
    private String driverName;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

    private DataSource dataSource;

    @Bean
    @PostConstruct
    public DataSource dataSource() {
        DataSourceBuilder factory = DataSourceBuilder.create(getClass().getClassLoader())
                .driverClassName(driverName)
                .url(url)
                .username(username)
                .password(password);
        dataSource = factory.build();

        return dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
