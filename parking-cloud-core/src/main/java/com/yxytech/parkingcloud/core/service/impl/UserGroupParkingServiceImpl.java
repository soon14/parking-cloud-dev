package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.UserGroupParking;
import com.yxytech.parkingcloud.core.mapper.UserGroupParkingMapper;
import com.yxytech.parkingcloud.core.service.IUserGroupParkingService;
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
public class UserGroupParkingServiceImpl extends ServiceImpl<UserGroupParkingMapper, UserGroupParking> implements IUserGroupParkingService {


    @Override
    public void syncParkings(Long groupId, List<Long> parkingIds) {
        EntityWrapper<UserGroupParking> ew = new EntityWrapper<>();
        ew.setSqlSelect("parking_id").eq("group_id", groupId);
        List<Object> existRoleIds = ListUtils.typedList(baseMapper.selectObjs(ew), Long.class);

        List<Long> intersection = ListUtils.intersection(parkingIds, existRoleIds);
        List<Long> toAddIds = ListUtils.subtract(parkingIds, intersection);
        List<Long> toRemoveIds = ListUtils.subtract(existRoleIds, intersection);

        /*if (!toRemoveIds.isEmpty()) {
            EntityWrapper<UserGroupParking> ewToRemove = new EntityWrapper<>();
            ewToRemove.eq("group_id", groupId).in("parking_id", toRemoveIds);
            delete(ewToRemove);
        }*/

        if (!toAddIds.isEmpty()) {
            List<UserGroupParking> toAdd = new ArrayList<>();
            toAddIds.forEach((item) -> toAdd.add(new UserGroupParking(groupId, item)));

            insertBatch(toAdd);
        }
    }


}
