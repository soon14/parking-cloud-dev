package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.ParkingLane;
import com.yxytech.parkingcloud.core.utils.YXYIService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-18
 */
public interface IParkingLaneService extends IService<ParkingLane>,YXYIService<ParkingLane> {

    public String validate(Long parkingId,Long parkingPortId,String code);

    ParkingLane detail(Long id);

    String updateValidate(ParkingLane parkingLane);
}
