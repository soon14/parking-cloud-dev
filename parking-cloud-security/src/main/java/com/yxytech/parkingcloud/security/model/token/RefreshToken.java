package com.yxytech.parkingcloud.security.model.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.Optional;

public class RefreshToken implements JwtToken {

    /**
     * claims
     */
    private Jws<Claims> claims;

    /**
     * 构造方法
     * @param claims
     */
    private RefreshToken(Jws<Claims> claims) {
        this.claims = claims;
    }

    /**
     * 创建和验证 Refresh token
     * @param token
     * @param signingKey
     *
     * @return
     */
    public static Optional<RefreshToken> create(RawAccessJwtToken token, String signingKey) {
        Jws<Claims> claims = token.parseClaims(signingKey);
        return Optional.of(new RefreshToken(claims));
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public Long getExpiresAt() {
        return claims.getBody().getExpiration().getTime() / 1000;
    }

    public Jws<Claims> getClaims() {
        return this.claims;
    }

    public String getJti() {
        return this.claims.getBody().getId();
    }

    public String getSubject() {
        return this.claims.getBody().getSubject();
    }
}
