package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author cj
 * @since 2017-11-11
 */
@TableName("yxy_permission_resource")
public class PermissionResource extends SuperModel<PermissionResource> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("permission_id")
	private Long permissionId;
	@TableField("resource_id")
	private Long resourceId;

    public PermissionResource() {

	}
	public PermissionResource(Long permissionId, Long resourceId) {
		this.permissionId = permissionId;
		this.resourceId = resourceId;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PermissionResource{" +
			"id=" + id +
			", permissionId=" + permissionId +
			", resourceId=" + resourceId +
			"}";
	}
}
