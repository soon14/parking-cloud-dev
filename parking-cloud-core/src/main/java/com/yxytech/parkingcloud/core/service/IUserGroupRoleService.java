package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.UserGroupRole;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-11-11
 */
public interface IUserGroupRoleService extends IService<UserGroupRole> {

    void syncRoles(Long groupId, List<Long> roleIds);

    Set<Long> groupsWithRole(Long roleId);

    /**
     * 多个用户的用户组转换的角色id列表的Map
     * @param usersUserGroups
     * @return
     */
    Map<Long, Set<Long>> transToUsersRoles(Map<Long, Set<Long>> usersUserGroups);

    /**
     * 单个用户的用户组转换的角色id列表
     * @param userGroupIds
     * @return
     */
    Set<Long> transToUserRoleIds(Set<Long> userGroupIds);
}
