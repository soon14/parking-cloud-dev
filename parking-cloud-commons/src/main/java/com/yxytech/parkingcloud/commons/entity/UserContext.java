package com.yxytech.parkingcloud.commons.entity;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

public class UserContext implements Serializable {

    /**
     * 用户账号Id
     */
    private String userId;

    /**
     * 权限列表对象
     */
    private List<GrantedAuthority> authorities;

    /**
     * 用户上下文
     * @param userId
     * @param authorities
     */
    public UserContext(String userId, List<GrantedAuthority> authorities) {
        this.userId = userId;
        this.authorities = authorities;
    }

    public String getUserId() {
        return userId;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
