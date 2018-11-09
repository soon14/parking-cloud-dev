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
 * @since 2017-11-22
 */
@TableName("yxy_coupon_log")
public class CouponLog extends SuperModel<CouponLog> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("promo_code_id")
	private Long promoCodeId;
	@TableField("created_by")
	private Long createdBy;
	@TableField("created_at")
	private Date createdAt;
	private Integer status;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPromoCodeId() {
		return promoCodeId;
	}

	public void setPromoCodeId(Long promoCodeId) {
		this.promoCodeId = promoCodeId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CouponLog{" +
			"id=" + id +
			", promoCodeId=" + promoCodeId +
			", createdBy=" + createdBy +
			", createdAt=" + createdAt +
			", status=" + status +
			"}";
	}
}
