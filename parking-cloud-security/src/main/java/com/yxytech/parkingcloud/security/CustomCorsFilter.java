package com.yxytech.parkingcloud.security;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

public class CustomCorsFilter extends CorsFilter {

    /**
     * 构造方法
     */
    public CustomCorsFilter() {
        super(configurationSource());
    }

    /**
     * URL基础资源配置方法
     * @return
     */
    private static UrlBasedCorsConfigurationSource configurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.setMaxAge(36000L);
        String[] methods = {"GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"};
        config.setAllowedMethods(Arrays.asList(methods));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
