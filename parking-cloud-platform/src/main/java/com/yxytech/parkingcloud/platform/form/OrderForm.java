package com.yxytech.parkingcloud.platform.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.enums.ColorsEnum;
import com.yxytech.parkingcloud.core.enums.OrderFromTypeEnum;

import java.io.Serializable;
import java.util.Date;

public class OrderForm implements Serializable {

    private String plateNumber;
    private ColorsEnum plateColor;
    private Long parkingId;
    private CarTypeEnum carType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enterAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date leaveAt;
    private OrderFromTypeEnum fromType;
    private String remark;
    private String deviceSn;
    private Long inParkingPortId;
    private Long outParkingPortId;
    private Long inParkingLaneId;
    private Long outParkingLaneId;
    private Long parkingCellId;
    private Double receivableAmount;

    public CarTypeEnum getCarType() {
        return carType;
    }

    public void setCarType(CarTypeEnum carType) {
        this.carType = carType;
    }

    public Double getReceivableAmount() {
        return receivableAmount;
    }

    public void setReceivableAmount(Double receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
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

    public void setPlateColor(ColorsEnum plateColor) {
        this.plateColor = plateColor;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
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

    public OrderFromTypeEnum getFromType() {
        return fromType;
    }

    public void setFromType(OrderFromTypeEnum fromType) {
        this.fromType = fromType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getInParkingPortId() {
        return inParkingPortId;
    }

    public void setInParkingPortId(Long inParkingPortId) {
        this.inParkingPortId = inParkingPortId;
    }

    public Long getOutParkingPortId() {
        return outParkingPortId;
    }

    public void setOutParkingPortId(Long outParkingPortId) {
        this.outParkingPortId = outParkingPortId;
    }

    public Long getInParkingLaneId() {
        return inParkingLaneId;
    }

    public void setInParkingLaneId(Long inParkingLaneId) {
        this.inParkingLaneId = inParkingLaneId;
    }

    public Long getOutParkingLaneId() {
        return outParkingLaneId;
    }

    public void setOutParkingLaneId(Long outParkingLaneId) {
        this.outParkingLaneId = outParkingLaneId;
    }

    public Long getParkingCellId() {
        return parkingCellId;
    }

    public void setParkingCellId(Long parkingCellId) {
        this.parkingCellId = parkingCellId;
    }
}
