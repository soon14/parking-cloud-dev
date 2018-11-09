package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Customer;
import com.yxytech.parkingcloud.core.service.ICustomerService;
import com.yxytech.parkingcloud.platform.config.Access;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController{

    @Autowired
    private ICustomerService customerService;

    @GetMapping("")
    public ApiResponse<Object> index(@RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
                                     @RequestParam(value = "size",required = false,defaultValue = pageSize)Integer size,
                                     @RequestParam(value = "name",required = false)String name,
                                     @RequestParam(value = "nickname",required = false)String nickname){

        Page<Customer> p = new Page<>(page,size);

        EntityWrapper<Customer> ew = new EntityWrapper<>();
        ew.like(StringUtils.isNotBlank(name),"name",name);
        ew.like(StringUtils.isNotBlank(nickname),"nickname",nickname);
        ew.orderBy("id",true);

        p = customerService.selectPage(p,ew);

        return apiSuccess(p);
    }
}
