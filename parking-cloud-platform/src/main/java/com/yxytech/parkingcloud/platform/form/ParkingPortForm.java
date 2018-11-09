package com.yxytech.parkingcloud.platform.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ParkingPortForm implements Serializable {

    private Long id;

    @NotBlank(message = "停车场出入口编号不能为空")
    private String code;

    @NotNull(message = "所属停车场不能为空")
    private Long parkingId;

    @NotNull(message = "出入口类型不能为空")
    private Integer portType;

    @NotBlank(message = "GPS位置信息不能为空")
    private String gps;

    @NotNull(message = "车道数不能为空")
    private Integer lanes;

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

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public Integer getPortType() {
        return portType;
    }

    public void setPortType(Integer portType) {
        this.portType = portType;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public Integer getLanes() {
        return lanes;
    }

    public void setLanes(Integer lanes) {
        this.lanes = lanes;
    }

    public Boolean getUsing() {
        return isUsing;
    }

    public void setUsing(Boolean using) {
        isUsing = using;
    }
}
