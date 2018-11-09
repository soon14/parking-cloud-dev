package com.yxytech.parkingcloud.app.controller;


import com.avos.avoscloud.AVException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxytech.parkingcloud.app.wechat.AccessToken;
import com.yxytech.parkingcloud.app.wechat.UserInfo;
import com.yxytech.parkingcloud.app.wechat.WechatAuthService;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.commons.entity.UserContext;
import com.yxytech.parkingcloud.core.entity.Customer;
import com.yxytech.parkingcloud.core.entity.CustomerAccount;
import com.yxytech.parkingcloud.core.entity.WechatUser;
import com.yxytech.parkingcloud.core.service.ICustomerAccountService;
import com.yxytech.parkingcloud.core.service.ICustomerService;
import com.yxytech.parkingcloud.core.service.IWechatUserService;
import com.yxytech.parkingcloud.security.model.token.JwtTokenFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Autowired
    private ICustomerAccountService customerAccountService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private JwtTokenFactory jwtTokenFactory;

    @Autowired
    private WechatAuthService wechatAuthService;

    @Autowired
    private IWechatUserService wechatUserService;

    /**
     * 发送登证短信验证码
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/sms", method = RequestMethod.POST)
    public ApiResponse<Object> sendLoginSMSCode(String mobile) {
        try {
            customerAccountService.sendLoginSMSCode(mobile);
        } catch (AVException e) {
            return this.apiFail(e.getMessage());
        }
        return this.apiSuccess(null);
    }

    /**
     * 手机验证码登录
     */
    @RequestMapping(value="/login_by_sms", method = RequestMethod.POST)
    @Transactional
    public ApiResponse<Object> loginBySMS(String mobile, String code) {
        try {
            customerAccountService.verifySMSCode(mobile, code);
        } catch (AVException e) {
            return this.apiFail(e.getMessage());
        }

        CustomerAccount account = (CustomerAccount) customerAccountService.getUserByLoginName(mobile);
        if (account == null) {
            account = new CustomerAccount();
            account.setMobile(mobile);
            account.setIsActive(true);

            Customer customer = new Customer();
            customerService.insert(customer);

            account.setUserId(customer.getId());
            customerAccountService.insert(account);
        }

        UserContext context = new UserContext(account.getUserId() + "", new ArrayList<>());

        return this.apiSuccess(jwtTokenFactory.createTokenData(context));
    }

    /**
     * 微信授权登录
     */
    @PostMapping("/login_by_wechat")
    @Transactional
    public ApiResponse<Object> loginByWechat(String code) throws JsonProcessingException {
        System.out.println("---" + code);
        AccessToken accessToken = wechatAuthService.queryAccessToken(code);
        if (accessToken == null || accessToken.getErrcode() != 0) {
            if (accessToken != null) {
                System.out.println(accessToken.getErrmsg());
            }
            return apiFail("获取AccessToken失败");
        }

        EntityWrapper<WechatUser> ew = new EntityWrapper<>();
        ew.eq("openid", accessToken.getOpenid());
        WechatUser wechatUser = wechatUserService.selectOne(ew);

        if (wechatUser == null) {
            UserInfo userInfo = wechatAuthService.queryUser(accessToken);
            if (userInfo == null || userInfo.getErrcode() != 0) {
                if (userInfo != null) {
                    System.out.println(userInfo.getErrmsg());
                }
                return apiFail("获取微信用户信息失败");
            }
            wechatUser = new WechatUser();
            BeanUtils.copyProperties(userInfo, wechatUser);
            wechatUser.setSex(null);
            Customer customer = new Customer(wechatUser);

            customerService.insert(customer);
            wechatUser.setCustomerId(customer.getId());
            ObjectMapper mapper = new ObjectMapper();
            wechatUser.setAccessToken(mapper.writeValueAsString(accessToken));
            wechatUserService.insert(wechatUser);
        }

        UserContext context = new UserContext(wechatUser.getCustomerId() + "", new ArrayList<>());

        return this.apiSuccess(jwtTokenFactory.createTokenData(context));
    }

}
