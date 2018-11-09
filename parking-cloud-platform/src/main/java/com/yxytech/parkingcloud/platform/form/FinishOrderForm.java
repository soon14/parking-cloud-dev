package com.yxytech.parkingcloud.platform.form;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class FinishOrderForm implements Serializable {
    @NotNull(message = "订单id必须填写!")
    private Long orderId;
    @NotNull(message = "停车场id必须填写!")
    private Long parkingId;
    @NotNull(message = "离场时间必须填写!")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date leaveAt;
    private String deviceSn;
    private Long outParkingPortId;
    private Long outParkingLaneId;

    public Long getOutParkingPortId() {
        return outParkingPortId;
    }

    public void setOutParkingPortId(Long outParkingPortId) {
        this.outParkingPortId = outParkingPortId;
    }

    public Long getOutParkingLaneId() {
        return outParkingLaneId;
    }

    public void setOutParkingLaneId(Long outParkingLaneId) {
        this.outParkingLaneId = outParkingLaneId;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getLeaveAt() {
        return leaveAt;
    }

    public void setLeaveAt(Date leaveAt) {
        this.leaveAt = leaveAt;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }
}
