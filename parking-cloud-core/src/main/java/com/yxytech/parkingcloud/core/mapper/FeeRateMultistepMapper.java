package com.yxytech.parkingcloud.core.mapper;

import com.yxytech.parkingcloud.core.entity.FeeRateMultistep;
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
public interface FeeRateMultistepMapper extends SuperMapper<FeeRateMultistep> {

    FeeRateMultistep findById(FeeRateMultistep entity);
    void create(FeeRateMultistep entity);

    void update(FeeRateMultistep entity);

    List<FeeRateMultistep> getRateSteps(@Param("feeRateId") Long rateId);

}