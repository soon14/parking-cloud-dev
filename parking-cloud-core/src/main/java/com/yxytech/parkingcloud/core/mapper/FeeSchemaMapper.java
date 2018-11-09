package com.yxytech.parkingcloud.core.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.core.entity.FeeRateRule;
import com.yxytech.parkingcloud.core.entity.FeeSchema;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author cj
 * @since 2017-10-29
 */
public interface FeeSchemaMapper extends SuperMapper<FeeSchema> {


    List<FeeSchema> getSchemaPage(Page<FeeSchema> page,FeeSchema entity);

    int create(FeeSchema entity);

    int update (FeeSchema entity);

    FeeSchema getByTime(@Param("enterTime") Long enterTime,@Param("parkingId") Long parkingId);

    Long isRuleExist(@Param("schemaId") Long schemaId,@Param("ruleId") Long ruleId);
    int bind(@Param("schemaId") Long schemaId,@Param("ruleId") Long ruleId);
    int bindParking(@Param("schemaId") Long schemaId,@Param("parkingId") Long parkingId);
    int unbindParking(@Param("schemaId") Long schemaId);
    int unbind(@Param("schemaId")  Long schemaId);
    Long maxVersion();

    List<FeeRateRule> getRateRulesBySchemaId(List<Long> id);

}