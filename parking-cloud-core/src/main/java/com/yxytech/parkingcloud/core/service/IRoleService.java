package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.Role;
import com.yxytech.parkingcloud.core.utils.YXYIService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-11-08
 */
public interface IRoleService extends IService<Role>, YXYIService<Role> {

    /**
     * 验证合法的角色id
     * @param roleIds
     * @return 验证错误消息，无错误为null
     */
    String validateRoleIds(List<Long> roleIds);

    Role roleByCode(String code);

    List<String> codesByIds(Collection<Long> roleIds);
}
