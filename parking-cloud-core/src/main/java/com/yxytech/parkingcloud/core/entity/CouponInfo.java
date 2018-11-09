package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.yxytech.parkingcloud.core.enums.CouponEnum;
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
@TableName("yxy_coupon_info")
public class CouponInfo extends SuperModel<CouponInfo> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("coupon_unique")
	private String couponUnique;
	@TableField("org_id")
	private Long organizationId;
	@TableField("parking_id")
	private Long parkingId;
	@TableField("started_at")
	private Date startedAt;
	@TableField("end_at")
	private Date endAt;
	@TableField("time_interval_start")
	private Date timeIntervalStart;
	@TableField("time_interval_end")
	private Date timeIntervalEnd;
	@TableField("can_superposed")
	private Boolean canSuperposed;
	@TableField("max_of_superposed")
	private Integer maxOfSuperposed;
	@TableField("can_use_with_whitelist")
	private Boolean canUseWithWhitelist;
	@TableField("min_use_money")
	private Double minUseMoney;
	@TableField("max_superposed_money")
	private Double maxSuperposedMoney;
	@TableField("coupon_type")
	private CouponEnum couponType;
	@TableField(value = "created_at", fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "created_by", fill = FieldFill.INSERT)
	private Long createdBy;
	@TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;
	@TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
	private Long updatedBy;
	@TableField("is_valid")
	private Boolean isValid;
	@TableField("coupon_info")
	private Double couponInfo;
	@TableField("describe")
	private String describe;
	private Integer times;
	private transient PromoCode promoCode;

	public PromoCode getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(PromoCode promoCode) {
		this.promoCode = promoCode;
	}

	public void setValid(Boolean valid) {
		isValid = valid;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Double getCouponInfo() {
		return couponInfo;
	}

	public void setCouponInfo(Double couponInfo) {
		this.couponInfo = couponInfo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCouponUnique() {
		return couponUnique;
	}

	public void setCouponUnique(String couponUnique) {
		this.couponUnique = couponUnique;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getParkingId() {
		return parkingId;
	}

	public void setParkingId(Long parkingId) {
		this.parkingId = parkingId;
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

	public Date getTimeIntervalStart() {
		return timeIntervalStart;
	}

	public void setTimeIntervalStart(Date timeIntervalStart) {
		this.timeIntervalStart = timeIntervalStart;
	}

	public Date getTimeIntervalEnd() {
		return timeIntervalEnd;
	}

	public void setTimeIntervalEnd(Date timeIntervalEnd) {
		this.timeIntervalEnd = timeIntervalEnd;
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

	public Boolean getCanUseWithWhitelist() {
		return canUseWithWhitelist;
	}

	public void setCanUseWithWhitelist(Boolean canUseWithWhitelist) {
		this.canUseWithWhitelist = canUseWithWhitelist;
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

	public CouponEnum getCouponType() {
		return couponType;
	}

	public void setCouponType(CouponEnum couponType) {
		this.couponType = couponType;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Boolean getValid() {
		return isValid;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CouponInfo{" +
			"id=" + id +
			", couponUnique=" + couponUnique +
			", organizationId=" + organizationId +
			", parkingId=" + parkingId +
			", startedAt=" + startedAt +
			", endAt=" + endAt +
			", timeIntervalStart=" + timeIntervalStart +
			", timeIntervalEnd=" + timeIntervalEnd +
			", canSuperposed=" + canSuperposed +
			", maxOfSuperposed=" + maxOfSuperposed +
			", canUseWithWhitelist=" + canUseWithWhitelist +
			", minUseMoney=" + minUseMoney +
			", maxSuperposedMoney=" + maxSuperposedMoney +
			", couponType=" + couponType +
			", createdAt=" + createdAt +
			", createdBy=" + createdBy +
			", updatedAt=" + updatedAt +
			", updatedBy=" + updatedBy +
			", isValid=" + isValid +
			"}";
	}
}
