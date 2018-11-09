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
 * @author liwd
 * @since 2017-11-15
 */
@TableName("yxy_parking_time_length_statistics")
public class ParkingTimeLengthStatistics extends SuperModel<ParkingTimeLengthStatistics> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("parking_id")
	private Long parkingId;
	@TableField("parking_name")
	private String parkingName;
	private Integer lt15;
	private Integer gt15lt30;
	private Integer gt30lt45;
	private Integer gt45lt60;
	private Integer gt60lt90;
	private Integer gt90lt120;
	private Integer gt120lt150;
	private Integer gt150lt180;
	private Integer gt180lt240;
	private Integer gt240;
	private Date datetime;
	@TableField("in_times")
	private Integer inTimes;
	@TableField("out_times")
	private Integer outTimes;
	private Integer type;
	private Integer month;

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

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

	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	public Integer getLt15() {
		return lt15;
	}

	public void setLt15(Integer lt15) {
		this.lt15 = lt15;
	}

	public Integer getGt15lt30() {
		return gt15lt30;
	}

	public void setGt15lt30(Integer gt15lt30) {
		this.gt15lt30 = gt15lt30;
	}

	public Integer getGt30lt45() {
		return gt30lt45;
	}

	public void setGt30lt45(Integer gt30lt45) {
		this.gt30lt45 = gt30lt45;
	}

	public Integer getGt45lt60() {
		return gt45lt60;
	}

	public void setGt45lt60(Integer gt45lt60) {
		this.gt45lt60 = gt45lt60;
	}

	public Integer getGt60lt90() {
		return gt60lt90;
	}

	public void setGt60lt90(Integer gt60lt90) {
		this.gt60lt90 = gt60lt90;
	}

	public Integer getGt90lt120() {
		return gt90lt120;
	}

	public void setGt90lt120(Integer gt90lt120) {
		this.gt90lt120 = gt90lt120;
	}

	public Integer getGt120lt150() {
		return gt120lt150;
	}

	public void setGt120lt150(Integer gt120lt150) {
		this.gt120lt150 = gt120lt150;
	}

	public Integer getGt150lt180() {
		return gt150lt180;
	}

	public void setGt150lt180(Integer gt150lt180) {
		this.gt150lt180 = gt150lt180;
	}

	public Integer getGt180lt240() {
		return gt180lt240;
	}

	public void setGt180lt240(Integer gt180lt240) {
		this.gt180lt240 = gt180lt240;
	}

	public Integer getGt240() {
		return gt240;
	}

	public void setGt240(Integer gt240) {
		this.gt240 = gt240;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Integer getInTimes() {
		return inTimes;
	}

	public void setInTimes(Integer inTimes) {
		this.inTimes = inTimes;
	}

	public Integer getOutTimes() {
		return outTimes;
	}

	public void setOutTimes(Integer outTimes) {
		this.outTimes = outTimes;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ParkingTimeLengthStatistics{" +
			"id=" + id +
			", parkingId=" + parkingId +
			", parkingName=" + parkingName +
			", lt15=" + lt15 +
			", gt15lt30=" + gt15lt30 +
			", gt30lt45=" + gt30lt45 +
			", gt45lt60=" + gt45lt60 +
			", gt60lt90=" + gt60lt90 +
			", gt90lt120=" + gt90lt120 +
			", gt120lt150=" + gt120lt150 +
			", gt150lt180=" + gt150lt180 +
			", gt180lt240=" + gt180lt240 +
			", gt240=" + gt240 +
			", datetime=" + datetime +
			", inTimes=" + inTimes +
			", outTimes=" + outTimes +
			", type=" + type +
			"}";
	}
}
