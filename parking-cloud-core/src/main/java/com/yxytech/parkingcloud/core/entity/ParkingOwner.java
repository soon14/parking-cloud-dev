package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author cj
 * @since 2017-10-19
 */
@TableName("yxy_parking_owner")
public class ParkingOwner extends SuperModel<ParkingOwner> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("owner_org_id")
	private Long ownerOrgId;
	@TableField("parking_id")
	private Long parkingId;
	@TableField("approve_status")
	private ApproveEnum approveStatus;
	@TableField("prove_images")
	private String proveImages;
	@TableField(value = "created_by",fill = FieldFill.INSERT)
	private Long createdBy;
	@TableField(value = "created_at",fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "updated_by",fill = FieldFill.INSERT_UPDATE)
	private Long updatedBy;
	@TableField(value = "updated_at",fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;
	@TableField("is_using")
	private Boolean isUsing;

	public ParkingOwner(){

	}

	public ParkingOwner(Long parkingId){
		this.parkingId = parkingId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOwnerOrgId() {
		return ownerOrgId;
	}

	public void setOwnerOrgId(Long ownerOrgId) {
		this.ownerOrgId = ownerOrgId;
	}

	public Long getParkingId() {
		return parkingId;
	}

	public void setParkingId(Long parkingId) {
		this.parkingId = parkingId;
	}

	public String getProveImages() {
		return proveImages;
	}

	public void setProveImages(String proveImages) {
		this.proveImages = proveImages;
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

	public ApproveEnum getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(ApproveEnum approveStatus) {
		this.approveStatus = approveStatus;
	}

	public Boolean getUsing() {
		return isUsing;
	}

	public void setUsing(Boolean using) {
		isUsing = using;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ParkingOwner{" +
			"id=" + id +
			", orgId=" + ownerOrgId +
			", parkingId=" + parkingId +
			", proveImages=" + proveImages +
			", createdBy=" + createdBy +
			", createdAt=" + createdAt +
			", updatedBy=" + updatedBy +
			", updatedAt=" + updatedAt +
			", isUsing=" + isUsing +
			"}";
	}
}
