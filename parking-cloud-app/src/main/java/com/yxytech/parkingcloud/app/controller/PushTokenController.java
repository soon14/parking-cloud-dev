package com.yxytech.parkingcloud.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.UserPushToken;
import com.yxytech.parkingcloud.core.enums.InUseEnum;
import com.yxytech.parkingcloud.core.service.IUserPushTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/push")
public class PushTokenController extends BaseController{

    @Autowired
    private IUserPushTokenService iUserPushTokenService;

    @PostMapping("/register_push_token")
    public ApiResponse<Object> registerPushToken(@RequestParam(value = "push_token",defaultValue = "",required = false) String push_token)
    {

        Long userId = getCurrentUser().getId();

        EntityWrapper<UserPushToken> ew = new EntityWrapper<>();
        ew.eq("user_id", userId);
        UserPushToken pushToken1 = iUserPushTokenService.selectOne(ew);
        if(pushToken1==null){
            UserPushToken pushToken = new UserPushToken();
            pushToken.setUserId(userId);
            pushToken.setInUse(InUseEnum.IN_USE);
            pushToken.setUsedTime(new Date());
            pushToken.setPushToken(push_token);
            iUserPushTokenService.insert(pushToken);
        }else{
            pushToken1.setPushToken(push_token);
            pushToken1.setInUse(InUseEnum.IN_USE);
            iUserPushTokenService.updateById(pushToken1);
        }
        return apiSuccess(null);
    }

    @PostMapping("unregister_push_token")
    public ApiResponse<Object> unregisterPushToken()
    {
        Long userId = getCurrentUser().getId();
        EntityWrapper<UserPushToken> ew = new EntityWrapper<>();
        ew.eq("user_id", userId);
        UserPushToken pushToken = iUserPushTokenService.selectOne(ew);
        if(pushToken == null){
            return apiFail("用户没有注册pushToken");
        }else{
            pushToken.setInUse(InUseEnum.NOT_USE);
            pushToken.setUnusedTime(new Date());
            iUserPushTokenService.updateById(pushToken);
        }
        return apiSuccess(null);
    }
}
