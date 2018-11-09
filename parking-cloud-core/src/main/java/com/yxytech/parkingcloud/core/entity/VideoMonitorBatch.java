package com.yxytech.parkingcloud.core.entity;

public class VideoMonitorBatch {

    private String systemNumber;
    private String sn;
    private String gps;
    private String ip;
    private String manager;
    private String parkingCellCode;
    private String videoAddress;
    private String remarks;
    private transient String facOrgName;
    private transient String manageOrgName;
    private transient String parkingName;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getVideoAddress() {
        return videoAddress;
    }

    public void setVideoAddress(String videoAddress) {
        this.videoAddress = videoAddress;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
