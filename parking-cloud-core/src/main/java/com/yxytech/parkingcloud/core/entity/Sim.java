package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author cj
 * @since 2017-10-24
 */
@TableName("yxy_sim")
public class Sim extends SuperModel<Sim> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("sim_number")
	private String simNumber;
	private String imsi;
	private String operator;
	@TableField("network_type")
	private String networkType;
	@TableField("network_flow")
	private String networkFlow;
	private Boolean status;
	@TableField("put_in_at")
	private Date putInAt;
	@TableField("put_out_at")
	private Date putOutAt;
	@TableField("startusing_at")
	private Date startusingAt;
	@TableField("fac_org_id")
	private Long facOrgId;
	@TableField("manage_org_id")
	private Long manageOrgId;
	@TableField("parking_id")
	private Long parkingId;
	private String manager;
	@TableField("teminal_device_id")
	private Long teminalDeviceId;
	private String remarks;
	private transient String facOrgName;
	private transient String manageOrgName;
	private transient String teminalDeviceCode;
	private transient String parkingName;

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

	public String getTeminalDeviceCode() {
		return teminalDeviceCode;
	}

	public void setTeminalDeviceCode(String teminalDeviceCode) {
		this.teminalDeviceCode = teminalDeviceCode;
	}

	public String getFacOrgName() {
		return facOrgName;
	}

	public void setFacOrgName(String facOrgName) {
		this.facOrgName = facOrgName;
	}

	public String getManageOrgName() {
		return manageOrgName;
	}

	public void setManageOrgName(String manageOrgName) {
		this.manageOrgName = manageOrgName;
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

	public String getSimNumber() {
		return simNumber;
	}

	public void setSimNumber(String simNumber) {
		this.simNumber = simNumber;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getNetworkFlow() {
		return networkFlow;
	}

	public void setNetworkFlow(String networkFlow) {
		this.networkFlow = networkFlow;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getPutInAt() {
		return putInAt;
	}

	public void setPutInAt(Date putInAt) {
		this.putInAt = putInAt;
	}

	public Date getPutOutAt() {
		return putOutAt;
	}

	public void setPutOutAt(Date putOutAt) {
		this.putOutAt = putOutAt;
	}

	public Date getStartusingAt() {
		return startusingAt;
	}

	public void setStartusingAt(Date startusingAt) {
		this.startusingAt = startusingAt;
	}

	public Long getFacOrgId() {
		return facOrgId;
	}

	public void setFacOrgId(Long facOrgId) {
		this.facOrgId = facOrgId;
	}

	public Long getManageOrgId() {
		return manageOrgId;
	}

	public void setManageOrgId(Long manageOrgId) {
		this.manageOrgId = manageOrgId;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Long getTeminalDeviceId() {
		return teminalDeviceId;
	}

	public void setTeminalDeviceId(Long teminalDeviceId) {
		this.teminalDeviceId = teminalDeviceId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Sim{" +
			"id=" + id +
			", simNumber=" + simNumber +
			", imsi=" + imsi +
			", operator=" + operator +
			", networkType=" + networkType +
			", networkFlow=" + networkFlow +
			", status=" + status +
			", putInAt=" + putInAt +
			", putOutAt=" + putOutAt +
			", startusingAt=" + startusingAt +
			", facOrgId=" + facOrgId +
			", manageOrgId=" + manageOrgId +
			", manager=" + manager +
			", teminalDeviceId=" + teminalDeviceId +
			", remarks=" + remarks +
			"}";
	}
}
