package com.yxytech.parkingcloud.app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxytech.parkingcloud.core.enums.ColorsEnum;
import com.yxytech.parkingcloud.core.enums.OrderCarStatusType;
import com.yxytech.parkingcloud.core.enums.OrderStatusEnum;

import java.util.Date;

public class OrderInfoResponse {
    private Long id;
    private String orderNumber;
    private String plateNumber;
    private ColorsEnum plateColor;
    private String parkingName;
    private Date enterAt;
    private Date leaveAt;
    private OrderStatusEnum status;
    private OrderCarStatusType carStatus;
    private Double totalAmount;
    private Double receivableAmount;
    private Double invoiceAmount;
    private Boolean isValid;

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getReceivableAmount() {
        return receivableAmount;
    }

    public void setReceivableAmount(Double receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    @JsonProperty("statusCode")
    public Integer getStatusCode() {
        return (Integer) this.getStatus().getValue();
    }

    @JsonProperty("carStatusCode")
    public Integer getCarStatusCode() {
        return (Integer) this.getCarStatus().getValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public ColorsEnum getPlateColor() {
        return plateColor;
    }

    public Integer getPlateColorCode() {
        return (Integer) plateColor.getValue();
    }

    public void setPlateColor(ColorsEnum plateColor) {
        this.plateColor = plateColor;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public Date getEnterAt() {
        return enterAt;
    }

    public void setEnterAt(Date enterAt) {
        this.enterAt = enterAt;
    }

    public Date getLeaveAt() {
        return leaveAt;
    }

    public void setLeaveAt(Date leaveAt) {
        this.leaveAt = leaveAt;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public OrderCarStatusType getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(OrderCarStatusType carStatus) {
        this.carStatus = carStatus;
    }
}
