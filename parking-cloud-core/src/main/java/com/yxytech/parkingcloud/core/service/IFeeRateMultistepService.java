package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.FeeRateMultistep;

import java.util.List;

public interface IFeeRateMultistepService extends IService<FeeRateMultistep> {
    FeeRateMultistep findById(FeeRateMultistep entity);
    void create(FeeRateMultistep entity);

    void update(FeeRateMultistep entity);

    List<FeeRateMultistep> getRateSteps(Long rateId);
}
