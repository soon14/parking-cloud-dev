package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author cj
 * @since 2017-10-29
 */
@TableName("yxy_fee_festival")
public class FeeFestival extends SuperModel<FeeFestival> {

    private static final long serialVersionUID = 1L;

	private Long id;
	// 节日名称
	private String name;
	// 节日起始时间
	@TableField("start_time")
	private Date startTime;
	// 假日结束时间
	@TableField("end_time")
	private Date endTime;

	@TableField("org_id")
	private Long organizationId;

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@TableField(value = "created_at",fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "updated_at",fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;
	@TableField(value = "created_by",fill = FieldFill.INSERT)
	private Long createdBy;
	@TableField(value = "updated_by",fill = FieldFill.INSERT_UPDATE)
	private Long updatedBy;
	// 备注
	private String note;

	public FeeFestival(){

	}

	public FeeFestival(String name,Date startTime,Date endTime,String note){
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.note = note;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "FeeFestival{" +
			"id=" + id +
			", name=" + name +
			", startTime=" + startTime +
			", endTime=" + endTime +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			", createdBy=" + createdBy +
			", updatedBy=" + updatedBy +
			", note=" + note +
			"}";
	}
}
