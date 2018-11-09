package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.PermissionResource;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-11-11
 */
public interface IPermissionResourceService extends IService<PermissionResource> {

    public void syncResource(Long permissionId, List<Long> resourceIds);
}
