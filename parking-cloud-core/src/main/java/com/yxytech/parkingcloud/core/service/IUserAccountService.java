package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.commons.interfaces.UserAuthEntity;
import com.yxytech.parkingcloud.core.entity.UserAccount;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-20
 */
public interface IUserAccountService extends IService<UserAccount> {

    String invalidNewAccount(UserAccount account);

    String invalidUpdateAccount(UserAccount account);

    String encodePassword(String password);

    UserAccount create(UserAccount account);

    boolean validateUserPassword(UserAuthEntity user, String password);

    UserAccount selectByUserId(Long userId);

    String invalidUpdate(UserAccount userAccount);
	
}
