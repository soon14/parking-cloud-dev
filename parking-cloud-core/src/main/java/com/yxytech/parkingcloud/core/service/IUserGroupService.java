package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.UserGroup;
import com.yxytech.parkingcloud.core.utils.YXYIService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-11-11
 */
public interface IUserGroupService extends IService<UserGroup>, YXYIService<UserGroup> {

    /**
     * 检查用户组id的是否正确
     * @param orgId
     * @param groupIds
     * @return
     */
    String validateGroupIds(Long orgId, List<Long> groupIds);

    String validate(Long orgId,String name);
}
