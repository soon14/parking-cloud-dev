package com.yxytech.parkingcloud.platform.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

public class UserForm implements Serializable {

    @Pattern(regexp = "^\\d{11}$|^$", message = "手机号格式不正确")
    private String mobile;

    @Email(message = "电子邮箱格式不正确")
    private String email;

    @Length(min = 6, max = 32, message = "密码长度为6到32位")
    private String password;

    @NotNull(message = "所属单位不能为空")
    private Long orgId;

    @Pattern(regexp = "^[0-9A-Za-z]*$", message = "工号只能为数字或字母")
    private String employeeNumber;

    @NotBlank(message = "姓名不能为空")
    @Length(
            min = 2,
            max = 16,
            message = "姓名长度为2~16"
    )
    private String name;

    @NotBlank(message = "性别不能为空")
    private String gender;

    @NotBlank(message = "身份证明类型不能为空")
    private String idType;

    @NotBlank(message = "身份证明号码不能为空")
    private String idNumber;

    @NotNull(message = "出生日期不能为空")
    private Date birth;

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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
}
