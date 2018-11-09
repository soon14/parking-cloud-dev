package com.yxytech.parkingcloud.platform.form;

import java.io.Serializable;

public class ParkingOperatorQuery implements Serializable {

    private Integer page;
    private Integer size;
    private Long operatorOrgId;
    private Long parkingId;
    private Boolean isUsing;
    private String start_time;
    private String end_time;
    private Integer approveStatus;

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Integer getPage() {
        if(page == null || page < 1){
            return 1;
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

    public Long getOperatorOrgId() {
        return operatorOrgId;
    }

    public void setOperatorOrgId(Long operatorOrgId) {
        this.operatorOrgId = operatorOrgId;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
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
