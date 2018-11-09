package com.yxytech.parkingcloud.core.service.impl;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.commons.entity.UserIdentity;
import com.yxytech.parkingcloud.commons.interfaces.UserAuthEntity;
import com.yxytech.parkingcloud.commons.interfaces.UserAuthService;
import com.yxytech.parkingcloud.core.entity.CustomerAccount;
import com.yxytech.parkingcloud.core.mapper.CustomerAccountMapper;
import com.yxytech.parkingcloud.core.service.ICustomerAccountService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-10-20
 */
@Service("com.yxytech.parkingcloud.core.service.impl.CustomerAccountServiceImpl")
public class CustomerAccountServiceImpl extends ServiceImpl<CustomerAccountMapper, CustomerAccount>
        implements ICustomerAccountService, UserAuthService{

    @Override
    public UserAuthEntity getUserByLoginName(String name) {
        return (UserAuthEntity) baseMapper.getUserByLoginName(name);
    }

    @Override
    public boolean validateUserPassword(UserAuthEntity user, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public UserAuthEntity getUserByUserIdentity(UserIdentity userIdentity) {
        return (UserAuthEntity) baseMapper.getUserByUserIdentity(userIdentity);
    }

    @Override
    public void sendLoginSMSCode(String mobile) throws AVException {
//        AVOSCloud.requestSMSCode(mobile);
        AVOSCloud.requestSMSCode(mobile, "requestMobilePhoneVerify", null);
    }

    @Override
    public void verifySMSCode(String mobile, String code) throws AVException {
        AVOSCloud.verifySMSCode(code, mobile);
    }

    @Override
    public UserAuthEntity getUserByAccountId(Long accountId) {
        return (UserAuthEntity) baseMapper.selectById(accountId);
    }

}
