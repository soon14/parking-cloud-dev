package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum UserGroupStatusEnum implements IEnum {
    INACTIVE(0, "停用"),
    ACTIVE(1, "启用");


    private Integer value;
    private String desc;

    UserGroupStatusEnum(final Integer value, final String desc) {
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
