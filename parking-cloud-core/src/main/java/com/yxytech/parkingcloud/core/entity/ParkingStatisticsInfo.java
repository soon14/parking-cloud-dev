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
 * @since 2017-11-10
 */
@TableName("yxy_parking_statistics_info")
public class ParkingStatisticsInfo extends SuperModel<ParkingStatisticsInfo> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("cell_used_time_length")
	private Long cellUsedTimeLength;
	@TableField("cell_utilization")
	private Double cellUtilization;
	@TableField("cell_reverse_rate")
	private Double cellReverseRate;
	@TableField("cell_usage")
	private Double cellUsage;
	@TableField("cell_count")
	private Long cellCount;
	@TableField("parking_id")
	private Long parkingId;
	@TableField("parking_name")
	private String parkingName;
	@TableField("gross_income")
	private Double grossIncome;
	@TableField("wechat_income")
	private Double wechatIncome;
	@TableField("alipay_income")
	private Double alipayIncome;
	@TableField("union_pay_income")
	private Double unionPayIncome;
	@TableField("discount_income")
	private Double discountIncome;
	@TableField("other_income")
	private Double otherIncome;
	private Date datetime;
	private Integer hour;
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

	public Long getCellUsedTimeLength() {
		return cellUsedTimeLength;
	}

	public void setCellUsedTimeLength(Long cellUsedTimeLength) {
		this.cellUsedTimeLength = cellUsedTimeLength;
	}

	public Double getCellUtilization() {
		return cellUtilization;
	}

	public void setCellUtilization(Double cellUtilization) {
		this.cellUtilization = cellUtilization;
	}

	public Double getCellReverseRate() {
		return cellReverseRate;
	}

	public void setCellReverseRate(Double cellReverseRate) {
		this.cellReverseRate = cellReverseRate;
	}

	public Double getCellUsage() {
		return cellUsage;
	}

	public void setCellUsage(Double cellUsage) {
		this.cellUsage = cellUsage;
	}

	public Long getCellCount() {
		return cellCount;
	}

	public void setCellCount(Long cellCount) {
		this.cellCount = cellCount;
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

	public Double getGrossIncome() {
		return grossIncome;
	}

	public void setGrossIncome(Double grossIncome) {
		this.grossIncome = grossIncome;
	}

	public Double getWechatIncome() {
		return wechatIncome;
	}

	public void setWechatIncome(Double wechatIncome) {
		this.wechatIncome = wechatIncome;
	}

	public Double getAlipayIncome() {
		return alipayIncome;
	}

	public void setAlipayIncome(Double alipayIncome) {
		this.alipayIncome = alipayIncome;
	}

	public Double getUnionPayIncome() {
		return unionPayIncome;
	}

	public void setUnionPayIncome(Double unionPayIncome) {
		this.unionPayIncome = unionPayIncome;
	}

	public Double getDiscountIncome() {
		return discountIncome;
	}

	public void setDiscountIncome(Double discountIncome) {
		this.discountIncome = discountIncome;
	}

	public Double getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(Double otherIncome) {
		this.otherIncome = otherIncome;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ParkingStatisticsInfo{" +
			"id=" + id +
			", cellUsedTimeLength=" + cellUsedTimeLength +
			", cellUtilization=" + cellUtilization +
			", cellReverseRate=" + cellReverseRate +
			", cellUsage=" + cellUsage +
			", cellCount=" + cellCount +
			", parkingId=" + parkingId +
			", parkingName=" + parkingName +
			", grossIncome=" + grossIncome +
			", wechatIncome=" + wechatIncome +
			", alipayIncome=" + alipayIncome +
			", unionPayIncome=" + unionPayIncome +
			", discountIncome=" + discountIncome +
			", otherIncome=" + otherIncome +
			", datetime=" + datetime +
			", hour=" + hour +
			"}";
	}
}
