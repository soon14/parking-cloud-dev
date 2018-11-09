package com.yxytech.parkingcloud.core.entity;

public class SimBatch {

    private String simNumber;
    private String imsi;
    private String operator;
    private String networkType;
    private String networkFlow;
    private String manager;
    private String teminalDeviceCode;
    private String remarks;
    private String facOrgName;
    private String manageOrgName;
    private String parkingName;


    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getSimNumber() {
        return simNumber;
    }

    public void setSimNumber(String simNumber) {
        this.simNumber = simNumber;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getNetworkFlow() {
        return networkFlow;
    }

    public void setNetworkFlow(String networkFlow) {
        this.networkFlow = networkFlow;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getTeminalDeviceCode() {
        return teminalDeviceCode;
    }

    public void setTeminalDeviceCode(String teminalDeviceCode) {
        this.teminalDeviceCode = teminalDeviceCode;
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
}
