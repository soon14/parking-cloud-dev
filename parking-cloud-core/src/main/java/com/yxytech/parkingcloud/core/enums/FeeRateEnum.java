package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum FeeRateEnum implements IEnum{
    T(0, "按次"),
    H(1, "按时");
    private Integer value;
    private String desc;

    FeeRateEnum(Integer value) {
        this.value = value;
    }
    FeeRateEnum(Integer value, String desc) {
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
