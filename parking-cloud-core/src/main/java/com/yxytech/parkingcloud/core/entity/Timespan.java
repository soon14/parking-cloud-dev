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
 * @author liwd
 * @since 2017-10-20
 */
@TableName("yxy_timespan")
public class Timespan extends SuperModel<Timespan> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("started_at")
	private Date startedAt;
	@TableField("end_at")
	private Date endAt;
	private Integer cycle;
	@TableField("freelist_id")
	private Long freelistId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public Long getFreelistId() {
		return freelistId;
	}

	public void setFreelistId(Long freelistId) {
		this.freelistId = freelistId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Timespan{" +
			"id=" + id +
			", startedAt=" + startedAt +
			", endAt=" + endAt +
			", cycle=" + cycle +
			", freelistId=" + freelistId +
			"}";
	}
}
