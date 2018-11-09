package com.yxytech.parkingcloud.platform.form;

import javax.validation.constraints.NotNull;

public class UsersWithRolesGroupsQuery {



    @NotNull(message = "单位id不能为空")
    private Long orgId;

    private Long roleId;

    private Long groupId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
