package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.ParkingTimeLengthStaticForm;
import com.yxytech.parkingcloud.core.entity.ParkingTimeLengthStatistics;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-11-15
 */
public interface IParkingTimeLengthStatisticsService extends IService<ParkingTimeLengthStatistics> {
	public Object formatDateSearch(ParkingTimeLengthStaticForm parkingTimeLengthStaticForm);
}
