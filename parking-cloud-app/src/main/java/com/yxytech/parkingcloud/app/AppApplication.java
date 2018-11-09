package com.yxytech.parkingcloud.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@ComponentScan(basePackages = {
        "com.yxytech.parkingcloud.app",
        "com.yxytech.parkingcloud.core",
        "com.yxytech.parkingcloud.security"
})
@MapperScan("com.yxytech.parkingcloud.core.mapper")
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class AppApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppApplication.class, args);
    }

}
