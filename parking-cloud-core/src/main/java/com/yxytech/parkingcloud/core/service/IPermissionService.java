package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.Permission;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-11-11
 */
public interface IPermissionService extends IService<Permission> {

    String validatePermissionIds(List<Long> permissionIds);

    List<String> getPermissionCodesByUser(Long userId);
}
