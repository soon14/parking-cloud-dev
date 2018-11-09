package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum OrderTransactionCreateWayEnum implements IEnum {
    SYSTEM(0, "系统创建"),
    WECHAT(1, "微信回调创建");

    private Integer value;
    private String desc;

    OrderTransactionCreateWayEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return value;
    }

    @JsonValue
    public String getDesc() {
        return desc;
    }
}
