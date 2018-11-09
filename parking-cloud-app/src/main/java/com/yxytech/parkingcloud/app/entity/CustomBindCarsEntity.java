package com.yxytech.parkingcloud.app.entity;

import com.yxytech.parkingcloud.core.enums.CarBindStatus;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.enums.ColorsEnum;

import java.util.Date;

public class CustomBindCarsEntity {
    private Long id;
    private Long carId;
    private Date bindAt;
    private Date unbindAt;
    private String plateNumber;
    private int plateColor;
    private int carType;
    private Boolean isValid;
    private Boolean isCertification;
    private String authImageUrl;
    private Date authRequestTime;
    private Date authFinishedTime;
    private int status;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Date getBindAt() {
        return bindAt;
    }

    public void setBindAt(Date bindAt) {
        this.bindAt = bindAt;
    }

    public Date getUnbindAt() {
        return unbindAt;
    }

    public void setUnbindAt(Date unbindAt) {
        this.unbindAt = unbindAt;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(ColorsEnum plateColor) {
        this.plateColor = (int)plateColor.getValue();
    }

    public int getCarType() {
        return carType;
    }

    public void setCarType(CarTypeEnum carType) {
        this.carType = (int)carType.getValue();
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Boolean getCertification() {
        return isCertification;
    }

    public void setCertification(Boolean certification) {
        isCertification = certification;
    }

    public String getAuthImageUrl() {
        return authImageUrl;
    }

    public void setAuthImageUrl(String authImageUrl) {
        this.authImageUrl = authImageUrl;
    }

    public Date getAuthRequestTime() {
        return authRequestTime;
    }

    public void setAuthRequestTime(Date authRequestTime) {
        this.authRequestTime = authRequestTime;
    }

    public Date getAuthFinishedTime() {
        return authFinishedTime;
    }

    public void setAuthFinishedTime(Date authFinishedTime) {
        this.authFinishedTime = authFinishedTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(CarBindStatus status) {
        this.status = (int)status.getValue();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
