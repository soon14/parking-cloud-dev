package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxytech.parkingcloud.core.entity.OrgParking;
import com.yxytech.parkingcloud.core.entity.OrgRole;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.entity.Role;
import com.yxytech.parkingcloud.core.mapper.OrgRoleMapper;
import com.yxytech.parkingcloud.core.service.IOrgRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.service.IRoleService;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-12-22
 */
@Service
public class OrgRoleServiceImpl extends ServiceImpl<OrgRoleMapper, OrgRole> implements IOrgRoleService {

    @Autowired
    private IRoleService roleService;

    @Override
    public List<Long> findBindRoleIds(Long orgId) {
        EntityWrapper<OrgRole> ew = new EntityWrapper<>();
        ew.setSqlSelect("role_id").eq("org_id",orgId);
        List<Long> roleIds = ListUtils.typedList(baseMapper.selectObjs(ew),Long.class);

        return roleIds;
    }

    @Override
    public List<Role> findBindRoles(Long orgId) {
        List<Long> roleIds = findBindRoleIds(orgId);
        List<Role> roles = new ArrayList<>();

        if (roleIds.size() > 0) {
            EntityWrapper<Role> ew = new EntityWrapper<>();
            ew.in("id", roleIds);
            roles = roleService.selectList(ew);
        }

        return roles;
    }

    @Override
    public void syncRoles(List<Long> roleIds, Long orgId) {
        EntityWrapper<OrgRole> ew = new EntityWrapper<>();
        ew.setSqlSelect("role_id").eq("org_id",orgId);
        List<Object> existRoleIds = ListUtils.typedList(baseMapper.selectObjs(ew),Long.class);

        List<Long> intersection = ListUtils.intersection(roleIds, existRoleIds);
        List<Long> toRemoveIds = ListUtils.subtract(existRoleIds, intersection);
        List<Long> toAddIds = ListUtils.subtract(roleIds, intersection);

        if(!toAddIds.isEmpty()){
            List<OrgRole> toAdd = new ArrayList<>();
            toAddIds.forEach((item) -> toAdd.add(new OrgRole(orgId, item)));

            insertBatch(toAdd);
        }

        if(!toRemoveIds.isEmpty()){
            EntityWrapper<OrgRole> ewToRemove = new EntityWrapper<>();
            ewToRemove.eq("org_id",orgId).in("role_id",toRemoveIds);
            delete(ewToRemove);
        }
    }
}
