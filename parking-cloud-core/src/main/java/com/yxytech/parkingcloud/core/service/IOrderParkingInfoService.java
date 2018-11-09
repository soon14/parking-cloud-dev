package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.OrderParkingInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-10-26
 */
public interface IOrderParkingInfoService extends IService<OrderParkingInfo> {
	Long getValidOrder(Long parkingId, Long parkingCellId);
}
