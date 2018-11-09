package com.yxytech.parkingcloud.security.model.token;

import com.yxytech.parkingcloud.security.exception.ExpiredTokenException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

public class RawAccessJwtToken implements JwtToken {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(RawAccessJwtToken.class);

    /**
     * token
     */
    private String token;

    /**
     * 构造方法
     * @param token
     */
    public RawAccessJwtToken(String token) {
        this.token = token;
    }

    /**
     * 分析并验证JWT token和签名
     *
     * @throws BadCredentialsException
     * @throws ExpiredTokenException
     */
    public Jws<Claims> parseClaims(String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.error("Invalid JWT Token", ex);
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            logger.error("JWT Token is expired", expiredEx);
            throw new ExpiredTokenException(this, "JWT Token expired", expiredEx);
        }
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public Long getExpiresAt() {
        return null;
    }
}
