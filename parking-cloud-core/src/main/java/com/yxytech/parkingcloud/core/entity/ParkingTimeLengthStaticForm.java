package com.yxytech.parkingcloud.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yxytech.parkingcloud.core.enums.StatisticsTimeLengthEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ParkingTimeLengthStaticForm implements Serializable{

    private Integer type;

    private Integer areaId;

    private Integer parkingId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    private List<StatisticsTimeLengthEnum> parkingType;

    public List<StatisticsTimeLengthEnum> getParkingType() {
        return parkingType;
    }

    public void setParkingType(List<StatisticsTimeLengthEnum> parkingType) {
        this.parkingType = parkingType;
    }

    private Integer page;

    private Integer size;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getParkingId() {
        return parkingId;
    }

    public void setParkingId(Integer parkingId) {
        this.parkingId = parkingId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
