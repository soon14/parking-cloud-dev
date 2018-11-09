package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.ParkingAmountStatistics;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-11-16
 */
public interface IParkingAmountStatisticsService extends IService<ParkingAmountStatistics> {

    public Object searchParkingLotCharge(Integer type, Integer areaId, Integer parkingId, Date startTimeDate, Date endTimeDate);

    public Object formatSearchDate(String startTime, String endTime);
}
