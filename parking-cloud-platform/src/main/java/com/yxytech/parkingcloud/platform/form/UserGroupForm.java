package com.yxytech.parkingcloud.platform.form;

import com.yxytech.parkingcloud.core.enums.UserGroupStatusEnum;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserGroupForm implements Serializable {

    @NotNull
    private Long orgId;

    @NotBlank
    private String name;

    private String remark;

    @NotNull
    private UserGroupStatusEnum status;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UserGroupStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UserGroupStatusEnum status) {
        this.status = status;
    }
}
