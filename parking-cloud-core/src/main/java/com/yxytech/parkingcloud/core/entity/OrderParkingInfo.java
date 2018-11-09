package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwd
 * @since 2017-10-26
 */
@TableName("yxy_order_parking_info")
public class OrderParkingInfo extends SuperModel<OrderParkingInfo> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("order_id")
	private Long orderId;
	@TableField("parking_id")
	private Long parkingId;
	@TableField("parking_name")
	private String parkingName;
	@TableField("parking_address")
	private String parkingAddress;
	@TableField("in_parking_port_id")
	private Long inParkingPortId;
	@TableField("in_parking_port_code")
	private String inParkingPortCode;
	@TableField("out_parking_port_id")
	private Long outParkingPortId;
	@TableField("out_parking_port_code")
	private String outParkingPortCode;
	@TableField("in_parking_lane_id")
	private Long inParkingLaneId;
	@TableField("in_parking_lane_code")
	private String inParkingLaneCode;
	@TableField("out_parking_lane_id")
	private Long outParkingLaneId;
	@TableField("out_parking_lane_code")
	private String outParkingLaneCode;
	@TableField("parking_cell_id")
	private Long parkingCellId;
	@TableField("parking_cell_code")
	private String parkingCellCode;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getParkingId() {
		return parkingId;
	}

	public void setParkingId(Long parkingId) {
		this.parkingId = parkingId;
	}

	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	public String getParkingAddress() {
		return parkingAddress;
	}

	public void setParkingAddress(String parkingAddress) {
		this.parkingAddress = parkingAddress;
	}

	public Long getInParkingPortId() {
		return inParkingPortId;
	}

	public void setInParkingPortId(Long inParkingPortId) {
		this.inParkingPortId = inParkingPortId;
	}

	public String getInParkingPortCode() {
		return inParkingPortCode;
	}

	public void setInParkingPortCode(String inParkingPortCode) {
		this.inParkingPortCode = inParkingPortCode;
	}

	public Long getOutParkingPortId() {
		return outParkingPortId;
	}

	public void setOutParkingPortId(Long outParkingPortId) {
		this.outParkingPortId = outParkingPortId;
	}

	public String getOutParkingPortCode() {
		return outParkingPortCode;
	}

	public void setOutParkingPortCode(String outParkingPortCode) {
		this.outParkingPortCode = outParkingPortCode;
	}

	public Long getInParkingLaneId() {
		return inParkingLaneId;
	}

	public void setInParkingLaneId(Long inParkingLaneId) {
		this.inParkingLaneId = inParkingLaneId;
	}

	public String getInParkingLaneCode() {
		return inParkingLaneCode;
	}

	public void setInParkingLaneCode(String inParkingLaneCode) {
		this.inParkingLaneCode = inParkingLaneCode;
	}

	public Long getOutParkingLaneId() {
		return outParkingLaneId;
	}

	public void setOutParkingLaneId(Long outParkingLaneId) {
		this.outParkingLaneId = outParkingLaneId;
	}

	public String getOutParkingLaneCode() {
		return outParkingLaneCode;
	}

	public void setOutParkingLaneCode(String outParkingLaneCode) {
		this.outParkingLaneCode = outParkingLaneCode;
	}

	public Long getParkingCellId() {
		return parkingCellId;
	}

	public void setParkingCellId(Long parkingCellId) {
		this.parkingCellId = parkingCellId;
	}

	public String getParkingCellCode() {
		return parkingCellCode;
	}

	public void setParkingCellCode(String parkingCellCode) {
		this.parkingCellCode = parkingCellCode;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "OrderParkingInfo{" +
			"id=" + id +
			", orderId=" + orderId +
			", parkingId=" + parkingId +
			", parkingName=" + parkingName +
			", parkingAddress=" + parkingAddress +
			", inParkingPortId=" + inParkingPortId +
			", inParkingPortCode=" + inParkingPortCode +
			", outParkingPortId=" + outParkingPortId +
			", outParkingPortCode=" + outParkingPortCode +
			", inParkingLaneId=" + inParkingLaneId +
			", inParkingLaneCode=" + inParkingLaneCode +
			", outParkingLaneId=" + outParkingLaneId +
			", outParkingLaneCode=" + outParkingLaneCode +
			", parkingCellId=" + parkingCellId +
			", parkingCellCode=" + parkingCellCode +
			"}";
	}
}
