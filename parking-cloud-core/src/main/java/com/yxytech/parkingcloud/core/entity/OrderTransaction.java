package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.yxytech.parkingcloud.core.enums.ColorsEnum;
import com.yxytech.parkingcloud.core.enums.OrderTransactionCreateWayEnum;
import com.yxytech.parkingcloud.core.enums.OrderTransactionEnum;
import com.yxytech.parkingcloud.core.enums.TransactionPayWay;
import com.yxytech.parkingcloud.core.utils.OrderInfoUtil;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwd
 * @since 2017-11-03
 */
@TableName("yxy_order_transaction")
public class OrderTransaction extends SuperModel<OrderTransaction> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
	private String uuid;
	@TableField("order_number")
	private String orderNumber;
	@TableField("user_id")
	private Long userId;
	@TableField("mch_id")
	private String mchId;
	@TableField("parking_id")
	private Long parkingId;
	@TableField("plate_number")
	private String plateNumber;
	@TableField("plate_color")
	private ColorsEnum plateColor;
	@TableField("pay_way")
	private TransactionPayWay payWay;
	@TableField("pay_method")
	private String payMethod;
	private Double amount;
	@TableField(value = "created_at")
	private Date createdAt;
	@TableField("created_way")
	private OrderTransactionCreateWayEnum createdWay;
	@TableField("transaction_id")
	private String transactionId;
	@TableField("finished_at")
	private Date finishedAt;
	@TableField("transaction_way")
	private Integer transactionWay;
	@TableField("transaction_detail")
	private String transactionDetail;
	private Long orderId;
	private OrderTransactionEnum status;
	private String remark;

	public OrderTransactionEnum getStatus() {
		return status;
	}

	public void setStatus(OrderTransactionEnum status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getParkingId() {
		return parkingId;
	}

	public void setParkingId(Long parkingId) {
		this.parkingId = parkingId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
		return OrderInfoUtil.formatAmount(amount);
	}

	public void setAmount(Double amount) {
		this.amount = OrderInfoUtil.formatAmount(amount);
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public OrderTransactionCreateWayEnum getCreatedWay() {
		return createdWay;
	}

	public void setCreatedWay(OrderTransactionCreateWayEnum createdWay) {
		this.createdWay = createdWay;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getFinishedAt() {
		return finishedAt;
	}

	public void setFinishedAt(Date finishedAt) {
		this.finishedAt = finishedAt;
	}

	public Integer getTransactionWay() {
		return transactionWay;
	}

	public void setTransactionWay(Integer transactionWay) {
		this.transactionWay = transactionWay;
	}

	public String getTransactionDetail() {
		return transactionDetail;
	}

	public void setTransactionDetail(String transactionDetail) {
		this.transactionDetail = transactionDetail;
	}

	@Override
	public String toString() {
		return "OrderTransaction{" +
			"id=" + uuid +
			", orderNumber=" + orderNumber +
			", userId=" + userId +
			", plateNumber=" + plateNumber +
			", plateColor=" + plateColor +
			", payWay=" + payWay +
			", payMethod=" + payMethod +
			", amount=" + amount +
			", createdAt=" + createdAt +
			", createdWay=" + createdWay +
			", transactionId=" + transactionId +
			", finishedAt=" + finishedAt +
			", transactionWay=" + transactionWay +
			", transactionDetail=" + transactionDetail +
			"}";
	}
}
