package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.FeeRateRule;
import com.yxytech.parkingcloud.core.entity.FeeSchema;
import com.yxytech.parkingcloud.core.entity.Parking;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-29
 */
public interface IFeeSchemaService extends IService<FeeSchema> {

    boolean bindRule(Long schemaId,List<Long> rules) throws Exception;
    boolean bindParking(Long schemaId,List<Long> parking) throws Exception;

    List<FeeRateRule> getFeeRateRulesBySchemaIds(List<Long> ids);

    boolean update(FeeSchema entity);

    boolean create(FeeSchema entity);

    FeeSchema getDetail(String schemaId);

    Page<FeeSchema> getFeeSchemaPage(Page<FeeSchema> page,FeeSchema entity);

    long getVersion();

    FeeSchema getByTime(Long enterTime, Long parkingId);
}
