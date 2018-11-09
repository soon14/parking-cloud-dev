package com.yxytech.parkingcloud.app.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PrePaymentForm {
    @NotNull(message = "订单编号不能为空!")
    @Min(value = 0, message = "非法的订单!")
    private Long orderId;
    @NotNull(message = "预支付金额不能为空!")
    private Integer amount;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
