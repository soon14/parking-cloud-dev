package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum  ApproveEnum implements IEnum {

    NOT_APPROVE(0, "未认证"),
    APPROVE_ING(1, "认证中"),
    APPROVE_SUCCESS(2, "已认证"),
    APPROVE_FAIL(3,"未通过");

    private Integer value;
    private String desc;

    ApproveEnum(Integer value, String desc) {
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
