package com.yxytech.parkingcloud.platform.form;

import com.yxytech.parkingcloud.core.enums.ColorsEnum;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class StatusListCreateForm implements Serializable {
    @NotBlank(message = "车牌号码不能为空")
    private String plateNumber;
    @NotNull(message = "非法的车牌颜色")
    private ColorsEnum plateColor;
    private Long parkingId;
    @NotNull(message = "启用时间不能为空")
    private Date startedAt;
    @NotNull(message = "结束时间不能为空")
    private Date endAt;
    private String joinReason;
    private String outReason;

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

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public String getJoinReason() {
        return joinReason;
    }

    public void setJoinReason(String joinReason) {
        this.joinReason = joinReason;
    }

    public String getOutReason() {
        return outReason;
    }

    public void setOutReason(String outReason) {
        this.outReason = outReason;
    }
}
