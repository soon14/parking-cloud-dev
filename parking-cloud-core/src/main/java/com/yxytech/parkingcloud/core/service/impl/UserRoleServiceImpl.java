package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.UserRole;
import com.yxytech.parkingcloud.core.mapper.UserRoleMapper;
import com.yxytech.parkingcloud.core.service.IUserRoleService;
import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-11-11
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Override
    public Map<Long, Set<Long>> queryUsersRolesMap(Set<Long> userIds) {
        EntityWrapper<UserRole> ew = new EntityWrapper<>();
        ew.setSqlSelect("role_id", "user_id").in("user_id", userIds);
        List<Map<String, Object>> items = selectMaps(ew);

        Map<Long, Set<Long>> usersRolesMap = new HashMap<>();
        userIds.forEach(item -> usersRolesMap.put(item, new HashSet<>()));
        items.forEach(item -> {
            usersRolesMap.get(item.get("user_id")).add((Long)item.get("role_id"));
        });

        return usersRolesMap;
    }

    @Override
    public Set<Long> queryUserRoleIds(Long userId) {
        EntityWrapper<UserRole> ew = new EntityWrapper<>();
        ew.setSqlSelect("role_id", "user_id").eq("user_id", userId);
        List<Map<String, Object>> items = selectMaps(ew);

        Set<Long> roleIds = new HashSet<>();
        items.forEach(item -> roleIds.add((Long)item.get("role_id")));

        return roleIds;
    }

    @Override
    public void syncRoles(Long userId, List<Long> roleIds) {
        EntityWrapper<UserRole> ew = new EntityWrapper<>();
        ew.setSqlSelect("role_id").eq("user_id", userId);
        List<Object> existIds = ListUtils.typedList(baseMapper.selectObjs(ew), Long.class);

        List<Long> intersection = ListUtils.intersection(roleIds, existIds);
        List<Long> toAddIds = ListUtils.subtract(roleIds, intersection);
        List<Long> toRemoveIds = ListUtils.subtract(existIds, intersection);

        if (!toRemoveIds.isEmpty()) {
            EntityWrapper<UserRole> ewToRemove = new EntityWrapper<>();
            ewToRemove.eq("user_id", userId).in("role_id", toRemoveIds);
            delete(ewToRemove);
        }

        if (!toAddIds.isEmpty()) {
            List<UserRole> toAdd = new ArrayList<>();
            toAddIds.forEach((item) -> toAdd.add(new UserRole(userId, item)));

            insertBatch(toAdd);
        }
    }
}
