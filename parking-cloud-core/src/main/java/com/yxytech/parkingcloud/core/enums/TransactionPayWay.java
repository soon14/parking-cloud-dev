package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum TransactionPayWay implements IEnum {
    WECHAT(0, "微信支付"),
    ALIPAY(1, "支付宝支付"),
    UNION_PAY(2, "银联支付"),
    COUPON(3, "优惠券支付"),
    CASH(4, "现金支付"),
    WECHAT_PRE_PAY(5, "微信预支付"),
    WECHAT_REFUND(6, "微信预支付退款");

    private Integer value;
    private String remark;

    TransactionPayWay(Integer value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    @JsonValue
    public String getDesc() {
        return remark;
    }

    @Override
    public Serializable getValue() {
        return value;
    }
}
