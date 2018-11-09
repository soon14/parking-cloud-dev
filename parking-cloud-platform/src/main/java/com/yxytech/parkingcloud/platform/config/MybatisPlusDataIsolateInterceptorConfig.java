package com.yxytech.parkingcloud.platform.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.yxytech.parkingcloud.core.config.YXYInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusDataIsolateInterceptorConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setLocalPage(false);// 是否开启 PageHelper 的支持

        return paginationInterceptor;
    }

    @Bean
    public YXYInterceptor yxyInterceptor() {
        return new YXYInterceptor();
    }
}
