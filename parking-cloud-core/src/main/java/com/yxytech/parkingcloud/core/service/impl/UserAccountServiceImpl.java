package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.commons.entity.UserIdentity;
import com.yxytech.parkingcloud.commons.interfaces.UserAuthEntity;
import com.yxytech.parkingcloud.commons.interfaces.UserAuthService;
import com.yxytech.parkingcloud.core.entity.UserAccount;
import com.yxytech.parkingcloud.core.mapper.UserAccountMapper;
import com.yxytech.parkingcloud.core.service.IUserAccountService;
import org.apache.commons.lang.StringUtils;
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
@Service("com.yxytech.parkingcloud.core.service.impl.UserAccountServiceImpl")
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount>
        implements IUserAccountService, UserAuthService {

    @Override
    public UserAuthEntity getUserByLoginName(String name) {
        return null;
    }

    @Override
    public boolean validateUserPassword(UserAuthEntity user, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public UserAccount selectByUserId(Long userId) {
        UserAccount account = new UserAccount();
        account.setUserId(userId);

        return baseMapper.selectOne(account);
    }

    @Override
    public String invalidUpdate(UserAccount userAccount) {
        EntityWrapper<UserAccount> ew1 = new EntityWrapper<>();
        ew1.setSqlSelect("id");
        ew1.eq("employee_number",userAccount.getEmployeeNumber());
        ew1.eq("org_id",userAccount.getOrgId());
        Object existId1 =  baseMapper.selectObjs(ew1).get(0);
        if(existId1 != null && Long.valueOf(existId1.toString()) != userAccount.getId())
            return "工号已被占用";

        EntityWrapper<UserAccount> ew2 = new EntityWrapper<>();
        ew2.setSqlSelect("id");
        ew2.eq("is_active",true);
        ew2.eq("mobile",userAccount.getMobile());
        Object existId2 = baseMapper.selectObjs(ew2).get(0);
        if(existId2 != null && Long.valueOf(existId2.toString()) != userAccount.getId())
            return "手机号已被占用";

        EntityWrapper<UserAccount> ew3 = new EntityWrapper<>();
        ew3.setSqlSelect("id");
        ew3.eq("is_active",true);
        ew3.eq("email",userAccount.getEmail());
        Object existId3 = baseMapper.selectObjs(ew3).get(0);
        if(existId3 != null && Long.valueOf(existId3.toString()) != userAccount.getId())
            return "邮箱已被占用";

        return null;
    }

    @Override
    public UserAuthEntity getUserByUserIdentity(UserIdentity userIdentity) {
        return (UserAuthEntity) baseMapper.getUserByUserIdentity(userIdentity);
    }

    @Override
    public UserAuthEntity getUserByAccountId(Long accountId) {
        return (UserAuthEntity) baseMapper.selectById(accountId);
    }

    /**
     * 判断工号是否已存在
     * @param account
     * @return
     */
    private boolean isEmployeeNumberExisted(UserAccount account) {
        if (StringUtils.isEmpty(account.getEmployeeNumber())) {
            return false;
        }
        EntityWrapper<UserAccount> ew = new EntityWrapper<>();
        ew.eq("org_id", account.getOrgId());
        ew.eq("employee_number", account.getEmployeeNumber());

        return baseMapper.selectCount(ew) > 0;
    }

    /**
     * 判断手机号是否已使用
     * @param account
     * @return
     */
    private boolean isMobileInUse(UserAccount account) {
        if (StringUtils.isEmpty(account.getMobile())) {
            return false;
        }

        EntityWrapper<UserAccount> ew = new EntityWrapper<>();
        ew.eq("is_active", true);
        ew.eq("mobile", account.getMobile());

        return baseMapper.selectCount(ew) > 0;
    }

    /**
     * 判断邮箱是否已使用
     * @param account
     * @return
     */
    private boolean isEmailInUse(UserAccount account) {
        if (StringUtils.isEmpty(account.getEmail())) {
            return false;
        }

        EntityWrapper<UserAccount> ew = new EntityWrapper<>();
        ew.eq("is_active", true);
        ew.eq("email", account.getEmail());

        return baseMapper.selectCount(ew) > 0;
    }

    /**
     * 判断账号唯一标识存在
     * @param account
     * @return
     */
    private boolean isAccountIdentityValid(UserAccount account) {
        // 验证用户手机，电子邮箱，工号不能同时为空
        if (StringUtils.isEmpty(account.getEmail())
                && StringUtils.isEmpty(account.getMobile())
                && StringUtils.isEmpty(account.getEmployeeNumber())) {
            return false;
        }
        return true;
    }


    @Override
    public String invalidNewAccount(UserAccount account) {
        String errorMsg = null;
        if (!isAccountIdentityValid(account)) {
            errorMsg = "用户手机号，电子邮箱，工号不能同时为空";
        } else if (isMobileInUse(account)) {
            errorMsg = "手机号已被使用";
        } else if (isEmailInUse(account)) {
            errorMsg = "邮箱已被使用";
        } else if (isEmployeeNumberExisted(account)) {
            errorMsg = "工号已被使用";
        }

        return errorMsg;
    }

    @Override
    public String invalidUpdateAccount(UserAccount account) {
        String errorMsg = null;
        if (!isAccountIdentityValid(account)) {
            errorMsg = "用户手机号，电子邮箱，工号不能同时为空";
        }

        return errorMsg;
    }

    @Override
    public String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    @Override
    public UserAccount create(UserAccount account) {
        account.setIsActive(true);
        account.setPassword(encodePassword(account.getPassword()));
        insert(account);

        return account;
    }

}
