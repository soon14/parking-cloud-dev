package com.yxytech.parkingcloud.core.service;

import com.avos.avoscloud.AVException;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.commons.interfaces.UserAuthEntity;
import com.yxytech.parkingcloud.core.entity.CustomerAccount;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-20
 */
public interface ICustomerAccountService extends IService<CustomerAccount> {

    /**
     * 发送登录短信验证码
     * @param mobile
     * @return
     */
    void sendLoginSMSCode(String mobile) throws AVException;


    /**
     * 验证短信验证码
     * @param mobile
     * @param code
     * @throws AVException
     */
    void verifySMSCode(String mobile, String code) throws AVException;

    UserAuthEntity getUserByLoginName(String name);

}
