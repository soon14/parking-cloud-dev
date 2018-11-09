package com.yxytech.parkingcloud.platform.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ParkingLaneForm implements Serializable{

    private Long id;

    @NotBlank(message = "车道编号不能为空")
    private String code;

    @NotNull(message = "车道类型不能为空")
    private Integer laneType;

    @NotNull(message = "所属停车场不能为空")
    private Long parkingId;

    @NotNull(message = "所属出入口不能为空")
    private Long parkingPortId;

    @NotNull(message = "出入口类型不能为空")
    private Integer portType;

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

    public Integer getLaneType() {
        return laneType;
    }

    public void setLaneType(Integer laneType) {
        this.laneType = laneType;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public Long getParkingPortId() {
        return parkingPortId;
    }

    public void setParkingPortId(Long parkingPortId) {
        this.parkingPortId = parkingPortId;
    }

    public Integer getPortType() {
        return portType;
    }

    public void setPortType(Integer portType) {
        this.portType = portType;
    }

    public Boolean getUsing() {
        return isUsing;
    }

    public void setUsing(Boolean using) {
        isUsing = using;
    }
}
