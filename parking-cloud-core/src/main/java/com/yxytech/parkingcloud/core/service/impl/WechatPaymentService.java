package com.yxytech.parkingcloud.core.service.impl;

import com.yxytech.parkingcloud.core.service.IPaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class WechatPaymentService implements IPaymentService {

    @Value("${payment.wechat.appId}")
    private String appId;

    @Value("${payment.wechat.mchId}")
    private String mchId;

    @Value("${payment.wechat.apiKey}")
    private String apiKey;

    @Override
    public String getSign(Object object, String apiKey) {
        String result = "";
        Field[] fields = object.getClass().getDeclaredFields();

        // 排序
        for (int i = 0; i < fields.length; i++) {
            for (int j = i + 1; j < fields.length; j++) {
                if (fields[i].getName().toLowerCase().compareTo(fields[j].getName().toLowerCase()) > 0) {
                    Field tmp = fields[i];
                    fields[i] = fields[j];
                    fields[j] = tmp;
                }
            }
        }

        // 拼接
        for (Field field : fields) {
            field.setAccessible(true);

            String filedName = field.getName();
            Object filedValue = null;

            try {
                filedValue = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (filedValue == null) {
                continue;
            }

            result += filedName.toLowerCase() + "=" + filedValue + "&";
        }

        result = result + "key=" + apiKey;

        String md5Result = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(result.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();

            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }

            md5Result = sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return md5Result;
    }

    /**
     * 生成随机数
     * @return
     */
    @Override
    public String generateString() {
        String operator = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String result = "";
        Integer length = operator.length();

        for (int i = 0; i < 32; i++) {
            int index = (int) (Math.random() * length);

            result += operator.substring(index, index + 1);
        }

        return result;
    }

    @Override
    public Map<String, String> getConfigInfo() {
        return new HashMap<String, String>() {{
            put("appId", appId);
            put("apiKey", apiKey);
            put("mchId", mchId);
        }};
    }
}
