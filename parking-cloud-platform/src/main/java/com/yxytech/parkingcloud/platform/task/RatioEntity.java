package com.yxytech.parkingcloud.platform.task;

public class RatioEntity {
    private Long parkingId;
    private Long usedTimeLength;
    private Integer inTimes;
    private Integer occupancy;
    private Integer cells;
    private String parkingName;

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public Integer getCells() {
        return cells;
    }

    public void setCells(Integer cells) {
        this.cells = cells;
    }

    public Long getUsedTimeLength() {
        return usedTimeLength;
    }

    public void setUsedTimeLength(Long usedTimeLength) {
        this.usedTimeLength = usedTimeLength;
    }

    public Integer getInTimes() {
        return inTimes;
    }

    public void setInTimes(Integer inTimes) {
        this.inTimes = inTimes;
    }

    public Integer getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(Integer occupancy) {
        this.occupancy = occupancy;
    }
}
