package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.yxytech.parkingcloud.core.enums.ColorsEnum;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwd
 * @since 2017-10-20
 */
@TableName("yxy_whitelist")
public class Whitelist extends SuperModel<Whitelist> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("plate_number")
	private String plateNumber;
	@TableField("plate_color")
	private ColorsEnum plateColor;
	@TableField("org_id")
	private Long organizationId;
	@TableField("parking_id")
	private Long parkingId;
	@TableField(value = "created_at", fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "created_by", fill = FieldFill.INSERT)
	private Long createdBy;
	@TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;
	@TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
	private Long updatedBy;
	@TableField("started_at")
	private Date startedAt;
	@TableField("end_at")
	private Date endAt;
	@TableField("join_reason")
	private String joinReason;
	@TableField("out_reason")
	private String outReason;
	@TableField("is_valid")
	private Boolean valid;
	private transient String parkingName;
	private transient String organizationName;

	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public ColorsEnum getPlateColor() {
		return plateColor;
	}

	public void setPlateColor(ColorsEnum color) {
		this.plateColor = color;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getParkingId() {
		return parkingId;
	}

	public void setParkingId(Long parkingId) {
		this.parkingId = parkingId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public String getJoinReason() {
		return joinReason;
	}

	public void setJoinReason(String joinReason) {
		this.joinReason = joinReason;
	}

	public String getOutReason() {
		return outReason;
	}

	public void setOutReason(String outReason) {
		this.outReason = outReason;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean isValid) {
		this.valid = isValid;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Whitelist{" +
			"id=" + id +
			", plateNumber=" + plateNumber +
			", color=" + plateColor +
			", organizationId=" + organizationId +
			", parkingId=" + parkingId +
			", createdAt=" + createdAt +
			", createdBy=" + createdBy +
			", updatedAt=" + updatedAt +
			", updatedBy=" + updatedBy +
			", startedAt=" + startedAt +
			", endAt=" + endAt +
			", joinReason=" + joinReason +
			", outReason=" + outReason +
			", isValid=" + valid +
			"}";
	}
}
