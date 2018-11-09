package com.yxytech.parkingcloud.security.model.token;

public interface JwtToken {

    /**
     * 返回token值
     * @return
     */
    String getToken();

    /**
     * 返回失效时间
     */
    Long getExpiresAt();
}
