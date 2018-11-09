package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum CycleEnum implements IEnum{
    REAL_TIME(1, "实时"), DAY(2, "天"), MONTH(3, "月"), YEAR(4, "年");
    private Integer value;
    private String desc;

    CycleEnum(Integer value, String desc) {
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
