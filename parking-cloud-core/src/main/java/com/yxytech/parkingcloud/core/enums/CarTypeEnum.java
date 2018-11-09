package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum CarTypeEnum implements IEnum {
    ALL(0, "通用车型"),
    BIG(1, "小型汽车"),
    MEDIUM(2, "中型汽车"),
    SMALL(3, "大型汽车");

    private Integer value;
    private String desc;

    CarTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    CarTypeEnum(Integer value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }

    @JsonValue
    public String getDesc() {
        return this.desc;
    }
}
