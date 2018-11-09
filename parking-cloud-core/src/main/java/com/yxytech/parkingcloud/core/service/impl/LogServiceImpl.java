package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxytech.parkingcloud.core.entity.Log;
import com.yxytech.parkingcloud.core.entity.User;
import com.yxytech.parkingcloud.core.mapper.LogMapper;
import com.yxytech.parkingcloud.core.service.ILogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.service.IUserService;
import com.yxytech.parkingcloud.core.utils.YXYServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2018-01-22
 */
@Service
public class LogServiceImpl extends YXYServiceImpl<LogMapper, Log> implements ILogService {

    @Autowired
    private IUserService userService;

    @Override
    public List<String> getUserIds(String name) {
        EntityWrapper<User> ewUser = new EntityWrapper<>();
        ewUser.setSqlSelect("id").like(StringUtils.isNotBlank(name),"name",name);
        List<Object> userIdsObj = userService.selectObjs(ewUser);
        List<String> userIds= new ArrayList<>();
        if(!userIdsObj.isEmpty()){
            for(Object userIdObj : userIdsObj){
                if(userIdObj == null)
                    continue;
                userIds.add(userIdObj.toString());
            }
        }
        return userIds;
    }
}
