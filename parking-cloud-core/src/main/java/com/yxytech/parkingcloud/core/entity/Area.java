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
 * @since 2017-10-18
 */
@TableName("yxy_area")
public class Area extends SuperModel<Area> {

    private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String code;
	@TableField("executive_level")
	private String executiveLevel;
	@TableField("parent_id")
	private Long parentId;
	@TableField("sort_number")
	private String sortNumber;
	private Integer level;


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

	public String getExecutiveLevel() {
		return executiveLevel;
	}

	public void setExecutiveLevel(String executiveLevel) {
		this.executiveLevel = executiveLevel;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(String sortNumber) {
		this.sortNumber = sortNumber;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Area{" +
			"id=" + id +
			", name=" + name +
			", code=" + code +
			", executiveLevel=" + executiveLevel +
			", parentId=" + parentId +
			", sortNumber=" + sortNumber +
			", level=" + level +
			"}";
	}
}
