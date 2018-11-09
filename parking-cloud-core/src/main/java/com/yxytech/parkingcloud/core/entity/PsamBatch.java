package com.yxytech.parkingcloud.core.entity;

public class PsamBatch {

    private String psamCardNumber;
    private String psamType;
    private String manager;
    private String terminalDeviceCode;
    private String remarks;
    private String manageOrgName;
    private String parkingName;

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getPsamCardNumber() {
        return psamCardNumber;
    }

    public void setPsamCardNumber(String psamCardNumber) {
        this.psamCardNumber = psamCardNumber;
    }

    public String getPsamType() {
        return psamType;
    }

    public void setPsamType(String psamType) {
        this.psamType = psamType;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getTerminalDeviceCode() {
        return terminalDeviceCode;
    }

    public void setTerminalDeviceCode(String terminalDeviceCode) {
        this.terminalDeviceCode = terminalDeviceCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getManageOrgName() {
        return manageOrgName;
    }

    public void setManageOrgName(String manageOrgName) {
        this.manageOrgName = manageOrgName;
    }
}
