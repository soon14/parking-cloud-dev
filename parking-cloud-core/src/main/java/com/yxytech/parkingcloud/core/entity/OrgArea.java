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
@TableName("yxy_org_area")
public class OrgArea extends SuperModel<OrgArea> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("org_id")
	private Long orgId;
	@TableField("area_id")
	private Long areaId;
	@TableField(value = "created_at",fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "updated_at",fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;
	@TableField(value = "created_by",fill = FieldFill.INSERT)
	private Long createdBy;
	@TableField(value = "updated_by",fill = FieldFill.INSERT_UPDATE)
	private Long updatedBy;
	private transient String orgName;
	private transient List<Area> areas;

	public OrgArea(){

	}

	public OrgArea(Long orgId,Long areaId){
		this.orgId = orgId;
		this.areaId = areaId;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
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

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
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
		return "OrgArea{" +
			"id=" + id +
			", orgId=" + orgId +
			", areaId=" + areaId +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			", createdBy=" + createdBy +
			", updatedBy=" + updatedBy +
			"}";
	}
}
