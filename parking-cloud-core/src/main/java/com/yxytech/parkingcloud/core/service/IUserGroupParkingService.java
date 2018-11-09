package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.UserGroupParking;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-11-10
 */
public interface IUserGroupParkingService extends IService<UserGroupParking> {

    void syncParkings(Long id, List<Long> parkingIds);

}
