package com.yxytech.parkingcloud.security.exception;

import com.yxytech.parkingcloud.security.model.token.JwtToken;
import org.springframework.security.core.AuthenticationException;

public class ExpiredTokenException extends AuthenticationException {

    /**
     * JWT token对象
     */
    private JwtToken token;

    /**
     * 构造方法
     * @param msg
     */
    public ExpiredTokenException(String msg) {
        super(msg);
    }

    /**
     * 构造方法
     * @param token
     * @param msg
     * @param t
     */
    public ExpiredTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    /**
     * 返回token
     * @return
     */
    public String token() {
        return this.token.getToken();
    }
}
