package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author cj
 * @since 2017-10-19
 */
@TableName("yxy_parking_cell")
public class ParkingCell extends SuperModel<ParkingCell> {

    private static final long serialVersionUID = 1L;

	private Long id;
	private String code;
	@TableField("roadbed_code")
	private String roadbedCode;
	@TableField("parking_id")
	private Long parkingId;
	private String gps;
	@TableField(value ="gps[0]")
	private Double longitude;
	@TableField(value = "gps[1]")
	private Double latitude;
	@TableField("parking_cell_long")
	private String parkingCellLong;
	@TableField("parking_cell_wide")
	private String parkingCellWide;
	@TableField("parking_cell_direction")
	private String parkingCellDirection;
	@TableField("is_using")
	private Boolean isUsing;
	@TableField(value = "created_by",fill = FieldFill.INSERT)
	private Long createdBy;
	@TableField(value = "created_at",fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "updated_by",fill = FieldFill.INSERT_UPDATE)
	private Long updatedBy;
	@TableField(value = "updated_at",fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;
	private transient String parkingName;

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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ParkingCell{" +
			"id=" + id +
			", code=" + code +
			", roadbedCode=" + roadbedCode +
			", parkingId=" + parkingId +
			", gps=" + gps +
			", parkingCellLong=" + parkingCellLong +
			", parkingCellWide=" + parkingCellWide +
			", parkingCellDirection=" + parkingCellDirection +
			", isUsing=" + isUsing +
			", createdBy=" + createdBy +
			", createdAt=" + createdAt +
			", updatedBy=" + updatedBy +
			", updatedAt=" + updatedAt +
			"}";
	}
}
