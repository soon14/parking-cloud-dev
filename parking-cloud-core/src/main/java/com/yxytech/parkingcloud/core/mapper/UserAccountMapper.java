package com.yxytech.parkingcloud.core.mapper;

import com.yxytech.parkingcloud.commons.entity.UserIdentity;
import com.yxytech.parkingcloud.core.entity.UserAccount;
import com.yxytech.parkingcloud.core.utils.SuperMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author cj
 * @since 2017-10-20
 */
public interface UserAccountMapper extends SuperMapper<UserAccount> {

    UserAccount getUserByUserIdentity(UserIdentity userIdentity);
}