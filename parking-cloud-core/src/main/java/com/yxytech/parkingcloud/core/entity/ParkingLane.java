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
 * @since 2017-10-18
 */
@TableName("yxy_parking_lane")
public class ParkingLane extends SuperModel<ParkingLane> {

    private static final long serialVersionUID = 1L;

	private Long id;
	private String code;
	@TableField("lane_type")
	private Integer laneType;
	@TableField("parking_id")
	private Long parkingId;
	@TableField("parking_port_id")
	private Long parkingPortId;
	@TableField("port_type")
	private Integer portType;
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
	private transient String parkingName;
	private transient String parkingPortCode;

	public String getParkingPortCode() {
		return parkingPortCode;
	}

	public void setParkingPortCode(String parkingPortCode) {
		this.parkingPortCode = parkingPortCode;
	}

	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	public Boolean getUsing() {
		return isUsing;
	}

	public void setUsing(Boolean using) {
		isUsing = using;
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
		return "ParkingLane{" +
			"id=" + id +
			", code=" + code +
			", laneType=" + laneType +
			", parkingId=" + parkingId +
			", parkingPortId=" + parkingPortId +
			", portType=" + portType +
			", createdBy=" + createdBy +
			", createdAt=" + createdAt +
			", updatedBy=" + updatedBy +
			", updatedAt=" + updatedAt +
			", isUsing=" + isUsing +
			"}";
	}
}
