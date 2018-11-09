package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum AdvertisementTypeEnum implements IEnum {
    NOT_CERT(0, "轮播"),
    CERT_ING(1, "底部");

    private Integer id;
    private String name;

    AdvertisementTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Serializable getValue() {
        return this.id;
    }

    @JsonValue
    public String getDesc() {
        return name;
    }
}
