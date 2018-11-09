package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.CouponInfo;
import com.yxytech.parkingcloud.core.entity.PromoCode;
import com.yxytech.parkingcloud.core.mapper.CouponInfoMapper;
import com.yxytech.parkingcloud.core.service.ICouponInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwd
 * @since 2017-11-22
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements ICouponInfoService {

    @Override
    public List<CouponInfo> getCouponByPromoCode(Wrapper<PromoCode> wrapper) {
        return baseMapper.selectByPromoCode(wrapper);
    }
}
