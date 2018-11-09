package com.yxytech.parkingcloud.core.mapper;

import com.yxytech.parkingcloud.core.entity.CustomerBindCars;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author liwd
 * @since 2017-10-26
 */
public interface CustomerBindCarsMapper extends SuperMapper<CustomerBindCars> {
    Integer updateOtherBindRelation(Long carId);

    List<CustomerBindCars> getAllBindCars(@Param("userId") Long userId);
}