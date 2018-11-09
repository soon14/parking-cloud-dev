package com.yxytech.parkingcloud.core.mapper;

import com.yxytech.parkingcloud.core.entity.Permission;
import com.yxytech.parkingcloud.core.utils.SuperMapper;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author cj
 * @since 2017-11-11
 */
public interface PermissionMapper extends SuperMapper<Permission> {
    List<String> getAllPermissionCodesByUser(Long userId);
}