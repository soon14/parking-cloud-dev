package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author cj
 * @since 2017-11-11
 */
@TableName("yxy_user_role")
public class UserRole extends SuperModel<UserRole> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("user_id")
	private Long userId;
	@TableField("role_id")
	private Long roleId;

	@TableField(value = "created_at", fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;
	@TableField(value = "created_by", fill = FieldFill.INSERT)
	private Long createdBy;
	@TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
	private Long updatedBy;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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

	public UserRole(Long userId, Long roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	public UserRole() {
	}

	@Override
	public String toString() {
		return "UserRole{" +
			"id=" + id +
			", userId=" + userId +
			", roleId=" + roleId +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			", createdBy=" + createdBy +
			", updatedBy=" + updatedBy +
			"}";
	}
}
