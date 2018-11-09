package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum OrderTransactionEnum implements IEnum{
    WAIT_FOR_PAY(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    FAILED(2, "支付失败");

    private Integer value;
    private String desc;

    OrderTransactionEnum(Integer value, String desc) {
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
