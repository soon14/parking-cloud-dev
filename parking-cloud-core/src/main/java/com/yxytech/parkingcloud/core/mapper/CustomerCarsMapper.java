package com.yxytech.parkingcloud.core.mapper;

import com.yxytech.parkingcloud.core.entity.CustomerCars;
import com.yxytech.parkingcloud.core.utils.SuperMapper;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author liwd
 * @since 2017-10-26
 */
public interface CustomerCarsMapper extends SuperMapper<CustomerCars> {
    List<CustomerCars> getAllCarsByUserId(Long userId);
}