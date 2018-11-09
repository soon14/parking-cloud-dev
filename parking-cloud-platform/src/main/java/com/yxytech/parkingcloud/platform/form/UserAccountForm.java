package com.yxytech.parkingcloud.platform.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class UserAccountForm implements Serializable {

    @Pattern(regexp = "^\\d{11}$", message = "手机号格式不正确")
    private String mobile;

    @Email(message = "电子邮箱格式不正确")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 32, message = "密码长度为6到32位")
    private String password;

    @NotBlank(message = "所属单位不能为空")
    private Integer orgId;

    @Pattern(regexp = "[0~9a-zA-Z]+", message = "工号只能为数字或字母")
    private String employeeNumber;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}
