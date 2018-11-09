package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

// TODO 待后续商定，目前预留
public enum OrderStatusEnum implements IEnum {
    CREATED(0, "新建"),
    FINISHED(1, "已完成");

    private Integer value;
    private String remark;

    OrderStatusEnum(Integer value, String remark) {
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
