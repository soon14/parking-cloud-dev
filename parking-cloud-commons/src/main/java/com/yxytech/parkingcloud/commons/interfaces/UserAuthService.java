package com.yxytech.parkingcloud.commons.interfaces;

import com.yxytech.parkingcloud.commons.entity.UserIdentity;

public interface UserAuthService {

    UserAuthEntity getUserByLoginName(String name);

    boolean validateUserPassword(UserAuthEntity user, String password);

    UserAuthEntity getUserByUserIdentity(UserIdentity userIdentity);

    UserAuthEntity getUserByAccountId(Long accountId);
}
