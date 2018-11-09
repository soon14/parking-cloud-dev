package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxytech.parkingcloud.core.enums.CouponEnum;
import com.yxytech.parkingcloud.core.enums.CouponStatus;
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
@TableName("yxy_promo_code")
public class PromoCode extends SuperModel<PromoCode> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("coupon_id")
	private Long couponId;
	@TableField("promo_code")
	private String promoCode;
	private CouponStatus status;
	@TableField("receive_start")
	private Date receiveStart;
	@TableField("receive_end")
	private Date receiveEnd;
	@TableField("started_at")
	private Date startedAt;
	@TableField("end_at")
	private Date endAt;
	private String describe;
	private Double couponInfo;
	@TableField("coupon_type")
	private CouponEnum couponType;
	private transient Boolean canSuperposed;
	private transient Integer maxOfSuperposed;
	private transient Double minUseMoney;
	private transient Double maxSuperposedMoney;
	private transient Boolean canUseWithWhitelist;

	public Boolean getCanUseWithWhitelist() {
		return canUseWithWhitelist;
	}

	public void setCanUseWithWhitelist(Boolean canUseWithWhitelist) {
		this.canUseWithWhitelist = canUseWithWhitelist;
	}

	public Boolean getCanSuperposed() {
		return canSuperposed;
	}

	public void setCanSuperposed(Boolean canSuperposed) {
		this.canSuperposed = canSuperposed;
	}

	public Integer getMaxOfSuperposed() {
		return maxOfSuperposed;
	}

	public void setMaxOfSuperposed(Integer maxOfSuperposed) {
		this.maxOfSuperposed = maxOfSuperposed;
	}

	public Double getMinUseMoney() {
		return minUseMoney;
	}

	public void setMinUseMoney(Double minUseMoney) {
		this.minUseMoney = minUseMoney;
	}

	public Double getMaxSuperposedMoney() {
		return maxSuperposedMoney;
	}

	public void setMaxSuperposedMoney(Double maxSuperposedMoney) {
		this.maxSuperposedMoney = maxSuperposedMoney;
	}

	@JsonProperty("couponType")
	public Integer getCouponTypeCode() {
		return (Integer) this.getCouponType().getValue();
	}

	@JsonProperty("couponTypeName")
	public CouponEnum getCouponType() {
		return couponType;
	}

	public void setCouponType(CouponEnum couponType) {
		this.couponType = couponType;
	}

	@JsonProperty("status")
	public Integer getStatusCode() {
		return (Integer) this.getStatus().getValue();
	}

	public Double getCouponInfo() {
		return couponInfo;
	}

	public void setCouponInfo(Double couponInfo) {
		this.couponInfo = couponInfo;
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

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	@JsonProperty("statusName")
	public CouponStatus getStatus() {
		return status;
	}

	public void setStatus(CouponStatus status) {
		this.status = status;
	}

	public Date getReceiveStart() {
		return receiveStart;
	}

	public void setReceiveStart(Date receiveStart) {
		this.receiveStart = receiveStart;
	}

	public Date getReceiveEnd() {
		return receiveEnd;
	}

	public void setReceiveEnd(Date receiveEnd) {
		this.receiveEnd = receiveEnd;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PromoCode{" +
			"id=" + id +
			", couponId=" + couponId +
			", promoCode=" + promoCode +
			", status=" + status +
			", receiveStart=" + receiveStart +
			", receiveEnd=" + receiveEnd +
			"}";
	}
}
