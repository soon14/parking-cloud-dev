package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum BillTypeEnum implements IEnum {
    KAI_PIAO(0, "开票"),
    HONG_CHONG(1, "红冲"),
    BEI_HONG_CHONG(2, "被红冲");
    private Integer value;
    private String desc;

    BillTypeEnum(Integer value, String desc) {
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
