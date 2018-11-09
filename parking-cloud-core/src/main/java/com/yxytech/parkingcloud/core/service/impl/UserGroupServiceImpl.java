package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxytech.parkingcloud.core.entity.UserGroup;
import com.yxytech.parkingcloud.core.mapper.UserGroupMapper;
import com.yxytech.parkingcloud.core.service.IUserGroupService;
import com.yxytech.parkingcloud.core.utils.YXYServiceImpl;
import org.springframework.stereotype.Service;

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
public class UserGroupServiceImpl extends YXYServiceImpl<UserGroupMapper, UserGroup> implements IUserGroupService {

    @Override
    public String validateGroupIds(Long orgId, List<Long> groupIds) {
        String errorMsg = null;
        EntityWrapper<UserGroup> ew = new EntityWrapper<>();
        ew.setSqlSelect("id").eq("org_id", orgId).in("id", groupIds);
        List<Object> existIds = baseMapper.selectObjs(ew);

        if (groupIds.size() > existIds.size()) {
            existIds.forEach(item -> groupIds.remove(item));
            errorMsg = "用户组不存在：" + groupIds.toString();
        }
        return errorMsg;
    }

    @Override
    public String validate(Long orgId, String name) {
        EntityWrapper<UserGroup> ew = new EntityWrapper<>();
        ew.eq("org_id",orgId);
        ew.eq("name",name);
        if(baseMapper.selectCount(ew) > 0)
            return "同一单位下用户组唯一";

        return null;
    }
}
