package com.yxytech.parkingcloud.security.auth.jwt;

import com.yxytech.parkingcloud.commons.entity.UserContext;
import com.yxytech.parkingcloud.security.config.JwtProperties;
import com.yxytech.parkingcloud.security.model.token.RawAccessJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    /**
     * JWT 属性配置对象
     */
    private final JwtProperties jwtProperties;

    /**
     * 构造方法并初始化JWT属性
     * @param jwtProperties
     */
    @Autowired
    public JwtAuthenticationProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(this.jwtProperties.getTokenSigningKey());
        UserContext context = new UserContext(jwsClaims.getBody().getSubject(), new ArrayList<>());

        return new JwtAuthenticationToken(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
