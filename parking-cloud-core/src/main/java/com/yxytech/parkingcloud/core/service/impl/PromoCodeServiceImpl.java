package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.CouponInfo;
import com.yxytech.parkingcloud.core.entity.PromoCode;
import com.yxytech.parkingcloud.core.enums.CouponStatus;
import com.yxytech.parkingcloud.core.mapper.PromoCodeMapper;
import com.yxytech.parkingcloud.core.service.IPromoCodeService;
import com.yxytech.parkingcloud.core.utils.UniqueCode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwd
 * @since 2017-11-21
 */
@Service
public class PromoCodeServiceImpl extends ServiceImpl<PromoCodeMapper, PromoCode> implements IPromoCodeService {

    @Override
    public List<PromoCode> createPromoCode(Integer count, CouponInfo couponInfo) {
        int[] arrs = new int[count];
        List<PromoCode> list = new ArrayList<>();

        for (int i : arrs) {
            PromoCode promoCode = new PromoCode();

            BeanUtils.copyProperties(couponInfo, promoCode);

            promoCode.setCouponId(couponInfo.getId());
            promoCode.setPromoCode(UniqueCode.generateUniqueCode(i));
            promoCode.setStatus(CouponStatus.CREATED);
            promoCode.setReceiveStart(couponInfo.getTimeIntervalStart());
            promoCode.setReceiveEnd(couponInfo.getTimeIntervalEnd());

            list.add(promoCode);
        }

        return list;
    }

    @Override
    public List<PromoCode> selectBatchIds(List<? extends Serializable> idList) {
        return baseMapper.selectBatchIds(idList);
    }
}
