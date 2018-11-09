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
 * @author cj
 * @since 2017-11-27
 */
@TableName("yxy_fee_schema_parking")
public class FeeSchemaParking extends SuperModel<FeeSchemaParking> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("fee_schema_id")
	private Long feeSchemaId;
	@TableField("parking_id")
	private Long parkingId;

	@TableField("org_id")
	private Long organizationId;

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public FeeSchemaParking(){

	}

	public FeeSchemaParking(Long parkingId,Long feeSchemaId){
    	this.parkingId = parkingId;
    	this.feeSchemaId = feeSchemaId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFeeSchemaId() {
		return feeSchemaId;
	}

	public void setFeeSchemaId(Long feeSchemaId) {
		this.feeSchemaId = feeSchemaId;
	}

	public Long getParkingId() {
		return parkingId;
	}

	public void setParkingId(Long parkingId) {
		this.parkingId = parkingId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "FeeSchemaParking{" +
			"id=" + id +
			", feeSchemaId=" + feeSchemaId +
			", parkingId=" + parkingId +
			"}";
	}
}
