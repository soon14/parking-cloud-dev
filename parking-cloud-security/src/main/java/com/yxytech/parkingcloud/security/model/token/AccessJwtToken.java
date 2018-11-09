package com.yxytech.parkingcloud.security.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;

public class AccessJwtToken implements JwtToken {

    /**
     * token
     */
    private final String rawToken;

    /**
     * 标明此属性在生成JSON不生成
     */
    @JsonIgnore
    private Claims claims;

    protected AccessJwtToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }

    public String getToken() {
        return this.rawToken;
    }

    @Override
    public Long getExpiresAt() {
        return claims.getExpiration().getTime() / 1000;
    }



    public Claims getClaims() {
        return claims;
    }
}
