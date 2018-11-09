package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxytech.parkingcloud.core.enums.ResourceStatusEnum;
import com.yxytech.parkingcloud.core.enums.ResourceTypeEnum;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 	资源信息表
 * </p>
 *
 * @author cj
 * @since 2017-11-11
 */
@TableName("yxy_resource")
public class Resource extends SuperModel<Resource> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@JsonProperty("title")
	private String name;
	private String code;
	@TableField("res_type")
	private ResourceTypeEnum resType;
	@TableField("module_id")
	private Long moduleId;
	@TableField("parent_id")
	private Long parentId;
	private Integer level;
	private String depth;
	@JsonProperty("isLeaf")
	private Boolean isLeaf;
	@JsonProperty("isHidden")
	private Boolean isHidden;
	@JsonProperty("path")
	private String url;
	private String icon;
	private ResourceStatusEnum status;
	@TableField(value = "created_at", fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ResourceTypeEnum getResType() {
		return resType;
	}

	public void setResType(ResourceTypeEnum resType) {
		this.resType = resType;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public Boolean getLeaf() {
		return isLeaf;
	}

	public void setLeaf(Boolean leaf) {
		isLeaf = leaf;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public ResourceStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ResourceStatusEnum status) {
		this.status = status;
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

	public Boolean getHidden() {
		return isHidden;
	}

	public void setHidden(Boolean hidden) {
		isHidden = hidden;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Resource{" +
			"id=" + id +
			", name=" + name +
			", code=" + code +
			", resType=" + resType +
			", moduleId=" + moduleId +
			", parentId=" + parentId +
			", level=" + level +
			", depth=" + depth +
			", isLeaf=" + isLeaf +
			", isHidden=" + isHidden +
			", url=" + url +
			", icon=" + icon +
			", status=" + status +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			"}";
	}
}
