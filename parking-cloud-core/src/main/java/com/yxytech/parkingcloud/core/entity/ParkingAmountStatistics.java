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
 * @since 2017-11-16
 */
@TableName("yxy_parking_amount_statistics")
public class ParkingAmountStatistics extends SuperModel<ParkingAmountStatistics> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("parking_id")
	private Long parkingId;
	@TableField("parking_name")
	private String parkingName;
	@TableField("receivable_amount")
	private Double receivableAmount;
	@TableField("paid_amount")
	private Double paidAmount;
	@TableField("real_paid_aount")
	private Double realPaidAount;
	@TableField("pre_paid_amount")
	private Double prePaidAmount;
	@TableField("supplementary_amout")
	private Double supplementaryAmout;
	@TableField("pre_paid_refunds_amount")
	private Double prePaidRefundsAmount;
	@TableField("unpaid_amount")
	private Double unpaidAmount;
	@TableField("alipay_amount")
	private Double alipayAmount;
	@TableField("wechat_amount")
	private Double wechatAmount;
	@TableField("discount_amount")
	private Double discountAmount;
	@TableField("cash_amount")
	private Double cashAmount;
	private Date datetime;
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

	public Double getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(Double receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Double getRealPaidAount() {
		return realPaidAount;
	}

	public void setRealPaidAount(Double realPaidAount) {
		this.realPaidAount = realPaidAount;
	}

	public Double getPrePaidAmount() {
		return prePaidAmount;
	}

	public void setPrePaidAmount(Double prePaidAmount) {
		this.prePaidAmount = prePaidAmount;
	}

	public Double getSupplementaryAmout() {
		return supplementaryAmout;
	}

	public void setSupplementaryAmout(Double supplementaryAmout) {
		this.supplementaryAmout = supplementaryAmout;
	}

	public Double getPrePaidRefundsAmount() {
		return prePaidRefundsAmount;
	}

	public void setPrePaidRefundsAmount(Double prePaidRefundsAmount) {
		this.prePaidRefundsAmount = prePaidRefundsAmount;
	}

	public Double getUnpaidAmount() {
		return unpaidAmount;
	}

	public void setUnpaidAmount(Double unpaidAmount) {
		this.unpaidAmount = unpaidAmount;
	}

	public Double getAlipayAmount() {
		return alipayAmount;
	}

	public void setAlipayAmount(Double alipayAmount) {
		this.alipayAmount = alipayAmount;
	}

	public Double getWechatAmount() {
		return wechatAmount;
	}

	public void setWechatAmount(Double wechatAmount) {
		this.wechatAmount = wechatAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getCashAmount() {
		return cashAmount;
	}

	public void setCashAmount(Double cashAmount) {
		this.cashAmount = cashAmount;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
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
		return "ParkingAmountStatistics{" +
			"id=" + id +
			", parkingId=" + parkingId +
			", parkingName=" + parkingName +
			", receivableAmount=" + receivableAmount +
			", paidAmount=" + paidAmount +
			", realPaidAount=" + realPaidAount +
			", prePaidAmount=" + prePaidAmount +
			", supplementaryAmout=" + supplementaryAmout +
			", prePaidRefundsAmount=" + prePaidRefundsAmount +
			", unpaidAmount=" + unpaidAmount +
			", alipayAmount=" + alipayAmount +
			", wechatAmount=" + wechatAmount +
			", discountAmount=" + discountAmount +
			", cashAmount=" + cashAmount +
			", datetime=" + datetime +
			", type=" + type +
			"}";
	}
}
