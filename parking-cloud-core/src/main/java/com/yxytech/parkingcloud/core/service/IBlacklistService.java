package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.Blacklist;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-10-20
 */
public interface IBlacklistService extends IService<Blacklist> {
    void isReflect(Blacklist blacklist);

    Page<Blacklist> customerSelect(Page<Blacklist> page, Wrapper<Blacklist> wrapper);

    Boolean isInBlackList(String plateNumber, Integer plateColor, Long parkingId);
}
