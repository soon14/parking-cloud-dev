package com.yxytech.parkingcloud.platform.config;

import com.yxytech.parkingcloud.security.CustomCorsFilter;
import com.yxytech.parkingcloud.security.RestAuthenticationEntryPoint;
import com.yxytech.parkingcloud.security.auth.ajax.AjaxAuthenticationProvider;
import com.yxytech.parkingcloud.security.auth.ajax.AjaxLoginProcessingFilter;
import com.yxytech.parkingcloud.security.auth.jwt.JwtAuthenticationProvider;
import com.yxytech.parkingcloud.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.yxytech.parkingcloud.security.auth.jwt.SkipPathRequestMatcher;
import com.yxytech.parkingcloud.security.auth.jwt.extractor.TokenExtractor;
import com.yxytech.parkingcloud.security.config.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 身份认证入口
     */
    public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/auth/login";

    /**
     * 刷新token
     */
    public static final String TOKEN_REFRESH_ENTRY_POINT = "/auth/token";

    /**
     * 拦截路径，这里是将所有的路径进行拦截
     */
    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/**";

    /**
     * 上传路径
     */
    public static final String UPLOAD_VIEW_ENTRY_POINT = "/upload/**";

    /**
     * 支付回调
     */
    public static final String PAYMENT_NOTIFY = "/notify/**";

    /**
     * etc通知
     */
    public static final String ETC_NOTIFY = "/etcInfo/etcNotify";

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 权限Manager
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 授权成功处理Handler
     */
    @Autowired
    private AuthenticationSuccessHandler successHandler;

    /**
     * 授权失败处理Handler
     */
    @Autowired
    private AuthenticationFailureHandler failureHandler;

    /**
     * ajax授权生产者
     */
    @Autowired
    private AjaxAuthenticationProvider ajaxAuthenticationProvider;

    /**
     * jwt授权生产者
     */
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    /**
     * token提取器
     */
    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private JwtProperties jwtProperties;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 创建Ajax登录处理过滤器
     * @return
     * @throws Exception
     */
    protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter() throws Exception {
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(FORM_BASED_LOGIN_ENTRY_POINT,
                this.successHandler, this.failureHandler);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    /**
     * JWT token授权处理过滤器
     * @return
     * @throws Exception
     */
    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {

        List<String> pathsToSkip = Arrays.asList(
                TOKEN_REFRESH_ENTRY_POINT, FORM_BASED_LOGIN_ENTRY_POINT, UPLOAD_VIEW_ENTRY_POINT, PAYMENT_NOTIFY, ETC_NOTIFY
        );
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);

        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(
                this.failureHandler, this.tokenExtractor, matcher, jwtProperties);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.ajaxAuthenticationProvider);
        auth.authenticationProvider(this.jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(this.authenticationEntryPoint)

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .antMatchers(FORM_BASED_LOGIN_ENTRY_POINT).permitAll() // Login end-point
            .antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll() // Token refresh end-point
            .antMatchers(UPLOAD_VIEW_ENTRY_POINT).permitAll()
            .antMatchers(PAYMENT_NOTIFY).permitAll()
            .antMatchers(ETC_NOTIFY).permitAll()

            .and()
            .authorizeRequests()
            .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).permitAll()

            .and()
            .addFilterBefore(new RequestLogFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            ;
    }

    /**
     * 放行excel文件下载
     * @param webSecurity
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().regexMatchers(".*xlsx");
    }
}
