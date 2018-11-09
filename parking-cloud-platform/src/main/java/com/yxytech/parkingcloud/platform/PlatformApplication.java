package com.yxytech.parkingcloud.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@ComponentScan(basePackages = {
        "com.yxytech.parkingcloud.platform",
        "com.yxytech.parkingcloud.core",
        "com.yxytech.parkingcloud.security"
}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value= AccessCapture.class)
})
@MapperScan("com.yxytech.parkingcloud.core.mapper")
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class PlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }

}
