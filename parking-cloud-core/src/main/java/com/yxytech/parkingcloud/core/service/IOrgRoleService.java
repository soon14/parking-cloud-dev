package com.yxytech.parkingcloud.core.service;

import com.yxytech.parkingcloud.core.entity.OrgRole;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-12-22
 */
public interface IOrgRoleService extends IService<OrgRole> {

    /**
     * 查询单位绑定的角色ids
     * @param orgId
     * @return
     */
    List<Long> findBindRoleIds(Long orgId);

    /**
     * 查询单位绑定的角色
     * @param orgId
     * @return
     */
    List<Role> findBindRoles(Long orgId);

    /**
     * 同步单位绑定的角色
     * @param roleIds
     * @param orgId
     */
    void syncRoles(List<Long> roleIds, Long orgId);
	
}
