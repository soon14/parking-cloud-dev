package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum CycleTypeEnum implements IEnum{
    NOW(0, "入场"),
    DATE(1, "日期"),
    DIY(2, "指定时间");
    private Integer value;
    private String desc;

    CycleTypeEnum(final int pValue) {
        this.value = pValue;
    }
    CycleTypeEnum(Integer value, String desc) {
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
