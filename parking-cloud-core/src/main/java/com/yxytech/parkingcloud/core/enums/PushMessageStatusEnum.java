package com.yxytech.parkingcloud.core.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

public enum PushMessageStatusEnum implements IEnum {
    UNREAD(0, "未读"),
    READ(1, "已读");

    private Integer value;
    private String desc;

    PushMessageStatusEnum(Integer value, String desc) {
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
