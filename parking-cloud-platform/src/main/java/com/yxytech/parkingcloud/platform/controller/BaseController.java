package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxytech.parkingcloud.commons.AbstractController;
import com.yxytech.parkingcloud.commons.entity.UserContext;
import com.yxytech.parkingcloud.core.entity.User;
import com.yxytech.parkingcloud.core.entity.UserAccount;
import com.yxytech.parkingcloud.core.service.IUserAccountService;
import com.yxytech.parkingcloud.core.service.IUserService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController extends AbstractController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserAccountService userAccountService;

    // 分页大小
    protected static final String pageSize = "10";

    protected User getCurrentUser() {
        UserContext context = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userService.selectById(Long.parseLong(context.getUserId()));
    }

    protected Map<String, Object> userInfo() {
        UserContext context = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Map<String, Object> data = new HashMap<>();

        User user = userService.selectById(Long.parseLong(context.getUserId()));

        EntityWrapper<UserAccount> ew = new EntityWrapper<>();

        ew.eq("user_id",Long.parseLong(context.getUserId()));

        UserAccount userAccount = userAccountService.selectOne(ew);

        data.put("user", user);
        data.put("userAccount", userAccount);

        return data;
    }

    protected void validate(BindingResult br) throws BindException {
        if (br.hasErrors()) {
            throw new BindException(br);
        }
    }

    protected void  notFound(Object model, String errMsg) throws NotFoundException {
        if (model == null) {
            throw new NotFoundException(errMsg);
        }
    }

    protected List nullToArrayList(List list) {
        if (list == null) {
            return new ArrayList();
        }

        return list;
    }
}
