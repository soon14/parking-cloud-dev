package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

public enum CouponType implements IEnum {
    COUPON(0, "优惠券"),
    PROMO_CODE(1, "优惠码");

    private Integer value;
    private String desc;

    CouponType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
