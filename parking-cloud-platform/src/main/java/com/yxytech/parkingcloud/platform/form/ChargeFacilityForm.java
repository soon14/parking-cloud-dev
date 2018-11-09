package com.yxytech.parkingcloud.platform.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class ChargeFacilityForm implements Serializable {

    private Long id;

    @NotBlank(message = "设备系统编号不能为空")
    private String systemNumber;

    @NotBlank(message = "设备SN序列号不能为空")
    private String sn;

    @NotNull(message = "设备厂商不能为空")
    private Long facOrgId;

    @NotBlank(message = "设备IP不能为空")
    private String ip;

    @NotBlank(message = "设备IMEI码不能为空")
    private String imei;

    @NotNull(message = "设备管理单位不能为空")
    private Long manageOrgId;

    @NotBlank(message = "设备管理员不能为空")
    private String manager;

    @NotNull(message = "所属停车场不能为空")
    private Long parkingId;

    private String remarks;

    private Boolean status;

    private Date putInAt;

    private transient String manageOrgName;
    private transient String facOrgName;
    private transient String parkingName;

    public String getManageOrgName() {
        return manageOrgName;
    }

    public void setManageOrgName(String manageOrgName) {
        this.manageOrgName = manageOrgName;
    }

    public String getFacOrgName() {
        return facOrgName;
    }

    public void setFacOrgName(String facOrgName) {
        this.facOrgName = facOrgName;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getPutInAt() {
        return putInAt;
    }

    public void setPutInAt(Date putInAt) {
        this.putInAt = putInAt;
    }
}
