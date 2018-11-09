package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.CouponInfo;
import com.yxytech.parkingcloud.core.entity.PromoCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-11-21
 */
public interface IPromoCodeService extends IService<PromoCode> {
	List<PromoCode> createPromoCode(Integer count, CouponInfo couponInfo);

	@Override
	List<PromoCode> selectBatchIds(List<? extends Serializable> idList);
}
