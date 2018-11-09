package com.yxytech.parkingcloud.security.auth.ajax;

import com.alibaba.fastjson.JSON;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.security.exception.ExpiredTokenException;
import com.yxytech.parkingcloud.security.exception.NotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // 设置返回错误编码 和 MediaType
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        ApiResponse<String> resp = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "Authentication failed");

        if (exception instanceof ExpiredTokenException) {
            resp.setMessage("Token已失效");
        } else if (exception instanceof NotSupportedException
                || exception instanceof UsernameNotFoundException
                || exception instanceof BadCredentialsException) {
            resp.setMessage(exception.getMessage());
        }

        response.getWriter().write(JSON.toJSONString(resp));

    }
}
