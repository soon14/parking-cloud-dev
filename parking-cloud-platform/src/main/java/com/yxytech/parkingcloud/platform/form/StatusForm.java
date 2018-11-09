package com.yxytech.parkingcloud.platform.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class StatusForm implements Serializable {
    @NotNull(message = "车牌号码不能为空")
    private String plateNumber;

    @NotNull(message = "车牌颜色不能为空")
    private Integer plateColor;

    @NotNull(message = "停车场id不能为空")
    @Min(1)
    private Integer parkingId;

    @NotNull(message = "入场时间不能为空")
    private String enterTime;

    @NotNull(message = "离场时间不能为空")
    private String leaveTime;

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(Integer plateColor) {
        this.plateColor = plateColor;
    }

    public Integer getParkingId() {
        return parkingId;
    }

    public void setParkingId(Integer parkingId) {
        this.parkingId = parkingId;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }
}
