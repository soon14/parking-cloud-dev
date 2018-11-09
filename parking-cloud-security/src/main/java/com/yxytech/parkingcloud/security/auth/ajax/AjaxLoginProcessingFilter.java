package com.yxytech.parkingcloud.security.auth.ajax;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.yxytech.parkingcloud.commons.entity.UserIdentity;
import com.yxytech.parkingcloud.security.entity.UserTokenRequest;
import com.yxytech.parkingcloud.security.exception.NotSupportedException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 日志对象
     */
    private static Logger logger = (Logger) LoggerFactory.getLogger(AjaxLoginProcessingFilter.class);

    /**
     * 授权成功处理对象
     */
    private final AuthenticationSuccessHandler successHandler;

    /**
     * 授权失败处理对象
     */
    private final AuthenticationFailureHandler failureHandler;


    public AjaxLoginProcessingFilter(String defaultFilterProcessesUrl,
                                        final AuthenticationSuccessHandler successHandler,
                                        final AuthenticationFailureHandler failureHandler) {
        super(defaultFilterProcessesUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            if (logger.isDebugEnabled()) {
                logger.debug("Authentication method not supported. Request method: " + request.getMethod());
            }

            throw new NotSupportedException("认证请求方式不支持");
        }

        /*
         * 从请求体中获取对应属性，并判断属性的合法性
         */
        char[] buf = new char[request.getContentLength()];
        request.getReader().read(buf);
        UserTokenRequest userReq = null;
        try {
            userReq = JSON.parseObject(String.valueOf(buf), UserTokenRequest.class);
        } catch (JSONException ex) {
            throw new NotSupportedException("请求参数不是有效的JSON");
        }

        if (userReq == null
                || StringUtils.isEmpty(userReq.getUsername())
                || StringUtils.isEmpty(userReq.getPassword())
                ) {
            throw new NotSupportedException("参数不能为空");
        }

        UserIdentity userIdentity = new UserIdentity(userReq.getOrgName(), userReq.getUsername());

        /*
         * 通过用户名和密码创建授权token
         */
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userIdentity, userReq.getPassword());

        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
