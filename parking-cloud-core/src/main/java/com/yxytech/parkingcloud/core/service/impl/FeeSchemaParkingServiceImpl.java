package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.FeeRateRule;
import com.yxytech.parkingcloud.core.entity.FeeSchema;
import com.yxytech.parkingcloud.core.entity.FeeSchemaParking;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.enums.FeeRateEnum;
import com.yxytech.parkingcloud.core.mapper.FeeSchemaParkingMapper;
import com.yxytech.parkingcloud.core.service.IFeeSchemaParkingService;
import com.yxytech.parkingcloud.core.service.IFeeSchemaService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-11-27
 */
@Service
public class FeeSchemaParkingServiceImpl extends ServiceImpl<FeeSchemaParkingMapper, FeeSchemaParking> implements IFeeSchemaParkingService {

    @Autowired
    private IFeeSchemaService feeSchemaService;

    @Autowired
    private IParkingService parkingService;

    @Override
    public String bindSchema(Long parkingId,List<Long> schemaIds) throws Exception {
        String errorMsg = null;
        EntityWrapper<FeeSchema> ew1 = new EntityWrapper<>();
        ew1.setSqlSelect("id").in("id",schemaIds);
        List<Object> existSchemaIds = feeSchemaService.selectObjs(ew1);

        if(schemaIds.size() > existSchemaIds.size()){
            existSchemaIds.forEach(item -> schemaIds.remove(item));
            errorMsg = "费率计划id不存在:" + schemaIds.toString();
        }

        Parking parking = parkingService.selectById(parkingId);
        if (! parking.getAllDay()) {
            List<FeeRateRule> rateRules = feeSchemaService.getFeeRateRulesBySchemaIds(schemaIds);

            for (FeeRateRule rateRule : rateRules) {
                if (rateRule.getType().equals(FeeRateEnum.H)) {
                    throw new Exception("非24小时营业的停车场不能绑定按时的费率!");
                }
            }
        }

        EntityWrapper<FeeSchemaParking> ew2 = new EntityWrapper<>();
        ew2.setSqlSelect("fee_schema_id").eq("parking_id",parkingId);
        List<Long> existIds = ListUtils.typedList(baseMapper.selectObjs(ew2),Long.class);

        List<Long> intersection = ListUtils.intersection(schemaIds,existIds);
        List<Long> toRemoveIds = ListUtils.subtract(existIds,intersection);
        List<Long> toAddIds = ListUtils.subtract(schemaIds,intersection);

        if(!toRemoveIds.isEmpty()){
            EntityWrapper<FeeSchemaParking> ewToRemove = new EntityWrapper<>();
            ewToRemove.eq("parking_id",parkingId).in("fee_schema_id",schemaIds);
            delete(ewToRemove);
        }

        if(!toAddIds.isEmpty()){
            List<FeeSchemaParking> toAdd = new ArrayList<>();
            toAddIds.forEach(item -> toAdd.add(new FeeSchemaParking(parkingId,item)));
            insertBatch(toAdd);
        }

        return errorMsg;
    }

}
