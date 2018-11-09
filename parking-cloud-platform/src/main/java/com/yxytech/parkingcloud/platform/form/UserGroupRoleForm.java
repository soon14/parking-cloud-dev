package com.yxytech.parkingcloud.platform.form;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserGroupRoleForm implements Serializable {

    @NotNull
    private Long groupId;

    @NotNull
    private Long roleId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
