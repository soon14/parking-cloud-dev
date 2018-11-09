package com.yxytech.parkingcloud.app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.enums.ColorsEnum;
import com.yxytech.parkingcloud.core.enums.OrderCarStatusType;
import com.yxytech.parkingcloud.core.enums.OrderStatusEnum;
import com.yxytech.parkingcloud.core.utils.OrderInfoUtil;

import java.util.Date;

public class OrderInfoDetailResponse {
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
    private Double freeAmount;
    private Double invoiceAmount;
    private Double paidAmount;
    private String parkingAddress;
    private String inParkingPortCode;
    private String outParkingPortCode;
    private String inParkingLaneCode;
    private String outParkingLaneCode;
    private String parkingCellCode;
    private CarTypeEnum carType;
    private Boolean isValid;
    private Date calculateTime;

    public Double getPaidAmount() {
        return OrderInfoUtil.formatAmount(paidAmount);
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = OrderInfoUtil.formatAmount(paidAmount);
    }

    public Date getCalculateTime() {
        return calculateTime;
    }

    public void setCalculateTime(Date calculateTime) {
        this.calculateTime = calculateTime;
    }

    public Double getFreeAmount() {
        return OrderInfoUtil.formatAmount(freeAmount);
    }

    public void setFreeAmount(Double freeAmount) {
        this.freeAmount = OrderInfoUtil.formatAmount(freeAmount);
    }

    public Double getInvoiceAmount() {
        return OrderInfoUtil.formatAmount(invoiceAmount);
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = OrderInfoUtil.formatAmount(invoiceAmount);
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public CarTypeEnum getCarType() {
        return carType;
    }

    @JsonProperty("carTypeCode")
    public Integer getCarTypeCode() {
        return (Integer) carType.getValue();
    }

    public void setCarType(CarTypeEnum carType) {
        this.carType = carType;
    }

    public String getParkingAddress() {
        return parkingAddress;
    }

    public void setParkingAddress(String parkingAddress) {
        this.parkingAddress = parkingAddress;
    }

    public String getInParkingPortCode() {
        return inParkingPortCode;
    }

    public void setInParkingPortCode(String inParkingPortCode) {
        this.inParkingPortCode = inParkingPortCode;
    }

    public String getOutParkingPortCode() {
        return outParkingPortCode;
    }

    public void setOutParkingPortCode(String outParkingPortCode) {
        this.outParkingPortCode = outParkingPortCode;
    }

    public String getInParkingLaneCode() {
        return inParkingLaneCode;
    }

    public void setInParkingLaneCode(String inParkingLaneCode) {
        this.inParkingLaneCode = inParkingLaneCode;
    }

    public String getOutParkingLaneCode() {
        return outParkingLaneCode;
    }

    public void setOutParkingLaneCode(String outParkingLaneCode) {
        this.outParkingLaneCode = outParkingLaneCode;
    }

    public String getParkingCellCode() {
        return parkingCellCode;
    }

    public void setParkingCellCode(String parkingCellCode) {
        this.parkingCellCode = parkingCellCode;
    }

    public Double getTotalAmount() {
        return OrderInfoUtil.formatAmount(totalAmount);
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = OrderInfoUtil.formatAmount(totalAmount);
    }

    public Double getReceivableAmount() {
        return OrderInfoUtil.formatAmount(receivableAmount);
    }

    public void setReceivableAmount(Double receivableAmount) {
        this.receivableAmount = OrderInfoUtil.formatAmount(receivableAmount);
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
