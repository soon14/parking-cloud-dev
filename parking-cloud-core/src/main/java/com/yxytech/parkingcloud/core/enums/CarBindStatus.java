package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum CarBindStatus implements IEnum {
    NOT_CERT(0, "未认证"),
    CERT_ING(1, "认证中"),
    CERT_SUCCESS(2, "已认证"),
    CERT_FAILED(3, "认证失败"),
    UNBIND(4, "已解绑");

    private Integer value;
    private String desc;

    CarBindStatus(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }

    @JsonValue
    public String getDesc() {
        return desc;
    }
}
