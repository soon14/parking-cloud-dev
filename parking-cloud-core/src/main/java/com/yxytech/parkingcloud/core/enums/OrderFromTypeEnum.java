package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum OrderFromTypeEnum implements IEnum {
    OTHER(0, "其他创建方式"),
    APP(1, "APP预约创建"),
    VIDEO(2, "视频桩创建"),
    CUSTOMER(3, "手动创建"),
    GEO(4, "地磁设备创建");

    private Integer value;
    private String remark;

    OrderFromTypeEnum(Integer value, String remark) {
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
