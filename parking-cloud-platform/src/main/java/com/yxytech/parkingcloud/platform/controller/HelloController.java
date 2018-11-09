package com.yxytech.parkingcloud.platform.controller;

import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.PushMessage;
import com.yxytech.parkingcloud.core.entity.User;
import com.yxytech.parkingcloud.core.service.IPushMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController extends BaseController {

    @Autowired
    private IPushMessageService pushMessageService;

    @GetMapping("/test_send_umeng_message/{id}")
    public ApiResponse<Object> hello(@PathVariable Long id) {
//        User u = getCurrentUser();
        PushMessage message = pushMessageService.selectById(id);
        if (message != null) {
            pushMessageService.sendUmengNotification(message);
        }

        return this.apiSuccess("");
    }


}
