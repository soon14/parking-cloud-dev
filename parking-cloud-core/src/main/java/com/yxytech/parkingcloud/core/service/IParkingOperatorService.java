package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.ParkingOperator;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-19
 */
public interface IParkingOperatorService extends IService<ParkingOperator> {

    String examine(ParkingOperator parkingOperator);
	
}
