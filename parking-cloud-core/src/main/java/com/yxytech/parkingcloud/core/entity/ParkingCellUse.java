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
 * @since 2017-10-19
 */
@TableName("yxy_parking_cell_use")
public class ParkingCellUse extends SuperModel<ParkingCellUse> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("parking_id")
	private Long parkingId;
	@TableField("using_number")
	private Integer usingNumber;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParkingId() {
		return parkingId;
	}

	public void setParkingId(Long parkingId) {
		this.parkingId = parkingId;
	}

	public Integer getUsingNumber() {
		return usingNumber;
	}

	public void setUsingNumber(Integer usingNumber) {
		this.usingNumber = usingNumber;
	}


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ParkingCellUse{" +
			"id=" + id +
			", parkingId=" + parkingId +
			", usingNumber=" + usingNumber +
			"}";
	}
}
