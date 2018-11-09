package com.yxytech.parkingcloud.app.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Value("${parkingcloud.upload-context}")
    private String uploadContext;

    @Value("${parkingcloud.upload-path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加上传目录资源映射
        registry.addResourceHandler(uploadContext + "/**").addResourceLocations("file:" + uploadPath + File.separator);

        super.addResourceHandlers(registry);
    }
}
