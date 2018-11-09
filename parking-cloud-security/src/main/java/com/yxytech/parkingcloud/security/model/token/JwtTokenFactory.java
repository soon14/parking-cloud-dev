package com.yxytech.parkingcloud.security.model.token;

import com.yxytech.parkingcloud.commons.entity.UserContext;
import com.yxytech.parkingcloud.security.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtTokenFactory {

    /**
     * Jwt属性生成对象
     */
    private final JwtProperties jwtProperties;

    /**
     * 构造方法
     * @param jwtProperties
     */
    @Autowired
    public JwtTokenFactory(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 创建token
     * @param ctxt
     * @return
     */
    public AccessJwtToken createAccessJwtToken(UserContext ctxt) {

        if (!StringUtils.hasText(ctxt.getUserId())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }


        Claims claims = Jwts.claims().setSubject(ctxt.getUserId());
        LocalDateTime currentTime = LocalDateTime.now();

        String token = Jwts.builder().setClaims(claims)
                .setIssuer(this.jwtProperties.getTokenIssuer())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(this.jwtProperties.getTokenExpirationTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, this.jwtProperties.getTokenSigningKey())
                .compact();

        return new AccessJwtToken(token, claims);
    }

    /**
     * 创建刷洗token
     * @param userContext
     * @return
     */
    public JwtToken createRefreshToken(UserContext userContext) {

        if (StringUtils.isEmpty(userContext.getUserId())) {
            throw new IllegalArgumentException("Cannot create JWT Token without accountId");
        }

        LocalDateTime currentTime = LocalDateTime.now();
        Claims claims = Jwts.claims().setSubject(userContext.getUserId());

        String token = Jwts.builder().setClaims(claims)
                .setIssuer(this.jwtProperties.getTokenIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(this.jwtProperties.getRefreshTokenExpTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, this.jwtProperties.getTokenSigningKey())
                .compact();

        return new AccessJwtToken(token, claims);
    }

    public Map<String, Object> createTokenData(UserContext userContext) {
        JwtToken accessToken = createAccessJwtToken(userContext);
        JwtToken refreshToken = createRefreshToken(userContext);

        // 返回授权对象
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", 		 accessToken.getToken());
        tokenMap.put("tokenExpiresAt", accessToken.getExpiresAt());
        tokenMap.put("refreshToken", refreshToken.getToken());
        tokenMap.put("refreshTokenExpiresAt", refreshToken.getExpiresAt());

        return tokenMap;
    }
}
