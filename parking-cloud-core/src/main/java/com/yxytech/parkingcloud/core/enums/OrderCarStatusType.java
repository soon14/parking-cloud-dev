package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum OrderCarStatusType implements IEnum {
    WAIT_ENTER(0, "未进入"),
    PARKING(1, "在场"),
    LEAVE(2, "离场");

    private Integer value;
    private String remark;

    OrderCarStatusType(Integer value, String remark) {
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
