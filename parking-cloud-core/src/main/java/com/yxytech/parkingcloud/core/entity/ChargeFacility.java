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
@TableName("yxy_charge_facility")
public class ChargeFacility extends SuperModel<ChargeFacility> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("system_number")
	private String systemNumber;
	private String sn;
	@TableField("put_in_at")
	private Date putInAt;
	@TableField("put_out_at")
	private Date putOutAt;
	@TableField("startusing_at")
	private Date startusingAt;
	@TableField("fac_org_id")
	private Long facOrgId;
	private String ip;
	private String imei;
	@TableField("manage_org_id")
	private Long manageOrgId;
	private String manager;
	@TableField("parking_id")
	private Long parkingId;
	private String remarks;
	private Boolean status;
	private transient String manageOrgName;
	private transient String facOrgName;
	private transient String parkingName;

	public String getManageOrgName() {
		return manageOrgName;
	}

	public void setManageOrgName(String manageOrgName) {
		this.manageOrgName = manageOrgName;
	}

	public String getFacOrgName() {
		return facOrgName;
	}

	public void setFacOrgName(String facOrgName) {
		this.facOrgName = facOrgName;
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

	public String getSystemNumber() {
		return systemNumber;
	}

	public void setSystemNumber(String systemNumber) {
		this.systemNumber = systemNumber;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
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

	public Long getParkingId() {
		return parkingId;
	}

	public void setParkingId(Long parkingId) {
		this.parkingId = parkingId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ChargeFacility{" +
			"id=" + id +
			", systemNumber=" + systemNumber +
			", sn=" + sn +
			", putInAt=" + putInAt +
			", putOutAt=" + putOutAt +
			", startusingAt=" + startusingAt +
			", facOrgId=" + facOrgId +
			", ip=" + ip +
			", imei=" + imei +
			", manageOrgId=" + manageOrgId +
			", manager=" + manager +
			", parkingId=" + parkingId +
			", remarks=" + remarks +
			", status=" + status +
			"}";
	}
}
