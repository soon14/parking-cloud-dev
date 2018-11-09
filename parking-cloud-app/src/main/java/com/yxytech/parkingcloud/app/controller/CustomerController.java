package com.yxytech.parkingcloud.app.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxytech.parkingcloud.app.controller.form.CustomerInfoForm;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Customer;
import com.yxytech.parkingcloud.core.entity.CustomerAccount;
import com.yxytech.parkingcloud.core.service.ICustomerAccountService;
import com.yxytech.parkingcloud.core.service.ICustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 客户信息控制层
 */
@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController{

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ICustomerAccountService customerAccountService;

    /**
     * 查看用户个人信息
     * @return
     */
    @GetMapping("/owner")
    public ApiResponse<Object> owner(){
        // 获取当前登录用户对象
        Customer currentCustomer = getCurrentUser();
        // 根据当前登录用户ID查询用户详细信息
        EntityWrapper<CustomerAccount> ew = new EntityWrapper<>();
        ew.eq("user_id", currentCustomer.getId());
        CustomerAccount customerAccount = customerAccountService.selectOne(ew);

        CustomerInfoForm customerInfo = new CustomerInfoForm();
        BeanUtils.copyProperties(currentCustomer,customerInfo);
        if (customerAccount != null) {
            BeanUtils.copyProperties(customerAccount,customerInfo);
        }
        return this.apiSuccess(customerInfo);
    }

    /**
     * 修改用户信息
     * @param
     * @return
     */
    @PutMapping("/updateCustomer")
    @Transactional
    public ApiResponse<Object> updateCustomer(@Valid @RequestBody CustomerInfoForm customerInfo, BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        // 获取当前登录用户对象
        Customer currentCustomer = getCurrentUser();
        // 获取登录用户账户ID
        Long userId = currentCustomer.getId();
        EntityWrapper<CustomerAccount> ew = new EntityWrapper<>();
        ew.eq("user_id", userId);
        CustomerAccount customerAccount = customerAccountService.selectOne(ew);

        BeanUtils.copyProperties(customerInfo, currentCustomer, "id");
        customerService.updateAllColumnById(currentCustomer);

        if (customerAccount != null) {
            BeanUtils.copyProperties(customerInfo, customerAccount, "id");
            customerAccountService.updateById(customerAccount);
        } else {
            Wrapper<CustomerAccount> wrapper = new EntityWrapper<>();
            wrapper.eq("mobile", customerInfo.getMobile())
                    .or("email", customerInfo.getEmail());

            List<CustomerAccount> accounts = customerAccountService.selectList(wrapper);

            if (accounts != null && accounts.size() > 0) {
                return this.apiFail("邮箱或手机号码重复!");
            }

            customerAccount = new CustomerAccount();
            customerAccount.setUserId(userId);
            BeanUtils.copyProperties(customerInfo, customerAccount, "id");
            customerAccountService.insert(customerAccount);
        }

        return this.apiSuccess(null);
    }

}
