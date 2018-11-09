package com.yxytech.parkingcloud.platform.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class RepasswordForm implements Serializable {

    @NotBlank(message = "原密码不能为空")
    private String origin;

    @NotBlank(message = "新密码不能为空")
    @Length(min = 6, max = 32, message = "密码长度为6到32位")
    private String newPassword;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
