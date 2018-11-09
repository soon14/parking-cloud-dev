package com.yxytech.parkingcloud.platform.form;

import java.io.Serializable;

public class ParkingCellQuery implements Serializable {

    private Integer page;
    private Integer size;
    private String code;
    private String roadbedCode;
    private Long parkingId;
    private String gps;
    private Boolean isUsing;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoadbedCode() {
        return roadbedCode;
    }

    public void setRoadbedCode(String roadbedCode) {
        this.roadbedCode = roadbedCode;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
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
