package com.yxytech.parkingcloud.platform.form;

import java.io.Serializable;

public class ParkingLaneQuery implements Serializable{

    private Integer page;
    private Integer size;
    private Boolean isUsing;
    private String code;
    private Long parkingId;
    private Integer laneType;
    private String start_time;
    private String end_time;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLaneType() {
        return laneType;
    }

    public void setLaneType(Integer laneType) {
        this.laneType = laneType;
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
