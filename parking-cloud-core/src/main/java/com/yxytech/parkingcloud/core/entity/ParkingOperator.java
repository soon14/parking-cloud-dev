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
@TableName("yxy_parking_operator")
public class ParkingOperator extends SuperModel<ParkingOperator> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("operator_org_id")
	private Long operatorOrgId;
	@TableField("parking_id")
	private Long parkingId;
	@TableField("manage_prove")
	private String manageProve;
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

	@TableField("approve_status")
	private ApproveEnum approveStatus;

	public ParkingOperator(){

	}

	public ParkingOperator(Long parkingId){
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

	public Long getOperatorOrgId() {
		return operatorOrgId;
	}

	public void setOperatorOrgId(Long operatorOrgId) {
		this.operatorOrgId = operatorOrgId;
	}

	public Long getParkingId() {
		return parkingId;
	}

	public void setParkingId(Long parkingId) {
		this.parkingId = parkingId;
	}

	public String getManageProve() {
		return manageProve;
	}

	public void setManageProve(String manageProve) {
		this.manageProve = manageProve;
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


	public Boolean getUsing() {
		return isUsing;
	}

	public void setUsing(Boolean using) {
		isUsing = using;
	}

	public ApproveEnum getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(ApproveEnum approveStatus) {
		this.approveStatus = approveStatus;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ParkingOperator{" +
			"id=" + id +
			", orgId=" + operatorOrgId +
			", parkingId=" + parkingId +
			", manageProve=" + manageProve +
			", createdBy=" + createdBy +
			", createdAt=" + createdAt +
			", updatedBy=" + updatedBy +
			", updatedAt=" + updatedAt +
			", isUsing=" + isUsing +
			"}";
	}
}
