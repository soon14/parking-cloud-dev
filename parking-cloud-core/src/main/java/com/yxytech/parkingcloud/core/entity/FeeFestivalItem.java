package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author cj
 * @since 2017-12-12
 */
@TableName("yxy_fee_festival_item")
public class FeeFestivalItem extends SuperModel<FeeFestivalItem> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("festival_id")
	private Long festivalId;

	@TableField("org_id")
	private Long organizationId;

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@TableField("date_time")
	private Date dateTime;

	public FeeFestivalItem(){

	}

	public FeeFestivalItem(Long festivalId,Date dateTime){
		this.festivalId = festivalId;
		this.dateTime = dateTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFestivalId() {
		return festivalId;
	}

	public void setFestivalId(Long festivalId) {
		this.festivalId = festivalId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "FeeFestivalItem{" +
			"id=" + id +
			", festivalId=" + festivalId +
			", dateTime=" + dateTime +
			"}";
	}
}
