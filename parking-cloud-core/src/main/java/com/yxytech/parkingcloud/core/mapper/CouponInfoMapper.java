package com.yxytech.parkingcloud.core.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxytech.parkingcloud.core.entity.CouponInfo;
import com.yxytech.parkingcloud.core.entity.PromoCode;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author liwd
 * @since 2017-11-22
 */
public interface CouponInfoMapper extends SuperMapper<CouponInfo> {
    List<CouponInfo> selectByPromoCode(@Param("ew")Wrapper<PromoCode> wrapper);
}