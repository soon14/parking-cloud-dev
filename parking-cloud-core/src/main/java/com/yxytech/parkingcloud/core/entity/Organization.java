package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import com.yxytech.parkingcloud.core.enums.OrgNatureEnum;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author cj
 * @since 2017-10-18
 */
@TableName("yxy_organzation")
public class Organization extends SuperModel<Organization> {

    private static final long serialVersionUID = 1L;

    @TableId
	private Long id;
	@TableField("org_nature")
	private OrgNatureEnum orgNature;
	@TableField("full_name")
	private String fullName;
	@TableField("short_name")
	private String shortName;
	@TableField("register_address")
	private String registerAddress;
	@TableField("legal_represent_ative")
	private String legalRepresentAtive;
	@TableField("taxpayer_number")
	private String taxpayerNumber;
	@TableField("phone_number")
	private String phoneNumber;
	private String email;
	@TableField("org_number")
	private String orgNumber;
	@TableField("registration_number")
	private String registrationNumber;
	@TableField("approve_status")
	private ApproveEnum approveStatus;
	@TableField("is_property_org")
	private Boolean isPropertyOrg;
	@TableField("is_manage_org")
	private Boolean isManageOrg;
	@TableField("is_facility_org")
	private Boolean isFacilityOrg;
	@TableField("is_regulatory")
	private Boolean isRegulatory;
	@TableField(value = "created_by", fill=FieldFill.INSERT)
	private Long createdBy;
	@TableField(value = "created_at", fill=FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "updated_by", fill=FieldFill.INSERT_UPDATE)
	private Long updatedBy;
	@TableField(value = "updated_at", fill=FieldFill.INSERT_UPDATE)
	private Date updatedAt;
	@TableField("is_valid")
	private Boolean isValid;
	@TableField("area_id")
	private Long areaId;
	@TableField("org_number_certificate")
	private String orgNumberCertificate;

	private transient String areaName;

	public Boolean getRegulatory() {
		return isRegulatory;
	}

	public void setRegulatory(Boolean regulatory) {
		isRegulatory = regulatory;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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
		return orgNumberCertificate;
	}

	public void setOrgNumberCertificate(String orgNumberCertificate) {
		this.orgNumberCertificate = orgNumberCertificate;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Organization{" +
			"id=" + id +
			", orgNature=" + orgNature +
			", fullName=" + fullName +
			", shortName=" + shortName +
			", registerAddress=" + registerAddress +
			", legalRepresentAtive=" + legalRepresentAtive +
			", taxpayerNumber=" + taxpayerNumber +
			", phoneNumber=" + phoneNumber +
			", email=" + email +
			", orgNumber=" + orgNumber +
			", registrationNumber=" + registrationNumber +
			", isPropertyOrg=" + isPropertyOrg +
			", isManageOrg=" + isManageOrg +
			", isFacilityOrg=" + isFacilityOrg +
			", createdBy=" + createdBy +
			", createdAt=" + createdAt +
			", updatedBy=" + updatedBy +
			", updatedAt=" + updatedAt +
			", isValid=" + isValid +
			", areaId=" + areaId +
			"}";
	}
}
