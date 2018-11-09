package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.ParkingPort;
import com.yxytech.parkingcloud.core.utils.YXYIService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-18
 */
public interface IParkingPortService extends IService<ParkingPort>,YXYIService<ParkingPort> {

    public String validate(Long parkingId,String code);

    public void createParkingPort(ParkingPort parkingPort);

    public void updateParkingPort(ParkingPort parkingPort);

    void updateBatch(List<ParkingPort> list);

    ParkingPort detail(Long id);

    String updateValidate(ParkingPort parkingPort);

}
