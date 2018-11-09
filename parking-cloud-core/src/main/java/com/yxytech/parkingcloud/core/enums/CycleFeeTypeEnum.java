package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum CycleFeeTypeEnum implements IEnum{
    N(0, "首循环不合并"),
    M(1, "首循环合并");
    private Integer value;
    private String desc;

    CycleFeeTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    CycleFeeTypeEnum(final int pValue) {
        this.value = pValue;
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
