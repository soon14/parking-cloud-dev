package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxytech.parkingcloud.core.entity.Role;
import com.yxytech.parkingcloud.core.mapper.RoleMapper;
import com.yxytech.parkingcloud.core.service.IRoleService;
import com.yxytech.parkingcloud.core.utils.YXYServiceImpl;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.SetUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-11-08
 */
@Service
public class RoleServiceImpl extends YXYServiceImpl<RoleMapper, Role> implements IRoleService {


    @Override
    public String validateRoleIds(List<Long> roleIds) {
        String errorMsg = null;
        EntityWrapper<Role> ew = new EntityWrapper<>();
        ew.setSqlSelect("id").in("id", roleIds);
        List<Object> existIds = baseMapper.selectObjs(ew);

        if (roleIds.size() > existIds.size()) {
            existIds.forEach(item -> roleIds.remove(item));
            errorMsg = "角色id不存在：" + roleIds.toString();
        }
        return errorMsg;
    }

    @Override
    public Role roleByCode(String code){
        EntityWrapper<Role> ew = new EntityWrapper<>();

        ew.eq("code", code);

        return baseMapper.selectList(ew).get(0);
    }

    @Override
    public List<String> codesByIds(Collection<Long> roleIds) {
        List<String> codes = new ArrayList<>();

        if (roleIds.size() > 0) {
            EntityWrapper ew = new EntityWrapper<Role>();
            ew.in("id", roleIds);
            ew.setSqlSelect("code");
            codes = ListUtils.typedList(baseMapper.selectObjs(ew), String.class);
        }

        return codes;
    }
}
