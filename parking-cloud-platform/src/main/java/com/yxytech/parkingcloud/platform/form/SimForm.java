package com.yxytech.parkingcloud.platform.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class SimForm implements Serializable {

    private Long id;

    @NotBlank(message = "SIM卡号不能为空")
    private String simNumber;

    @NotBlank(message = "IMSI码不能为空")
    private String imsi;

    @NotBlank(message = "运营商不能为空")
    private String operator;

    @NotBlank(message = "网络制式不能为空")
    private String networkType;

    private String networkFlow;

    private Boolean status;

    @NotNull(message = "设备厂商不能为空")
    private Long facOrgId;

    @NotNull(message = "设备管理单位不能为空")
    private Long manageOrgId;

    @NotNull(message = "设备管理员不能为空")
    private String manager;

    @NotNull(message = "设备绑定终端不能为空")
    private Long teminalDeviceId;

    @NotNull(message = "请选择停车场")
    private Long parkingId;

    private String remarks;

    private Date putInAt;

    private Date putOutAt;

    private Date startusingAt;

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getFacOrgId() {
        return facOrgId;
    }

    public void setFacOrgId(Long facOrgId) {
        this.facOrgId = facOrgId;
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

    public Long getTeminalDeviceId() {
        return teminalDeviceId;
    }

    public void setTeminalDeviceId(Long teminalDeviceId) {
        this.teminalDeviceId = teminalDeviceId;
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
}
