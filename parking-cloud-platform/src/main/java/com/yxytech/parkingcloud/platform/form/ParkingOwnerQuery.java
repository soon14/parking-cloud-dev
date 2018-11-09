package com.yxytech.parkingcloud.platform.form;

import java.io.Serializable;

public class ParkingOwnerQuery implements Serializable {

    private Integer page;
    private Integer size;
    private Long ownerOrgId;
    private Long parkingId;
    private Integer approveStatus;
    private Boolean isUsing;
    private String start_time;
    private String end_time;

    public Integer getPage() {
        if(page == null || page < 1){
            return 1;
        }
        return page;
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

    public void setPage(Integer page) {
        this.page = page;

    }

    public Long getOwnerOrgId() {
        return ownerOrgId;
    }

    public void setOwnerOrgId(Long ownerOrgId) {
        this.ownerOrgId = ownerOrgId;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Boolean getUsing() {
        return isUsing;
    }

    public void setUsing(Boolean using) {
        isUsing = using;
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
}
