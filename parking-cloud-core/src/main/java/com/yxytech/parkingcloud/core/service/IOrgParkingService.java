package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.OrgParking;
import com.yxytech.parkingcloud.core.entity.Parking;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-11-10
 */
public interface IOrgParkingService extends IService<OrgParking> {


    public void syncParkings(List<Long> parkingIds, Long orgId);

    public List<Object> findBindParkingIds(Long orgId);

    public List<Parking> findBindParkings(List<Object> parkingIds);
	
}
