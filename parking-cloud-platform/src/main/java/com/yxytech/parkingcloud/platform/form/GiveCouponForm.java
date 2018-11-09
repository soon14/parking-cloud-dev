package com.yxytech.parkingcloud.platform.form;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class GiveCouponForm implements Serializable {
    @NotNull(message = "优惠券不能为空!")
    private Long couponId;
    private Long promoCodeId;
    @NotNull(message = "用户id不能为空!")
    private Long userId;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getPromoCodeId() {
        return promoCodeId;
    }

    public void setPromoCodeId(Long promoCodeId) {
        this.promoCodeId = promoCodeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
