package com.yxytech.parkingcloud.platform.form;


import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class ParkingForm implements Serializable{

    private Long id;

    @NotBlank(message = "停车场编码不能为空")
    private String code;

    @NotBlank(message = "停车场全称不能为空")
    private String fullName;

    @NotBlank(message = "停车场简称不能为空")
    private String shortName;

    @NotNull(message = "停车场类型不能为空")
    private Integer roadType;

    @NotNull(message = "停车场类型不能为空")
    private Integer openType;

    @NotNull(message = "停车场类型不能为空")
    private Integer feeType;

    @NotNull(message = "总层数不能为空")
    private Integer layers;

    @NotNull(message = "地下层数不能为空")
    private Integer undergroundLayers;

    @NotNull(message = "地上层数不能为空")
    private Integer overgroundLayers;

    private String businessNumber;

    private String chargeLicence;

    @NotBlank(message = "负责人不能为空")
    private String principal;

    @Pattern(regexp = "^\\d{11}$|^$", message = "手机号格式不正确")
    private String phoneNumber;

    @Email(message = "电子邮箱格式不正确")
    private String email;

    @NotBlank(message = "地址不能为空")
    private String address;

    @NotBlank(message = "GPS位置信息不能为空")
    private String gps;

    @NotNull(message = "停车位总数不能为空")
    private Integer parkingCells;

    @NotBlank(message = "营业时间不能为空")
    private String businessHoursDesc;

    @NotNull(message = "停车场是否为24小时营业不能为空")
    private Boolean allDay;

    private String startTime;

    private String endTime;

    @NotBlank(message = "收费标准不能为空")
    private String rates;

    @NotNull(message = "所属街道不能为空")
    private Long streetAreaId;

    @NotNull(message = "是否启用不能为空")
    private Boolean isUsing;

    private ApproveEnum approveStatus;

    private Long ownerOrgId;

    private Long operatorOrgId;

    @NotBlank(message = "停车场车位分布图不能为空")
    private String parkingCellMap;

    public String getParkingCellMap() {
        return parkingCellMap;
    }

    public void setParkingCellMap(String parkingCellMap) {
        this.parkingCellMap = parkingCellMap;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }


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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getRoadType() {
        return roadType;
    }

    public void setRoadType(Integer roadType) {
        this.roadType = roadType;
    }

    public Integer getOpenType() {
        return openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }

    public Integer getLayers() {
        return layers;
    }

    public void setLayers(Integer layers) {
        this.layers = layers;
    }

    public Integer getUndergroundLayers() {
        return undergroundLayers;
    }

    public void setUndergroundLayers(Integer undergroundLayers) {
        this.undergroundLayers = undergroundLayers;
    }

    public Integer getOvergroundLayers() {
        return overgroundLayers;
    }

    public void setOvergroundLayers(Integer overgroundLayers) {
        this.overgroundLayers = overgroundLayers;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    public String getChargeLicence() {
        return chargeLicence;
    }

    public void setChargeLicence(String chargeLicence) {
        this.chargeLicence = chargeLicence;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public Integer getParkingCells() {
        return parkingCells;
    }

    public void setParkingCells(Integer parkingCells) {
        this.parkingCells = parkingCells;
    }

    public String getBusinessHoursDesc() {
        return businessHoursDesc;
    }

    public void setBusinessHoursDesc(String businessHoursDesc) {
        this.businessHoursDesc = businessHoursDesc;
    }

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }

    public Long getStreetAreaId() {
        return streetAreaId;
    }

    public void setStreetAreaId(Long streetAreaId) {
        this.streetAreaId = streetAreaId;
    }

    public Boolean getUsing() {
        return isUsing;
    }

    public void setUsing(Boolean using) {
        isUsing = using;
    }

    public ApproveEnum getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(ApproveEnum approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Long getOwnerOrgId() {
        return ownerOrgId;
    }

    public void setOwnerOrgId(Long ownerOrgId) {
        this.ownerOrgId = ownerOrgId;
    }

    public Long getOperatorOrgId() {
        return operatorOrgId;
    }

    public void setOperatorOrgId(Long operatorOrgId) {
        this.operatorOrgId = operatorOrgId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
