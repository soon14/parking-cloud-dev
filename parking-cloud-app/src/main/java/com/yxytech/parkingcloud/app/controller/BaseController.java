package com.yxytech.parkingcloud.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxytech.parkingcloud.commons.AbstractController;
import com.yxytech.parkingcloud.commons.entity.UserContext;
import com.yxytech.parkingcloud.core.entity.Customer;
import com.yxytech.parkingcloud.core.service.ICustomerService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public class BaseController extends AbstractController {

    @Autowired
    private ICustomerService customerService;

    protected void validate(BindingResult br) throws BindException {
        if (br.hasErrors()) {
            throw new BindException(br);
        }
    }

    protected Customer getCurrentUser() {
        UserContext context = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EntityWrapper<Customer> ew = new EntityWrapper<>();
        ew.eq("id", Long.valueOf(context.getUserId()));

        Customer customer = customerService.selectOne(ew);

        if (customer == null) {
            throw new BadCredentialsException("错误的token！");
        }

        return customer;
    }

    void notFound(Object param, String msg) throws NotFoundException {
        if (param instanceof System) {
            if (StringUtils.isEmpty((String) param)) {
                throw new NotFoundException(msg);
            }
        } else {
            if (param == null) {
                throw new NotFoundException(msg);
            }
        }
    }
}
