package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.RolePermission;
import com.yxytech.parkingcloud.core.mapper.RolePermissionMapper;
import com.yxytech.parkingcloud.core.service.IRolePermissionService;
import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-11-11
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

    @Override
    public void syncPermissions(Long roleId, List<Long> permissionIds) {
        EntityWrapper<RolePermission> ew = new EntityWrapper<>();
        ew.setSqlSelect("permission_id").eq("role_id", roleId);
        List<Object> existIds = ListUtils.typedList(baseMapper.selectObjs(ew), Long.class);

        List<Long> intersection = ListUtils.intersection(permissionIds, existIds);
        List<Long> toAddIds = ListUtils.subtract(permissionIds, intersection);
        List<Long> toRemoveIds = ListUtils.subtract(existIds, intersection);

        if (!toRemoveIds.isEmpty()) {
            EntityWrapper<RolePermission> ewToRemove = new EntityWrapper<>();
            ewToRemove.eq("role_id", roleId).in("permission_id", toRemoveIds);
            delete(ewToRemove);
        }

        if (!toAddIds.isEmpty()) {
            List<RolePermission> toAdd = new ArrayList<>();
            toAddIds.forEach((item) -> toAdd.add(new RolePermission(roleId, item)));

            insertBatch(toAdd);
        }
    }
}
