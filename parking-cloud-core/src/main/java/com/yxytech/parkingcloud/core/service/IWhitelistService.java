package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.Whitelist;
import com.yxytech.parkingcloud.core.utils.FreeEntity;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-10-20
 */
public interface IWhitelistService extends IService<Whitelist> {
    void isReflect(Whitelist whitelist);

    Page<Whitelist> customerSelect(Page<Whitelist> page, Wrapper<Whitelist> wrapper);

    List<FreeEntity> getAllFreeTime(List<Whitelist> whitelists, Date startTime, Date endTime);
}
