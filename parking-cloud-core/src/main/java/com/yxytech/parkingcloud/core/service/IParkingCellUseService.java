package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.ParkingCellUse;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-19
 */
public interface IParkingCellUseService extends IService<ParkingCellUse> {
	List<ParkingCellUse> getInUseCells(List<Long> parkingIds);
}
