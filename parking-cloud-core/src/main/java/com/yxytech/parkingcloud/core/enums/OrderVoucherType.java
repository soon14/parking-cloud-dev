package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum OrderVoucherType implements IEnum {
    ENTER(0, "入场"),
    IN_PARKING(1, "在场"),
    LEAVE(2, "离场");

    private Integer type;
    private String desc;

    OrderVoucherType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return type;
    }

    @JsonValue
    public String getDesc() {
        return desc;
    }
}
