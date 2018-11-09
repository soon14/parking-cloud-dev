package com.yxytech.parkingcloud.core.exception;

public class ServiceException extends Exception {
    private Integer code;
    private String message;

    public static final Integer NOT_FOUND_ACCOUNT = 10400;

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
