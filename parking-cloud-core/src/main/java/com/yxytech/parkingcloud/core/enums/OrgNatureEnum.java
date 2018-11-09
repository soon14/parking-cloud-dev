package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum OrgNatureEnum implements IEnum {

    COMPANY(0, "企业单位"),
    INSTITUTION(1, "事业单位"),
    GOVERNMENT(2,"政府单位"),
    ORGANIZATION(3,"社会团体");

    private int value;
    private String desc;

    OrgNatureEnum(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }


    @JsonValue // Jackson 注解，可序列化该属性为中文描述【可无】
    public String getDesc(){
        return this.desc;
    }
}
