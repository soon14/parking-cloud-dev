package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author cj
 * @since 2017-11-10
 */
@TableName("yxy_org_parking")
public class OrgParking extends SuperModel<OrgParking> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("org_id")
	private Long orgId;
	@TableField("parking_id")
	private Long parkingId;
	@TableField("is_regulatory")
	private Boolean isRegulatory;
	@TableField("is_owner")
	private Boolean isOwner;
	@TableField("is_operator")
	private Boolean isOperator;
	@TableField(value = "created_at",fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "updated_at",fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;
	@TableField(value = "created_by",fill = FieldFill.INSERT)
	private Long createdBy;
	@TableField(value = "updated_by",fill = FieldFill.INSERT_UPDATE)
	private Long updatedBy;
	private transient String orgName;
	private transient List<Parking> parkings;

	public OrgParking(){

	}
	public  OrgParking(Long orgId,Long parkingId,Boolean isOperator,Boolean isOwner,Boolean isRegulatory){
         this.orgId = orgId;
         this.parkingId = parkingId;
         this.isOperator = isOperator;
         this.isOwner = isOwner;
         this.isRegulatory = isRegulatory;
	}


	public List<Parking> getParkings() {
		return parkings;
	}

	public void setParkings(List<Parking> parkings) {
		this.parkings = parkings;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getParkingId() {
		return parkingId;
	}

	public void setParkingId(Long parkingId) {
		this.parkingId = parkingId;
	}

	public Boolean getRegulatory() {
		return isRegulatory;
	}

	public void setRegulatory(Boolean regulatory) {
		isRegulatory = regulatory;
	}

	public Boolean getOwner() {
		return isOwner;
	}

	public void setOwner(Boolean owner) {
		isOwner = owner;
	}

	public Boolean getOperator() {
		return isOperator;
	}

	public void setOperator(Boolean operator) {
		isOperator = operator;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "OrgParking{" +
			"id=" + id +
			", orgId=" + orgId +
			", parkingId=" + parkingId +
			", isRegulatory=" + isRegulatory +
			", isOwner=" + isOwner +
			", isOperator=" + isOperator +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			", createdBy=" + createdBy +
			", updatedBy=" + updatedBy +
			"}";
	}
}
