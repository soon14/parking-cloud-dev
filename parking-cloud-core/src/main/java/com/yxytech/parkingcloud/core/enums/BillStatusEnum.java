package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum BillStatusEnum implements IEnum{
    ING(0, "开票中"),
    COMPLETE(1, "已完成"),
    FAIL(2, "开票失败");

    private Integer value;
    private String desc;

    BillStatusEnum(Integer value, String desc) {
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
