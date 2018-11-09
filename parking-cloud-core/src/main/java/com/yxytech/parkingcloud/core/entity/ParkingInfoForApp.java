package com.yxytech.parkingcloud.core.entity;

public class ParkingInfoForApp {
    private Long id;
    private Double longitude;
    private Double latitude;
    private String parkingName;
    private String parkingFullName;
    private Double distance;
    private Integer parkingCells;
    private Integer unUsedParkingCells;

    public Integer getParkingCells() {
        return parkingCells;
    }

    public void setParkingCells(Integer parkingCells) {
        this.parkingCells = parkingCells;
    }

    public Integer getUnUsedParkingCells() {
        return unUsedParkingCells;
    }

    public void setUnUsedParkingCells(Integer unUsedParkingCells) {
        this.unUsedParkingCells = unUsedParkingCells;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getParkingFullName() {
        return parkingFullName;
    }

    public void setParkingFullName(String parkingFullName) {
        this.parkingFullName = parkingFullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }
}
