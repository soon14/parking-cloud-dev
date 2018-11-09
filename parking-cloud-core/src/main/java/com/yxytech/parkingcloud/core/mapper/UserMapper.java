package com.yxytech.parkingcloud.core.mapper;

import com.yxytech.parkingcloud.core.entity.User;
import com.yxytech.parkingcloud.core.utils.SuperMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author cj
 * @since 2017-10-20
 */
public interface UserMapper extends SuperMapper<User> {

    /**
     * 根据用户关联的角色或用户组查询用户
     * @param params
     * @return
     */
    List<User> selectUsersWithRolesGroups(Map<String, Object> params);


    Set<Long> selectUserRoleIds(Long userId);

}