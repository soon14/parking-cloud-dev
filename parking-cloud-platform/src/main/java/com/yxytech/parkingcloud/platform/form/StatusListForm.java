package com.yxytech.parkingcloud.platform.form;

import com.yxytech.parkingcloud.core.enums.ColorsEnum;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class StatusListForm implements Serializable {
    @NotBlank(message = "车牌号码不能为空")
    private String plateNumber;
    @NotNull(message = "非法的车牌颜色")
    private ColorsEnum plateColor;
    private Long parkingId;
    @NotBlank(message = "启用时间不能为空")
    private String startedAt;
    @NotBlank(message = "结束时间不能为空")
    private String endAt;
    @NotBlank(message = "加入原因不能为空")
    private String joinReason;
    private String outReason;
    private transient String parkingName;
    private transient String organizationName;

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

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
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

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
