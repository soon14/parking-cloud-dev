package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.User;
import com.yxytech.parkingcloud.core.exception.ServiceException;
import com.yxytech.parkingcloud.core.utils.YXYIService;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-20
 */
public interface IUserService extends IService<User>,YXYIService<User> {

    Page<User> selectUsersWithRolesGroups(Page<User> page, Map<String, Object> params);

    /**
     * 用户的角色 （分配的角色和所属组的角色的并集）
     * @return
     */
    Set<Long> userRoleIds(Long userId);

    /**
     * 判断是否需要数据隔离
     * @return
     */
    Boolean needDataIsolate(Long userId);

    /**
     * 获取隔离的单位sql
     * @return
     */
    String getIsolatedOrgsSql(Long userId) throws ServiceException;

    /**
     * 获取隔离的停车场sql
     * @return
     */
    String getIsolatedParkingsSql(Long userId) throws ServiceException;
}
