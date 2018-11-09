package com.yxytech.parkingcloud.core.utils;

import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

public class SuperModel<T extends Model> extends Model<T> {

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
