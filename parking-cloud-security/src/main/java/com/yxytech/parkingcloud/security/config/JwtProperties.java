package com.yxytech.parkingcloud.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "yxytech.security.jwt")
public class JwtProperties {

    /**
     * token默认超时时间
     */
    private final static int TOKEN_EXPIRATION_TIME = 30;

    /**
     * 刷新token默认超时时间
     */
    private final static int REFRESH_TOKEN_EXPTIME = 60;

    /**
     * ISSUER
     */
    private final static String _ISSUER = "http://www.yxytech.com";

    /**
     * 默认签名密钥
     */
    private final static String TOKEN_SIGN_KEY = "xm8EV6Hy5RMFK4EEACIDAwQus";

    /**
     * 默认JWT_TOKEN头参数
     */
    private final static String JWT_TOKEN_HEADER_PARAM = "X-Authorization";


    /**
     * token超时时间
     */
    private Integer tokenExpirationTime = TOKEN_EXPIRATION_TIME;

    /**
     * Token issuer.
     */
    private String tokenIssuer = _ISSUER;

    /**
     * token签名字符串
     */
    private String tokenSigningKey = TOKEN_SIGN_KEY;

    private String tokenHeaderParam = JWT_TOKEN_HEADER_PARAM;


    /**
     * token刷新超时时间
     */
    private Integer refreshTokenExpTime = REFRESH_TOKEN_EXPTIME;

    public Integer getRefreshTokenExpTime() {
        return refreshTokenExpTime;
    }

    public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
        this.refreshTokenExpTime = refreshTokenExpTime;
    }

    public Integer getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public void setTokenExpirationTime(Integer tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }
    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    public String getTokenSigningKey() {
        return tokenSigningKey;
    }

    public void setTokenSigningKey(String tokenSigningKey) {
        this.tokenSigningKey = tokenSigningKey;
    }

    public String getTokenHeaderParam() {
        return tokenHeaderParam;
    }

    public void setTokenHeaderParam(String tokenHeaderParam) {
        this.tokenHeaderParam = tokenHeaderParam;
    }
}
