package com.yxytech.parkingcloud.app.form;

import javax.validation.constraints.NotNull;

public class ParkingInfoForm {
    @NotNull(message = "经纬度不能为空!")
    private Double longitude;
    @NotNull(message = "经纬度不能为空!")
    private Double latitude;
    private Double leftTopLatitude;
    private Double leftTopLongitude;
    private Integer page;
    private Integer size;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLeftTopLatitude() {
        return leftTopLatitude;
    }

    public void setLeftTopLatitude(Double leftTopLatitude) {
        this.leftTopLatitude = leftTopLatitude;
    }

    public Double getLeftTopLongitude() {
        return leftTopLongitude;
    }

    public void setLeftTopLongitude(Double leftTopLongitude) {
        this.leftTopLongitude = leftTopLongitude;
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
