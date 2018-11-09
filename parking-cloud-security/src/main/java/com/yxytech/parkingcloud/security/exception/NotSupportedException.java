package com.yxytech.parkingcloud.security.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

public class NotSupportedException extends AuthenticationServiceException {

    /**
     * 构造方法
     * @param msg
     */
    public NotSupportedException(String msg) {
        super(msg);
    }
}
