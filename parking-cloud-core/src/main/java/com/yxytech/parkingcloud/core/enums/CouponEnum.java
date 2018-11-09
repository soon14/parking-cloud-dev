package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum CouponEnum implements IEnum {
    VOUCHER(0, "代金券"),
    DISCOUNT(1, "打折卡"),
    TIME(2, "时长优惠卡");

    private Integer value;
    private String desc;

    CouponEnum(Integer value, String desc) {
        this.value = value;
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
