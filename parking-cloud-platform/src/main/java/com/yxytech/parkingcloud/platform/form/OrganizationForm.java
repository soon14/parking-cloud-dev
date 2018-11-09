package com.yxytech.parkingcloud.platform.form;

import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import com.yxytech.parkingcloud.core.enums.OrgNatureEnum;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class OrganizationForm implements Serializable{

    private Long id;

    @NotNull(message = "单位性质不能为空")
    private OrgNatureEnum orgNature;

    @NotBlank(message = "单位全称不能为空")
    @Length(max =50, message = "单位全称不能超过50个字符")
    private String fullName;

    @NotBlank(message = "单位简称不能为空")
    @Length(max = 20,message = "单位简称不能超过20个字符")
    private String shortName;

    @NotBlank(message = "单位注册地址不能为空" )
    private String registerAddress;

    @NotBlank(message = "单位法人不能为空")
    private String legalRepresentAtive;

    @NotBlank(message = "纳税人识别号不能为空")
    private String taxpayerNumber;

    @Pattern(regexp = "^\\d{11}$|^$", message = "手机号格式不正确")
    private String phoneNumber;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String orgNumber;

    private String registrationNumber;

    private ApproveEnum approveStatus;

    private Boolean isPropertyOrg;

    private Boolean isManageOrg;

    private Boolean isFacilityOrg;

    @NotNull(message = "是否为监管单位不能为空")
    private Boolean isRegulatory;

    private Boolean isValid;

    @NotNull(message = "所属区域不能为空")
    private Long areaId;

    private String OrgNumberCertificate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getRegulatory() {
        return isRegulatory;
    }

    public void setRegulatory(Boolean regulatory) {
        isRegulatory = regulatory;
    }

    public OrgNatureEnum getOrgNature() {
        return orgNature;
    }

    public void setOrgNature(OrgNatureEnum orgNature) {
        this.orgNature = orgNature;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getLegalRepresentAtive() {
        return legalRepresentAtive;
    }

    public void setLegalRepresentAtive(String legalRepresentAtive) {
        this.legalRepresentAtive = legalRepresentAtive;
    }

    public String getTaxpayerNumber() {
        return taxpayerNumber;
    }

    public void setTaxpayerNumber(String taxpayerNumber) {
        this.taxpayerNumber = taxpayerNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public ApproveEnum getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(ApproveEnum approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Boolean getPropertyOrg() {
        return isPropertyOrg;
    }

    public void setPropertyOrg(Boolean propertyOrg) {
        isPropertyOrg = propertyOrg;
    }

    public Boolean getManageOrg() {
        return isManageOrg;
    }

    public void setManageOrg(Boolean manageOrg) {
        isManageOrg = manageOrg;
    }

    public Boolean getFacilityOrg() {
        return isFacilityOrg;
    }

    public void setFacilityOrg(Boolean facilityOrg) {
        isFacilityOrg = facilityOrg;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getOrgNumberCertificate() {
        return OrgNumberCertificate;
    }

    public void setOrgNumberCertificate(String orgNumberCertificate) {
        OrgNumberCertificate = orgNumberCertificate;
    }
}
