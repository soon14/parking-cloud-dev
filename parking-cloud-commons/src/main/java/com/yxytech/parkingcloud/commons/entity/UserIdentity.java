package com.yxytech.parkingcloud.commons.entity;

public class UserIdentity {

    private String orgName;

    private String username;

    public UserIdentity(String orgName, String username) {
        this.orgName = orgName;
        this.username = username;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getUsername() {
        return username;
    }
}
