package com.yxytech.parkingcloud.core.utils;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import push.AndroidNotification;
import push.PushClient;
import push.android.AndroidUnicast;

import java.util.logging.Logger;

@Component
public class UMengPush {

    @Value("${umeng.push.appkey}")
    private String appKey;

    @Value("${umeng.push.appMasterSecret}")
    private String appMasterSecret;

    private PushClient pushClient;

    @Bean
    private PushClient getPushClient() {
        if (pushClient == null) {
            pushClient = new PushClient();
        }
        return pushClient;
    }

    /**
     * 发送android notification
     * @param pushToken
     * @param title
     * @param content
     * @throws Exception
     */
    public boolean sendAndroidNotification(String pushToken, String title, String content) {

        boolean ret = true;
        try {
            AndroidUnicast unicast = new AndroidUnicast(appKey, appMasterSecret);
            unicast.setDeviceToken(pushToken);
            unicast.setTicker(title);
            unicast.setTitle(title);
            unicast.setText(content);
            unicast.goAppAfterOpen();
            unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
            unicast.setProductionMode();
            ret = getPushClient().send(unicast);
        } catch (Exception e) {
            LoggerFactory.getLogger(UMengPush.class).error(e.getMessage());
            ret = false;
        }

        return ret;
    }
}
