package com.yxytech.parkingcloud.app.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class WechatAuthService {

    private static final String WX_AUTH_LOGIN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private static final String WX_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";

    @Value("${wechat.login.appId}")
    private String appId;

    @Value("${wechat.login.appSecret}")
    private String appKey;

    /**
     * 用code换取AccessToken
     * @param code
     * @return
     */
    public AccessToken queryAccessToken(String code) {
        AccessToken accessToken = null;
        StringBuffer loginUrl = new StringBuffer();
        loginUrl.append(WX_AUTH_LOGIN_URL).append("?appid=")
                .append(appId).append("&secret=")
                .append(appKey).append("&code=").append(code)
                .append("&grant_type=authorization_code");
        String loginRet = get(loginUrl.toString());
        ObjectMapper mapper = new ObjectMapper();
        try {
            accessToken = mapper.readValue(loginRet, AccessToken.class);
            if (accessToken.getErrcode() == null) {
                accessToken.setErrcode(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }


    /**
     * 通过AccessToken查询用户信息
     * @param accessToken
     * @return
     */
    public UserInfo queryUser(AccessToken accessToken) {
        UserInfo user = null;
        StringBuffer userUrl = new StringBuffer();
        userUrl.append(WX_USERINFO_URL)
                .append("?access_token=").append(accessToken.getAccessToken())
                .append("&openid=").append(accessToken.getOpenid());
        String userRet = get(userUrl.toString());
        System.out.println("---- " + userRet);
        ObjectMapper mapper = new ObjectMapper();
        try {
            user = mapper.readValue(userRet, UserInfo.class);
            if (user.getErrcode() == null) {
                user.setErrcode(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    public String get(String url) {
        String body = null;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet get = new HttpGet(url);
            get.addHeader("Accept-Charset","utf-8");
            HttpResponse response = sendRequest(httpClient, get);
            body = parseResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    private HttpResponse sendRequest(CloseableHttpClient httpclient, HttpUriRequest httpost)
            throws ClientProtocolException, IOException {
        HttpResponse response = null;
        response = httpclient.execute(httpost);
        return response;
    }

    private String parseResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();

        Charset charset = ContentType.getOrDefault(entity).getCharset();

        String body = null;
        try {
            body = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    private String paramsToString(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        try{
            for (String key : params.keySet()) {
                sb.append(String.format("&%s=%s", key, URLEncoder.encode(params.get(key), StandardCharsets.UTF_8.toString())));
            }
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return sb.length() > 0 ? "?".concat(sb.substring(1)) : "";
    }

}
