package com.yxytech.parkingcloud.platform.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class PsamForm implements Serializable {

    private Long id;

    @NotBlank(message = "PSAM卡号不能为空")
    private String psamCardNumber;

    @NotBlank(message = "PSAM类型不能为空")
    private String psamType;

    @NotNull(message = "管理单位不能为空")
    private Long manageOrgId;

    @NotBlank(message = "管理员不能为空")
    private String manager;

    @NotNull(message = "绑定的终端ID不能为空")
    private Long terminalDeviceId;

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

    public Long getTerminalDeviceId() {
        return terminalDeviceId;
    }

    public void setTerminalDeviceId(Long terminalDeviceId) {
        this.terminalDeviceId = terminalDeviceId;
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
