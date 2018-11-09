package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.RolePermission;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-11-11
 */
public interface IRolePermissionService extends IService<RolePermission> {

    void syncPermissions(Long id, List<Long> permissionIds);
}
