package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.WechatPaymentSetting;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-11-15
 */
public interface IWechatPaymentSettingService extends IService<WechatPaymentSetting> {
	WechatPaymentSetting getPaymentSetting(Long parkingId);
}
