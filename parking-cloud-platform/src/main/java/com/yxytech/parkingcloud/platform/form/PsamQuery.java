package com.yxytech.parkingcloud.platform.form;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class PsamQuery implements Serializable {

    private Integer page;
    private Integer size;
    private Date putInAt;
    private Date putOutAt;
    private Date startusingAt;
    private Long facOrgId;
    private Long manageOrgId;
    private String manager;
    private Long terminalDeviceId;
    private String psamCardNumber;
    private String psamType;
    private String start_time;
    private String end_time;

    public Integer getPage() {
        if(page == null || page < 1){
            return  1;
        }
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        if(size == null || size < 1){
            return 10;
        }
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
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

    public Long getTerminalDeviceId() {
        return terminalDeviceId;
    }

    public void setTerminalDeviceId(Long terminalDeviceId) {
        this.terminalDeviceId = terminalDeviceId;
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
}
