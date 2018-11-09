package com.yxytech.parkingcloud.security.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTokenRequest {

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 所属单位
     */
    private String orgName;

    /**
     * 构造方法
     * @param username
     * @param password
     */
    @JsonCreator
    public UserTokenRequest(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("orgName") String orgName) {
        this.username = username;
        this.password = password;
        this.orgName = orgName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getOrgName() {
        return orgName;
    }
}
