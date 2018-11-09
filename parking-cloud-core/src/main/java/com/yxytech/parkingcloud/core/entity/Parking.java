package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author cj
 * @since 2017-10-18
 */
@TableName("yxy_parking")
public class Parking extends SuperModel<Parking> {

    private static final long serialVersionUID = 1L;

	private Long id;
	private String code;
	@TableField("full_name")
	private String fullName;
	@TableField("short_name")
	private String shortName;
	@TableField("road_type")
	private Integer roadType;
	@TableField("open_type")
	private Integer openType;
	@TableField("fee_type")
	private Integer feeType;
	private Integer layers;
	@TableField("underground_layers")
	private Integer undergroundLayers;
	@TableField("overground_layers")
	private Integer overgroundLayers;
	@TableField("business_number")
	private String businessNumber;
	@TableField("charge_licence")
	private String chargeLicence;
	@TableField("approve_status")
	private ApproveEnum approveStatus;
	@TableField("owner_org_id")
	private Long ownerOrgId;
	@TableField("operator_org_id")
	private Long operatorOrgId;
	@TableField("regulatory_id")
	private Long regulatoryId;
	private String principal;
	@TableField("phone_number")
	private String phoneNumber;
	private String email;
	private String address;
	private String gps;
	@TableField("parking_cells")
	private Integer parkingCells;
	@TableField("business_hours_desc")
	private String businessHoursDesc;
	@TableField("all_day")
	private Boolean allDay;
	@TableField("start_time")
	private String startTime;
	@TableField("end_time")
	private String endTime;
	private String rates;
	@TableField("street_area_id")
	private Long streetAreaId;
	@TableField("manage_prove")
	private String manageProve;
	@TableField("prove_images")
	private String proveImages;
	@TableField("parking_cell_map")
	private String parkingCellMap;
	@TableField(value = "created_by",fill = FieldFill.INSERT)
	private Long createdBy;
	@TableField(value = "created_at",fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "updated_by",fill = FieldFill.INSERT_UPDATE)
	private Long updatedBy;
	@TableField(value = "updated_at",fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;
	@TableField("is_using")
	private Boolean isUsing;
	@TableField(value ="gps[0]")
	private Double longitude;
	@TableField(value = "gps[1]")
	private Double latitude;
	private transient Integer usedCells;
	private transient Integer unUsedCells;
	private transient String ownerOrgName;
	private transient String operatorOrgName;
	private transient String streetAreaName;
    private transient String regulatoryName;
	private transient List<OrderInfo> orderInfos;
	private transient List<OrderTransaction> orderTransactions;

	public Integer getUnUsedCells() {
		return unUsedCells;
	}

	public void setUnUsedCells(Integer unUsedCells) {
		this.unUsedCells = unUsedCells;
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

	public List<OrderTransaction> getOrderTransactions() {
		return orderTransactions;
	}

	public void setOrderTransactions(List<OrderTransaction> orderTransactions) {
		this.orderTransactions = orderTransactions;
	}

	public String getRegulatoryName() {
		return regulatoryName;
	}

	public void setRegulatoryName(String regulatoryName) {
		this.regulatoryName = regulatoryName;
	}

	public Long getRegulatoryId() {
		return regulatoryId;
	}

	public void setRegulatoryId(Long regulatoryId) {
		this.regulatoryId = regulatoryId;
	}

	public List<OrderInfo> getOrderInfos() {
		return orderInfos;
	}

	public void setOrderInfos(List<OrderInfo> orderInfos) {
		this.orderInfos = orderInfos;
	}

	public String getOwnerOrgName() {
		return ownerOrgName;
	}

	public void setOwnerOrgName(String ownerOrgName) {
		this.ownerOrgName = ownerOrgName;
	}

	public String getOperatorOrgName() {
		return operatorOrgName;
	}

	public void setOperatorOrgName(String operatorOrgName) {
		this.operatorOrgName = operatorOrgName;
	}

	public String getStreetAreaName() {
		return streetAreaName;
	}

	public void setStreetAreaName(String streetAreaName) {
		this.streetAreaName = streetAreaName;
	}

	public Integer getUsedCells() {
		return usedCells;
	}

	public void setUsedCells(Integer usedCells) {
		this.usedCells = usedCells;
	}

	public Boolean getAllDay() {
		return allDay;
	}

	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getManageProve() {
		return manageProve;
	}

	public void setManageProve(String manageProve) {
		this.manageProve = manageProve;
	}

	public String getProveImages() {
		return proveImages;
	}

	public void setProveImages(String proveImages) {
		this.proveImages = proveImages;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public ApproveEnum getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(ApproveEnum approveStatus) {
		this.approveStatus = approveStatus;
	}

	public Boolean getUsing() {
		return isUsing;
	}

	public void setUsing(Boolean using) {
		isUsing = using;
	}

	public String getParkingCellMap() {
		return parkingCellMap;
	}

	public void setParkingCellMap(String parkingCellMap) {
		this.parkingCellMap = parkingCellMap;
	}

	@Override
	public String toString() {
		return "Parking{" +
			"id=" + id +
			", code=" + code +
			", fullName=" + fullName +
			", shortName=" + shortName +
			", roadType=" + roadType +
			", openType=" + openType +
			", feeType=" + feeType +
			", layers=" + layers +
			", undergroundLayers=" + undergroundLayers +
			", overgroundLayers=" + overgroundLayers +
			", businessNumber=" + businessNumber +
			", chargeLicence=" + chargeLicence +
			", ownerOrgId=" + ownerOrgId +
			", operatorOrgId=" + operatorOrgId +
			", principal=" + principal +
			", phoneNumber=" + phoneNumber +
			", email=" + email +
			", address=" + address +
			", gps=" + gps +
			",  parkingCells=" + parkingCells +
			", businessHoursDesc=" + businessHoursDesc +
			", rates=" + rates +
			", streetAreaId=" + streetAreaId +
			", createdBy=" + createdBy +
			", createdAt=" + createdAt +
			", updatedBy=" + updatedBy +
			", updatedAt=" + updatedAt +
			", isUsing=" + isUsing +
			"}";
	}
}
