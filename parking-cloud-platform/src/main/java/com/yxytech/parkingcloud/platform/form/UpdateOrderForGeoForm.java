package com.yxytech.parkingcloud.platform.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.enums.ColorsEnum;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class UpdateOrderForGeoForm implements Serializable {
    @NotBlank(message = "车牌号码必须填写!")
    private String plateNumber;
    @NotNull(message = "车牌颜色不能为空!")
    private ColorsEnum plateColor;
    @NotNull(message = "停车场不能为空!")
    private Long parkingId;
    @NotNull(message = "订单编号不能为空!")
    private Long orderId;
    private CarTypeEnum carType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enterAt;
    @NotNull(message = "修改原因不能为空")
    private String remark;
    private String deviceSn;
    private Long inParkingPortId;
    private Long inParkingLaneId;
    @NotNull(message = "车位id不能为空")
    private Long parkingCellId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
