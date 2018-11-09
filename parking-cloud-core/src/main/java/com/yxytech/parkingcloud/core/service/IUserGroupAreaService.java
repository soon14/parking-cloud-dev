package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.UserGroupArea;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-11-10
 */
public interface IUserGroupAreaService extends IService<UserGroupArea> {

    void syncAreas(Long id, List<Long> areaIds);

}
