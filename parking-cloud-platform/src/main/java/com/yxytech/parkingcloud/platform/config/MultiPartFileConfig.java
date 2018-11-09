package com.yxytech.parkingcloud.platform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MultiPartFileConfig  {

    @Value("${parkingcloud.upload-tmp-path}")
    private String uploadTmpLocation;

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(uploadTmpLocation);
        factory.setMaxRequestSize("10MB");
        factory.setMaxFileSize("10MB");
        return factory.createMultipartConfig();
    }
}
