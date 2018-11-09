package com.yxytech.parkingcloud.platform.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class MagneticDeviceForm implements Serializable{

    private Long id;

    @NotBlank(message = "设备系统编号不能为空")
    private String systemNumber;

    @NotBlank(message = "设备SN序列号不能为空")
    private String sn;

    @NotNull(message = "设备厂商不能为空")
    private Long facOrgId;

    @NotBlank(message = "设备IP不能为空")
    private String ip;

    @NotNull(message = "设备管理单位不能为空")
    private Long manageOrgId;

    @NotBlank(message = "设备管理员不能为空")
    private String manager;

    @NotNull(message = "所属停车场不能为空")
    private Long parkingId;

    private Boolean status;

    @NotBlank(message = "设备所属停车位编号不能为空")
    private String parkingCellCode;

    @NotBlank(message = "设备安装GPS信息不能为空")
    private String gps;

    private String remarks;

    private Date putInAt;

    private Date putOutAt;

    private Date startusingAt;

    private String batteryVoltage;

    private String batteryPower;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemNumber() {
        return systemNumber;
    }

    public void setSystemNumber(String systemNumber) {
        this.systemNumber = systemNumber;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Long getFacOrgId() {
        return facOrgId;
    }

    public void setFacOrgId(Long facOrgId) {
        this.facOrgId = facOrgId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getManageOrgId() {
        return manageOrgId;
    }

    public void setManageOrgId(Long manageOrgId) {
        this.manageOrgId = manageOrgId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getParkingCellCode() {
        return parkingCellCode;
    }

    public void setParkingCellCode(String parkingCellCode) {
        this.parkingCellCode = parkingCellCode;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getPutInAt() {
        return putInAt;
    }

    public void setPutInAt(Date putInAt) {
        this.putInAt = putInAt;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getPutOutAt() {
        return putOutAt;
    }

    public void setPutOutAt(Date putOutAt) {
        this.putOutAt = putOutAt;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getStartusingAt() {
        return startusingAt;
    }

    public void setStartusingAt(Date startusingAt) {
        this.startusingAt = startusingAt;
    }

    public String getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(String batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public String getBatteryPower() {
        return batteryPower;
    }

    public void setBatteryPower(String batteryPower) {
        this.batteryPower = batteryPower;
    }
}
