package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.FeeRateMultistep;
import com.yxytech.parkingcloud.core.entity.FeeRateRule;
import com.yxytech.parkingcloud.core.entity.FeeSchema;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.mapper.FeeRateRuleMapper;
import com.yxytech.parkingcloud.core.service.IFeeRateMultistepService;
import com.yxytech.parkingcloud.core.service.IFeeRateRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-10-29
 */
@Service
public class FeeRateRuleServiceImpl extends ServiceImpl<FeeRateRuleMapper, FeeRateRule> implements IFeeRateRuleService {

    @Autowired
    private IFeeRateMultistepService feeRateMultistepService;

    @Override
    public List<FeeRateRule> findByVehicle(CarTypeEnum vehicle, Long schemaId) {
        return baseMapper.findByVehicle(vehicle, schemaId);
    }

    @Override
    public boolean update(FeeRateRule entity, List<FeeRateMultistep> steps) {

        boolean flag = false;

        if (baseMapper.update(entity) <= 0) {
            return false;
        }

        for (int i = 0; i < steps.size(); i++) {
            FeeRateMultistep mstep = steps.get(i);
            mstep.setSortNumber(i + 1);
            mstep.setFeeRateId(entity.getId());

            steps.set(i, mstep);
        }

        Wrapper<FeeRateMultistep> wrapper = new EntityWrapper<>();
        wrapper.eq("fee_rate_id", entity.getId());
        feeRateMultistepService.delete(wrapper);
        if (steps.size() > 0) {
            feeRateMultistepService.insertBatch(steps, steps.size());
        }

        return false;
    }

    @Override
    public int create(FeeRateRule entity, List<FeeRateMultistep> steps) {

        baseMapper.create(entity);


        for (int i = 0; i < steps.size(); i++) {

            FeeRateMultistep mstep = steps.get(i);
            mstep.setSortNumber(i + 1);
            mstep.setFeeRateId(entity.getId());
            feeRateMultistepService.create(mstep);
        }
        return 0;
    }

    @Override
    public List<Object> getDetail(Long ruleId) {
        List<Object> rules = new ArrayList<>();
        FeeRateRule rule = baseMapper.findById(ruleId);
        rules.add(rule);
        List<FeeRateMultistep> steps = feeRateMultistepService.getRateSteps(ruleId);
        rules.add(steps);

        return rules;
    }

    @Override
    public Page<Object[]> getFeeRuleRatePage(Page<Object[]> page, Wrapper<FeeRateRule> ew) {
        page.setRecords(baseMapper.getFeeRuleRatePage(page, ew));
        return page;
    }

    @Override
    public List<FeeRateRule> list(FeeSchema schema) {

        return baseMapper.getFeeRuleList(schema.getId());
    }

    @Override
    public List<FeeRateRule> bindList(FeeSchema schema) {
        return baseMapper.getBindFeeRuleList(schema.getId());
    }


    @Override
    public FeeRateRule selectById(Serializable id) {
        return baseMapper.findById((Long) id);
    }
}
