package com.yxytech.parkingcloud.security.auth.jwt;

import com.yxytech.parkingcloud.commons.entity.UserContext;
import com.yxytech.parkingcloud.security.model.token.RawAccessJwtToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * JWTtoken
     */
    private RawAccessJwtToken rawAccessToken;

    /**
     * 用户上下文
     */
    private UserContext userContext;

    /**
     * 构造方法
     * @param unsafeToken
     */
    public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(null);
        this.rawAccessToken = unsafeToken;
        this.setAuthenticated(false);
    }

    /**
     * 构造方法
     * @param userContext
     * @param authorities
     */
    public JwtAuthenticationToken(UserContext userContext,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.userContext = userContext;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.userContext;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.rawAccessToken = null;
    }
}
