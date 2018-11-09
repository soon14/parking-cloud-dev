package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.yxytech.parkingcloud.core.enums.ColorsEnum;
import com.yxytech.parkingcloud.core.enums.TransactionPayWay;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwd
 * @since 2017-11-06
 */
@TableName("yxy_transaction_success")
public class TransactionSuccess extends SuperModel<TransactionSuccess> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("user_id")
	private Long userId;
	@TableField("order_id")
	private Long orderId;
	@TableField("plate_number")
	private String plateNumber;
	@TableField("plate_color")
	private ColorsEnum plateColor;
	@TableField("pay_way")
	private TransactionPayWay payWay;
	@TableField("pay_method")
	private String payMethod;
	private Double amount;
	@TableField(value = "created_at", fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField("updated_at")
	private Date updatedAt;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public ColorsEnum getPlateColor() {
		return plateColor;
	}

	public void setPlateColor(ColorsEnum plateColor) {
		this.plateColor = plateColor;
	}

	public TransactionPayWay getPayWay() {
		return payWay;
	}

	public void setPayWay(TransactionPayWay payWay) {
		this.payWay = payWay;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
		return "TransactionSuccess{" +
			"id=" + id +
			", orderId=" + orderId +
			", plateNumber=" + plateNumber +
			", plateColor=" + plateColor +
			", payWay=" + payWay +
			", payMethod=" + payMethod +
			", amount=" + amount +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			"}";
	}
}
