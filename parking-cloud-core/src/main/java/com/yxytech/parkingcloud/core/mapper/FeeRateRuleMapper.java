package com.yxytech.parkingcloud.core.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.core.entity.FeeRateRule;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author cj
 * @since 2017-10-29
 */
public interface FeeRateRuleMapper extends SuperMapper<FeeRateRule>, BaseMapper<FeeRateRule> {

    FeeRateRule findById(Long ruleId);


    List<FeeRateRule> getFeeRuleList(Serializable schemaId);
    List<FeeRateRule> getBindFeeRuleList(Serializable schemaId);

    int create(FeeRateRule entity);

    int update(FeeRateRule entity);

    FeeRateRule findByVehicleDate(@Param("vehicle") CarTypeEnum vehicle, @Param("daySql") String daySql, @Param("schemaId") Long schemaId);

    List<FeeRateRule> findByVehicle(@Param("vehicleType") CarTypeEnum vehicle, @Param("schemaId") Long schemaId);

    List<Object[]> getFeeRuleRatePage(Page<Object[]> page, @Param("ew") Wrapper<FeeRateRule> ew);

}