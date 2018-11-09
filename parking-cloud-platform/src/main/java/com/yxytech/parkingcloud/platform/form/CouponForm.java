package com.yxytech.parkingcloud.platform.form;

import com.yxytech.parkingcloud.core.enums.CouponEnum;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class CouponForm implements Serializable {
    @NotBlank(message = "优惠券描述不能为空!")
    private String describe;
    @NotNull(message = "停车场不能为空!")
    private Long parkingId;
    @NotNull(message = "优惠券启用时间不能为空!")
    private Date startedAt;
    @NotNull(message = "优惠券停用时间不能为空!")
    private Date endAt;
    @NotNull(message = "开始领取优惠券时间不能为空!")
    private Date timeIntervalStart;
    @NotNull(message = "结束领取优惠券时间不能为空!")
    private Date timeIntervalEnd;
    @NotNull(message = "优惠券优惠类型不能为空!")
    private CouponEnum couponType;
    @NotNull(message = "是否可以叠加使用不能为空!")
    private Boolean canSuperposed;
    private Integer maxOfSuperposed;
    @NotNull(message = "是否可以和白名单同时使用!")
    private Boolean canUseWithWhitelist;
    @NotNull(message = "最低使用金额不能为空!")
    private Double minUseMoney;
    private Double maxSuperposedMoney;
    @NotNull(message = "优惠码个数不能为空!")
    @Min(value = 1, message = "优惠码个数不能为空!")
    private Integer times;
    @NotNull(message = "优惠金额信息不能为空!")
    private Double couponInfo;

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

    public CouponEnum getCouponType() {
        return couponType;
    }

    public void setCouponType(CouponEnum couponType) {
        this.couponType = couponType;
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

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}
