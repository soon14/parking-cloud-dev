package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.UserGroupArea;
import com.yxytech.parkingcloud.core.mapper.UserGroupAreaMapper;
import com.yxytech.parkingcloud.core.service.IUserGroupAreaService;
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
 * @since 2017-11-10
 */
@Service
public class UserGroupAreaServiceImpl extends ServiceImpl<UserGroupAreaMapper, UserGroupArea> implements IUserGroupAreaService {

    @Override
    public void syncAreas(Long groupId, List<Long> areaIds) {
        EntityWrapper<UserGroupArea> ew = new EntityWrapper<>();
        ew.setSqlSelect("area_id").eq("group_id", groupId);
        List<Object> existRoleIds = ListUtils.typedList(baseMapper.selectObjs(ew), Long.class);

        List<Long> intersection = ListUtils.intersection(areaIds, existRoleIds);
        List<Long> toAddIds = ListUtils.subtract(areaIds, intersection);
        List<Long> toRemoveIds = ListUtils.subtract(existRoleIds, intersection);

        if (!toRemoveIds.isEmpty()) {
            EntityWrapper<UserGroupArea> ewToRemove = new EntityWrapper<>();
            ewToRemove.eq("group_id", groupId).in("area_id", toRemoveIds);
            delete(ewToRemove);
        }

        if (!toAddIds.isEmpty()) {
            List<UserGroupArea> toAdd = new ArrayList<>();
            toAddIds.forEach((item) -> toAdd.add(new UserGroupArea(groupId, item)));

            insertBatch(toAdd);
        }
    }

}
