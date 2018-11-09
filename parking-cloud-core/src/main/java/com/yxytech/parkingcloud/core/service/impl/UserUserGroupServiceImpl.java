package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.UserUserGroup;
import com.yxytech.parkingcloud.core.mapper.UserUserGroupMapper;
import com.yxytech.parkingcloud.core.service.IUserUserGroupService;
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
public class UserUserGroupServiceImpl extends ServiceImpl<UserUserGroupMapper, UserUserGroup> implements IUserUserGroupService {

    @Override
    public boolean hasGroup(Long id) {
        EntityWrapper<UserUserGroup> ew = new EntityWrapper<>();
        ew.eq("group_id", id);
        return baseMapper.selectCount(ew) > 0;
    }

    @Override
    public Map<Long, Set<Long>> queryUsersUserGroupsMap(Set<Long> userIds) {
        EntityWrapper<UserUserGroup> ew = new EntityWrapper<>();
        ew.setSqlSelect("group_id", "user_id").in("user_id", userIds);
        List<Map<String, Object>> items = selectMaps(ew);

        Map<Long, Set<Long>> userUserGroupsMap = new HashMap<>();

        userIds.forEach(item -> userUserGroupsMap.put(item, new HashSet<>()));

        items.forEach(item -> {
            userUserGroupsMap.get((Long)item.get("user_id")).add((Long)item.get("group_id"));
        });

        return userUserGroupsMap;
    }

    @Override
    public Set<Long> queryUserGroupIds(Long userId) {
        EntityWrapper<UserUserGroup> ew = new EntityWrapper<>();
        ew.setSqlSelect("group_id", "user_id").eq("user_id", userId);
        List<Map<String, Object>> items = selectMaps(ew);

        Set<Long> groupIds = new HashSet<>();
        items.forEach(item -> groupIds.add((Long)item.get("group_id")));

        return groupIds;
    }

    @Override
    public void syncGroups(Long userId, List<Long> groupIds) {
        EntityWrapper<UserUserGroup> ew = new EntityWrapper<>();
        ew.setSqlSelect("group_id").eq("user_id", userId);
        List<Object> existIds = ListUtils.typedList(baseMapper.selectObjs(ew), Long.class);

        List<Long> intersection = ListUtils.intersection(groupIds, existIds);
        List<Long> toAddIds = ListUtils.subtract(groupIds, intersection);
        List<Long> toRemoveIds = ListUtils.subtract(existIds, intersection);

        if (!toRemoveIds.isEmpty()) {
            EntityWrapper<UserUserGroup> ewToRemove = new EntityWrapper<>();
            ewToRemove.eq("user_id", userId).in("group_id", toRemoveIds);
            delete(ewToRemove);
        }

        if (!toAddIds.isEmpty()) {
            List<UserUserGroup> toAdd = new ArrayList<>();
            toAddIds.forEach((item) -> toAdd.add(new UserUserGroup(userId, item)));

            insertBatch(toAdd);
        }
    }
}
