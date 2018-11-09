package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.enums.OrderVoucherType;
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
@TableName("yxy_order_voucher")
public class OrderVoucher extends SuperModel<OrderVoucher> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("order_id")
	private Long orderId;
	private OrderVoucherType type;
	private String voucher;


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

	public OrderVoucherType getType() {
		return type;
	}

	public void setType(OrderVoucherType type) {
		this.type = type;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "OrderVoucher{" +
			"id=" + id +
			", orderId=" + orderId +
			", type=" + type +
			", voucher=" + voucher +
			"}";
	}
}
