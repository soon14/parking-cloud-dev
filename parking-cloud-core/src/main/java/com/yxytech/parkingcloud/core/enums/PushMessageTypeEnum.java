package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

public enum PushMessageTypeEnum implements IEnum {
    ANDROID_NOTIFICATION(0, "Andriod Notification");

    private Integer value;
    private String desc;

    PushMessageTypeEnum(Integer value, String desc) {
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
