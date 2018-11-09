package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum OrderVoucherEnum implements IEnum {
    ENTER(0, "入场"),
    LEAVE(1, "离场");

    private Integer value;
    private String remark;

    OrderVoucherEnum(Integer value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    @Override
    public Serializable getValue() {
        return value;
    }

    @JsonValue
    public String getRemark() {
        return remark;
    }
}
