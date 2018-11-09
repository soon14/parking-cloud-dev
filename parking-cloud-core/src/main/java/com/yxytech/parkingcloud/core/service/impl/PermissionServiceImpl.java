package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Permission;
import com.yxytech.parkingcloud.core.mapper.PermissionMapper;
import com.yxytech.parkingcloud.core.service.IPermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Override
    public String validatePermissionIds(List<Long> permissionIds) {
        String errorMsg = null;
        EntityWrapper<Permission> ew = new EntityWrapper<>();
        ew.setSqlSelect("id").in("id", permissionIds);
        List<Object> existIds = baseMapper.selectObjs(ew);

        if (permissionIds.size() > existIds.size()) {
            existIds.forEach(item -> permissionIds.remove(item));
            errorMsg = "权限id不存在：" + permissionIds.toString();
        }
        return errorMsg;
    }

    @Override
    public List<String> getPermissionCodesByUser(Long userId) {
        return baseMapper.getAllPermissionCodesByUser(userId);
    }
}
