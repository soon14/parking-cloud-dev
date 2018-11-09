package com.yxytech.parkingcloud.security.exception;

public class InvalidTokenException extends RuntimeException {
    private static final long serialVersionUID = -294671188037028603L;

    /**
     * 构造方法
     */
    public InvalidTokenException() {
    }

    /**
     * 构造方法
     * @param msg
     */
    public InvalidTokenException(String msg) {
        super(msg);
    }

    /**
     * 构造方法
     * @param msg
     * @param t
     */
    public InvalidTokenException(String msg, Throwable t) {
        super(msg, t);
    }
}