package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.UserGroupRole;
import com.yxytech.parkingcloud.core.mapper.UserGroupRoleMapper;
import com.yxytech.parkingcloud.core.service.IUserGroupRoleService;
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
public class UserGroupRoleServiceImpl extends ServiceImpl<UserGroupRoleMapper, UserGroupRole> implements IUserGroupRoleService {

    @Override
    public void syncRoles(Long groupId, List<Long> roleIds) {
        EntityWrapper<UserGroupRole> ew = new EntityWrapper<>();
        ew.setSqlSelect("role_id").eq("group_id", groupId);
        List<Object> existIds = ListUtils.typedList(baseMapper.selectObjs(ew), Long.class);

        List<Long> intersection = ListUtils.intersection(roleIds, existIds);
        List<Long> toAddIds = ListUtils.subtract(roleIds, intersection);
        List<Long> toRemoveIds =ListUtils.subtract(existIds, intersection);

        if (!toRemoveIds.isEmpty()) {
            EntityWrapper<UserGroupRole> ewToRemove = new EntityWrapper<UserGroupRole>();
            ewToRemove.eq("group_id", groupId).in("role_id", toRemoveIds);
            delete(ewToRemove);
        }

        if (!toAddIds.isEmpty()) {
            List<UserGroupRole> toAdd = new ArrayList<>();
            toAddIds.forEach((item) -> toAdd.add(new UserGroupRole(groupId, item)));

            insertBatch(toAdd);
        }

    }

    @Override
    public Set<Long> groupsWithRole(Long roleId) {
        Set<Long> groupIds = new HashSet<>();
        if (roleId == null) return groupIds;

        EntityWrapper<UserGroupRole> ew = new EntityWrapper<UserGroupRole>();
        ew.setSqlSelect("group_id").eq("role_id", roleId);
        List<Long> ids = ListUtils.typedList(baseMapper.selectObjs(ew), Long.class);
        ids.forEach(id -> groupIds.add(id));

        return groupIds;
    }

    @Override
    public Map<Long, Set<Long>> transToUsersRoles(Map<Long, Set<Long>> usersUserGroups) {
        Map<Long, Set<Long>> usersRolesMap = new HashMap<>();
        usersUserGroups.keySet().forEach(item -> usersRolesMap.put(item, new HashSet<>()));

        List<Long> groupIds = new ArrayList<>();
        usersUserGroups.values().forEach(item -> groupIds.addAll(item));

        if (groupIds.size() == 0) {
            return usersRolesMap;
        }

        EntityWrapper<UserGroupRole> ew = new EntityWrapper<>();
        ew.setSqlSelect("group_id", "role_id").in("group_id", groupIds);
        List<UserGroupRole> items = selectList(ew);
        if (items.size() == 0) {
            return usersRolesMap;
        }

        Map<Long, List<Long>> groupRoles = new HashMap<>();
        items.forEach(item -> {
            Long key = item.getGroupId();
            if (!groupRoles.containsKey(key)) {
                groupRoles.put(key, new ArrayList<>());
            }
            groupRoles.get(key).add(item.getRoleId());
        });

        usersUserGroups.keySet().forEach(item -> {
            Set<Long> values = usersUserGroups.get(item);
            values.forEach(groupId -> {
                if (groupRoles.containsKey(groupId)) {
                    usersRolesMap.get(item).addAll(groupRoles.get(groupId));
                }
            });
        });

        return usersRolesMap;
    }

    @Override
    public Set<Long> transToUserRoleIds(Set<Long> userGroupIds) {
        Set<Long> roleIds = new HashSet<>();

        if (userGroupIds.size() == 0) {
            return roleIds;
        }

        EntityWrapper<UserGroupRole> ew = new EntityWrapper<>();
        ew.setSqlSelect("group_id", "role_id").in("group_id", userGroupIds);
        List<UserGroupRole> items = selectList(ew);
        items.forEach(item -> roleIds.add(item.getRoleId()));

        return roleIds;
    }
}
