package com.yxytech.parkingcloud.app.form;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class PaymentForm {
    @NotNull(message = "订单编号不能为空!")
    @Min(value = 0, message = "非法的订单!")
    private Long orderId;
    private List<Long> promoCodes;
    @NotNull(message = "计算时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date calculateTime;

    public Date getCalculateTime() {
        return calculateTime;
    }

    public void setCalculateTime(Date calculateTime) {
        this.calculateTime = calculateTime;
    }

    public List<Long> getPromoCodes() {
        return promoCodes;
    }

    public void setPromoCodes(List<Long> promoCodes) {
        this.promoCodes = promoCodes;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
