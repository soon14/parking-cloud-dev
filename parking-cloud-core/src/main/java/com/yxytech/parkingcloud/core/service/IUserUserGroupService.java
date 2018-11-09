package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.UserUserGroup;

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
public interface IUserUserGroupService extends IService<UserUserGroup> {

    boolean hasGroup(Long id);

    /**
     * 查询多个用户的用户组id列表的Map
     * @param userIds
     * @return
     */
    Map<Long,Set<Long>> queryUsersUserGroupsMap(Set<Long> userIds);

    /**
     * 查询单个用户的用户组id列表
     * @param userId
     * @return
     */
    Set<Long> queryUserGroupIds(Long userId);

    /**
     * 更新用户的用户组绑定关系
     * @param userId
     * @param groupIds
     */
    void syncGroups(Long userId, List<Long> groupIds);
}
