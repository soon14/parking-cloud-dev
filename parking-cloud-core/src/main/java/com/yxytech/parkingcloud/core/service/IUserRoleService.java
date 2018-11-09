package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.UserRole;

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
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 查询多个用户角色id列表的Map
     * @param userIds
     * @return
     */
    Map<Long, Set<Long>> queryUsersRolesMap(Set<Long> userIds);

    /**
     * 查询单个用户的角色id列表
     * @param userId
     * @return
     */
    Set<Long> queryUserRoleIds(Long userId);

    /**
     * 更新用户角色绑定
     * @param id
     * @param roleIds
     * @return
     */
    void syncRoles(Long id, List<Long> roleIds);
}
