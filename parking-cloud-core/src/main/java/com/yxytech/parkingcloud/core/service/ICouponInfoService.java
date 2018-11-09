package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.CouponInfo;
import com.yxytech.parkingcloud.core.entity.PromoCode;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-11-22
 */
public interface ICouponInfoService extends IService<CouponInfo> {
	List<CouponInfo> getCouponByPromoCode(Wrapper<PromoCode> wrapper);
}
