package com.yxytech.parkingcloud.platform.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.enums.OrderFromTypeEnum;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class CreateOrderByGeoForm implements Serializable {
    @NotNull(message = "停车场id必须填写!")
    private Long parkingId;
    @NotNull(message = "车辆类型必须填写!")
    private CarTypeEnum carType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "入场时间必须填写!")
    private Date enterAt;
    private OrderFromTypeEnum fromType;
    private String deviceSn;
    private Long inParkingPortId;
    private Long inParkingLaneId;
    @NotNull(message = "车位id不能为空!")
    private Long parkingCellId;

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public CarTypeEnum getCarType() {
        return carType;
    }

    public void setCarType(CarTypeEnum carType) {
        this.carType = carType;
    }

    public Date getEnterAt() {
        return enterAt;
    }

    public void setEnterAt(Date enterAt) {
        this.enterAt = enterAt;
    }

    public OrderFromTypeEnum getFromType() {
        return fromType;
    }

    public void setFromType(OrderFromTypeEnum fromType) {
        this.fromType = fromType;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public Long getInParkingPortId() {
        return inParkingPortId;
    }

    public void setInParkingPortId(Long inParkingPortId) {
        this.inParkingPortId = inParkingPortId;
    }

    public Long getInParkingLaneId() {
        return inParkingLaneId;
    }

    public void setInParkingLaneId(Long inParkingLaneId) {
        this.inParkingLaneId = inParkingLaneId;
    }

    public Long getParkingCellId() {
        return parkingCellId;
    }

    public void setParkingCellId(Long parkingCellId) {
        this.parkingCellId = parkingCellId;
    }
}
