package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum CouponStatus implements IEnum {
    CREATED(0, "未领取"),
    RECEIVED(1, "未使用"),
    USED(2, "已使用"),
    CANCEL(3, "已取消");

    private Integer value;
    private String desc;

    CouponStatus(Integer status, String desc) {
        this.value = status;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return value;
    }

    @JsonValue
    public String getDesc() {
        return desc;
    }
}
