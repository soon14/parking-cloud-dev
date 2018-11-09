package com.yxytech.parkingcloud.core.entity;

public class MagneticDeviceBatch {

    private String systemNumber;
    private String sn;
    private String gps;
    private String ip;
    private String batteryVoltage;
    private String batteryPower;
    private String manager;
    private String parkingCellCode;
    private String facOrgName;
    private String manageOrgName;
    private String parkingName;
    private String remarks;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getParkingCellCode() {
        return parkingCellCode;
    }

    public void setParkingCellCode(String parkingCellCode) {
        this.parkingCellCode = parkingCellCode;
    }

    public String getFacOrgName() {
        return facOrgName;
    }

    public void setFacOrgName(String facOrgName) {
        this.facOrgName = facOrgName;
    }

    public String getManageOrgName() {
        return manageOrgName;
    }

    public void setManageOrgName(String manageOrgName) {
        this.manageOrgName = manageOrgName;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }
}
