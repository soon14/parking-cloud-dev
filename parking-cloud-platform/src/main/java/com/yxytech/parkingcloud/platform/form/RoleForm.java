package com.yxytech.parkingcloud.platform.form;

import com.yxytech.parkingcloud.core.enums.RoleStatusEnum;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RoleForm implements Serializable {

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    private String remark;

    @NotNull
    private RoleStatusEnum status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public RoleStatusEnum getStatus() {
        return status;
    }

    public void setStatus(RoleStatusEnum status) {
        this.status = status;
    }
}
