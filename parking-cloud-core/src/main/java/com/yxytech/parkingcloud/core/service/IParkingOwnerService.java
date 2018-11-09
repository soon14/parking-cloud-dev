package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.ParkingOwner;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-19
 */
public interface IParkingOwnerService extends IService<ParkingOwner> {

    String examine(ParkingOwner parkingOwner);
	
}
