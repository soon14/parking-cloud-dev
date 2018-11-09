package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.enums.CouponStatus;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwd
 * @since 2017-11-22
 */
@TableName("yxy_coupon_history")
public class CouponHistory extends SuperModel<CouponHistory> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("promo_code_id")
	private Long promoCodeId;
	@TableField("received_by")
	private Long receivedBy;
	@TableField("received_car_id")
	private Long receivedCarId;
	private CouponStatus status;


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

	public Long getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(Long receivedBy) {
		this.receivedBy = receivedBy;
	}

	public Long getReceivedCarId() {
		return receivedCarId;
	}

	public void setReceivedCarId(Long receivedCarId) {
		this.receivedCarId = receivedCarId;
	}

	public CouponStatus getStatus() {
		return status;
	}

	public void setStatus(CouponStatus status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CouponHistory{" +
			"id=" + id +
			", promoCodeId=" + promoCodeId +
			", receivedBy=" + receivedBy +
			", receivedCarId=" + receivedCarId +
			", status=" + status +
			"}";
	}
}
