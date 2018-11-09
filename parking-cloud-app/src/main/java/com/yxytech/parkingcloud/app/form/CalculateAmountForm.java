package com.yxytech.parkingcloud.app.form;

import com.yxytech.parkingcloud.core.enums.CarTypeEnum;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculateAmountForm {
    @NotNull(message = "离场时间不能为空!")
    private Date leaveAt;
    @NotNull(message = "入场时间不能为空!")
    private Date enterAt;
    @NotNull(message = "车辆类型不能为空!")
    private CarTypeEnum carType;
    @NotNull(message = "停车场不能为空!")
    private Long parkingId;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public Date getLeaveAt() {
        return leaveAt;
    }

    public void setLeaveAt(String leaveAt) throws ParseException {
        this.leaveAt = simpleDateFormat.parse(leaveAt);
    }

    public Date getEnterAt() {
        return enterAt;
    }

    public void setEnterAt(String enterAt) throws ParseException {
        this.enterAt = simpleDateFormat.parse(enterAt);
    }

    public CarTypeEnum getCarType() {
        return carType;
    }

    public void setCarType(CarTypeEnum carType) {
        this.carType = carType;
    }
}
