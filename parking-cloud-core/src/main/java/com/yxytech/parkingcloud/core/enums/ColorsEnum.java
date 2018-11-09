package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum ColorsEnum implements IEnum {
    BLUE(0, "蓝"),
    WHITE(1, "白"),
    YELLOW(2, "黄"),
    GREEN(3, "绿"),
    BLACK(4, "黑");

    ColorsEnum(Integer type, String remark) {
        this.value = type;
        this.desc = remark;
    }

    private int value;
    private String desc;

    @Override
    public Serializable getValue() {
        return this.value;
    }

    @JsonValue
    public String getDesc() {
        return desc;
    }
}
