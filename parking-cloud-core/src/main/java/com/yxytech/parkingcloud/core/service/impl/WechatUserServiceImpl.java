package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.WechatUser;
import com.yxytech.parkingcloud.core.mapper.WechatUserMapper;
import com.yxytech.parkingcloud.core.service.IWechatUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-12-07
 */
@Service
public class WechatUserServiceImpl extends ServiceImpl<WechatUserMapper, WechatUser> implements IWechatUserService {

}
