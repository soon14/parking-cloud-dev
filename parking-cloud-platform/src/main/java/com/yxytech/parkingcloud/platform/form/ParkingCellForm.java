package com.yxytech.parkingcloud.platform.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ParkingCellForm implements Serializable {

    private Long id;

    @NotBlank(message = "车位编号不能为空")
    private String code;

    @NotBlank(message = "路面编码不能为空")
    private String roadbedCode;

    @NotNull(message = "所属停车场不能为空")
    private Long parkingId;

    @NotBlank(message = "GPS位置信息不能为空")
    private String gps;

    @NotBlank(message = "停车位长度不能为空")
    private String parkingCellLong;

    @NotBlank(message = "停车位宽度不能为空")
    private String parkingCellWide;

    @NotBlank(message = "车位朝向不能为空")
    private String parkingCellDirection;

    private Boolean isUsing;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getParkingCellLong() {
        return parkingCellLong;
    }

    public void setParkingCellLong(String parkingCellLong) {
        this.parkingCellLong = parkingCellLong;
    }

    public String getParkingCellWide() {
        return parkingCellWide;
    }

    public void setParkingCellWide(String parkingCellWide) {
        this.parkingCellWide = parkingCellWide;
    }

    public String getParkingCellDirection() {
        return parkingCellDirection;
    }

    public void setParkingCellDirection(String parkingCellDirection) {
        this.parkingCellDirection = parkingCellDirection;
    }

    public Boolean getUsing() {
        return isUsing;
    }

    public void setUsing(Boolean using) {
        isUsing = using;
    }
}
