package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.WechatPaymentSetting;
import com.yxytech.parkingcloud.core.mapper.WechatPaymentSettingMapper;
import com.yxytech.parkingcloud.core.service.IWechatPaymentSettingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwd
 * @since 2017-11-15
 */
@Service
public class WechatPaymentSettingServiceImpl extends ServiceImpl<WechatPaymentSettingMapper, WechatPaymentSetting> implements IWechatPaymentSettingService {

    @Value("${payment.wechat.appId}")
    private String appId;

    @Value("${payment.wechat.mchId}")
    private String mchId;

    @Value("${payment.wechat.apiKey}")
    private String apiKey;

    @Value("${payment.wechat.cert}")
    private String certPath;

    @Value("${payment.wechat.certPass}")
    private String password;

    @Override
    public WechatPaymentSetting getPaymentSetting(Long parkingId) {
        WechatPaymentSetting wechatPaymentSettingParam = new WechatPaymentSetting();
        wechatPaymentSettingParam.setParkingId(parkingId);

        WechatPaymentSetting wechatPaymentSetting = baseMapper.selectOne(wechatPaymentSettingParam);

        if (wechatPaymentSetting == null) {
            wechatPaymentSetting = new WechatPaymentSetting();

            wechatPaymentSetting.setAppId(appId);
            wechatPaymentSetting.setMchId(mchId);
            wechatPaymentSetting.setApiKey(apiKey);
            wechatPaymentSetting.setCertificationPath(certPath);
            wechatPaymentSetting.setPassword(password);
        }

        return wechatPaymentSetting;
    }
}
