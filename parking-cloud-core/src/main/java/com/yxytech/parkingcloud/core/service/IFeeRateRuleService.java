package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.FeeRateMultistep;
import com.yxytech.parkingcloud.core.entity.FeeRateRule;
import com.yxytech.parkingcloud.core.entity.FeeSchema;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-29
 */
public interface IFeeRateRuleService extends IService<FeeRateRule> {
    List<FeeRateRule> findByVehicle(CarTypeEnum vehicle, Long schemaId);

    boolean update(FeeRateRule entity, List<FeeRateMultistep> steps);

    int create(FeeRateRule entity, List<FeeRateMultistep> steps);

    List<Object> getDetail(Long ruleId);

    Page<Object[]> getFeeRuleRatePage(Page<Object[]> page, Wrapper<FeeRateRule> ew);

    List<FeeRateRule> list(FeeSchema schema);
    List<FeeRateRule> bindList(FeeSchema schema);



}
