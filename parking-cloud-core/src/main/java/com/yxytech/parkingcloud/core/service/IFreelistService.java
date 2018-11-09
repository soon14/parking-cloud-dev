package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.Freelist;
import com.yxytech.parkingcloud.core.entity.OrderInfo;
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
public interface IFreelistService extends IService<Freelist> {
	Boolean create(Freelist freelist);

	Page<Freelist> customerSelect(Page<Freelist> page, Wrapper<Freelist> wrapper);

	List<FreeEntity> getAllFreeTime(OrderInfo orderInfo, List<Freelist> freelists, Date startTime, Date endTime);

	Boolean updateForUsedTimes(Long id, Integer times);
}
