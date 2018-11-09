package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.FeeRateMultistep;
import com.yxytech.parkingcloud.core.mapper.FeeRateMultistepMapper;
import com.yxytech.parkingcloud.core.service.IFeeRateMultistepService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeRateMultistepServiceImpl extends ServiceImpl<FeeRateMultistepMapper, FeeRateMultistep> implements IFeeRateMultistepService {

    @Override
    public FeeRateMultistep findById(FeeRateMultistep entity) {
        return baseMapper.findById(entity);
    }

    @Override
    public void create(FeeRateMultistep entity) {
        baseMapper.create(entity);
    }

    @Override
    public void update(FeeRateMultistep entity) {
        baseMapper.update(entity);
    }

    @Override
    public List<FeeRateMultistep> getRateSteps(Long rateId) {
        return baseMapper.getRateSteps(rateId);
    }
}
