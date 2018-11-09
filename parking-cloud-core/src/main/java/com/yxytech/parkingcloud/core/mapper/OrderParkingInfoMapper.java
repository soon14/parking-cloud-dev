package com.yxytech.parkingcloud.core.mapper;

import com.yxytech.parkingcloud.core.entity.OrderParkingInfo;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author liwd
 * @since 2017-10-26
 */
public interface OrderParkingInfoMapper extends SuperMapper<OrderParkingInfo> {
    Long getValidOrder(@Param("parkingId") Long parkingId, @Param("parkingCellId") Long parkingCellId);
}