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
@TableName("yxy_psam")
public class Psam extends SuperModel<Psam> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("psam_card_number")
	private String psamCardNumber;
	@TableField("psam_type")
	private String psamType;
	@TableField("put_in_at")
	private Date putInAt;
	@TableField("put_out_at")
	private Date putOutAt;
	@TableField("startusing_at")
	private Date startusingAt;
	@TableField("manage_org_id")
	private Long manageOrgId;
	@TableField("parking_id")
	private Long parkingId;
	private String manager;
	@TableField("terminal_device_id")
	private Long terminalDeviceId;
	private String remarks;
	private transient String manageOrgName;
	private transient String terminalDeviceCode;
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

	public String getTerminalDeviceCode() {
		return terminalDeviceCode;
	}

	public void setTerminalDeviceCode(String terminalDeviceCode) {
		this.terminalDeviceCode = terminalDeviceCode;
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

	public String getPsamCardNumber() {
		return psamCardNumber;
	}

	public void setPsamCardNumber(String psamCardNumber) {
		this.psamCardNumber = psamCardNumber;
	}

	public String getPsamType() {
		return psamType;
	}

	public void setPsamType(String psamType) {
		this.psamType = psamType;
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

	public Long getTerminalDeviceId() {
		return terminalDeviceId;
	}

	public void setTerminalDeviceId(Long terminalDeviceId) {
		this.terminalDeviceId = terminalDeviceId;
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
		return "Psam{" +
			"id=" + id +
			", psamCardNumber=" + psamCardNumber +
			", psamType=" + psamType +
			", putInAt=" + putInAt +
			", putOutAt=" + putOutAt +
			", startusingAt=" + startusingAt +
			", manageOrgId=" + manageOrgId +
			", manager=" + manager +
			", terminalDeviceId=" + terminalDeviceId +
			", remarks=" + remarks +
			"}";
	}
}
