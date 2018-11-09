package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.entity.PermissionResource;
import com.yxytech.parkingcloud.core.mapper.PermissionResourceMapper;
import com.yxytech.parkingcloud.core.service.IPermissionResourceService;
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
public class PermissionResourceServiceImpl extends ServiceImpl<PermissionResourceMapper, PermissionResource> implements IPermissionResourceService {


    @Override
    public void syncResource(Long permissionId, List<Long> resourceIds) {
        EntityWrapper<PermissionResource> ew = new EntityWrapper<>();

        ew.setSqlSelect("resource_id").eq("permission_id",permissionId);


        List<Object> existIds = ListUtils.typedList(baseMapper.selectObjs(ew),Long.class);

        List<Long> intersection = ListUtils.intersection(resourceIds,existIds);
        List<Long> toRemoveIds = ListUtils.subtract(existIds,intersection);
        List<Long> toAddIds = ListUtils.subtract(resourceIds,intersection);

        EntityWrapper<Organization> ewOrg = new EntityWrapper<>();

        if(!toAddIds.isEmpty()){
            List<PermissionResource> toAdd = new ArrayList<>();
            toAddIds.forEach((item) -> {
                toAdd.add(new PermissionResource(permissionId, item));
            });

            insertBatch(toAdd);
        }

        if(!toRemoveIds.isEmpty()){
            EntityWrapper<PermissionResource> ewToRemove = new EntityWrapper<>();
            ewToRemove.eq("permission_id",permissionId).in("resource_id",toRemoveIds);

            delete(ewToRemove);
        }
    }
}
