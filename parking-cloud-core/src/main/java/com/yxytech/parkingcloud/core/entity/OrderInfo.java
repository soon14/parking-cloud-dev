package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxytech.parkingcloud.core.enums.*;
import com.yxytech.parkingcloud.core.utils.OrderInfoUtil;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwd
 * @since 2017-10-27
 */
@TableName("yxy_order_info")
public class OrderInfo extends SuperModel<OrderInfo> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("order_number")
	private String orderNumber;
	@TableField("user_id")
	private Long userId;
	@TableField("customer_car_id")
	private Long customerCarId;
	@TableField("plate_number")
	private String plateNumber;
	@TableField("plate_color")
	private ColorsEnum plateColor;
	@TableField("parking_id")
	private Long parkingId;
	@TableField("parking_name")
	private String parkingName;
	@TableField("org_id")
	private Long organizationId;
	@TableField("total_amount")
	private Double totalAmount;
	@TableField("receivable_amount")
	private Double receivableAmount;
	@TableField("free_amount")
	private Double freeAmount;
	@TableField("prepaid_amount")
	private Double prepaidAmount;
	@TableField("paid_amount")
	private Double paidAmount;
	@TableField("invoice_amount")
	private Double invoiceAmount;
	@TableField(value = "created_at", fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField("enter_at")
	private Date enterAt;
	@TableField("leave_at")
	private Date leaveAt;
	private Long duration;
	@TableField("requested_bill")
	private Boolean requestedBill;
	private OrderStatusEnum status;
	@TableField("car_status")
	private OrderCarStatusType carStatus;
	@TableField("from_type")
	private OrderFromTypeEnum fromType;
	@TableField("is_valid")
	private Boolean isValid;
	private String remark;
	@TableField(value = "updated_by", fill = FieldFill.UPDATE)
	private Long updatedBy;
	@TableField("in_device_sn")
	private String inDeviceSn;
	@TableField("out_device_sn")
	private String outDeviceSn;
	@TableField("last_payment_time")
	private Date lastPaymentTime;
	@TableField("car_type")
	private CarTypeEnum carType;
	private transient Date calculateTime;
	private transient OrderParkingInfo parkingInfo;
	private transient List<OrderVoucher> voucherList;
	private transient List<OrderTransaction> orderTransactionList;
	private transient InvoiceInformationRequest invoiceInformationRequest;

	private transient String organizationName;

	public Date getCalculateTime() {
		return calculateTime;
	}

	public void setCalculateTime(Date calculateTime) {
		this.calculateTime = calculateTime;
	}

	public Date getLastPaymentTime() {
		return lastPaymentTime;
	}

	public void setLastPaymentTime(Date lastPaymentTime) {
		this.lastPaymentTime = lastPaymentTime;
	}

	private transient Integer orderCount;

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public InvoiceInformationRequest getInvoiceInformationRequest() {
		return invoiceInformationRequest;
	}

	public void setInvoiceInformationRequest(InvoiceInformationRequest invoiceInformationRequest) {
		this.invoiceInformationRequest = invoiceInformationRequest;
	}

	public List<OrderTransaction> getOrderTransactionList() {
		return orderTransactionList;
	}

	public void setOrderTransactionList(List<OrderTransaction> orderTransactionList) {
		this.orderTransactionList = orderTransactionList;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public CarTypeEnum getCarType() {
		return carType;
	}

	public void setCarType(CarTypeEnum carType) {
		this.carType = carType;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getInDeviceSn() {
		return inDeviceSn;
	}

	public void setInDeviceSn(String inDeviceSn) {
		this.inDeviceSn = inDeviceSn;
	}

	public String getOutDeviceSn() {
		return outDeviceSn;
	}

	public void setOutDeviceSn(String outDeviceSn) {
		this.outDeviceSn = outDeviceSn;
	}

	public OrderParkingInfo getParkingInfo() {
		return parkingInfo;
	}

	public void setParkingInfo(OrderParkingInfo parkingInfo) {
		this.parkingInfo = parkingInfo;
	}

	public List<OrderVoucher> getVoucherList() {
		return voucherList;
	}

	public void setVoucherList(List<OrderVoucher> voucherList) {
		this.voucherList = voucherList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getCustomerCarId() {
		return customerCarId;
	}

	public void setCustomerCarId(Long customerCarId) {
		this.customerCarId = customerCarId;
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

	@JsonProperty("plateColorCode")
	public Integer getPlateColorCode() {
		return (Integer)plateColor.getValue();
	}

	public void setPlateColor(ColorsEnum plateColor) {
		this.plateColor = plateColor;
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

	public Double getTotalAmount() {
		return OrderInfoUtil.formatAmount(totalAmount);
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = OrderInfoUtil.formatAmount(totalAmount);
	}

	public Double getReceivableAmount() {
		return OrderInfoUtil.formatAmount(receivableAmount);
	}

	public void setReceivableAmount(Double receivableAmount) {
		this.receivableAmount = OrderInfoUtil.formatAmount(receivableAmount);
	}

	public Double getFreeAmount() {
		return OrderInfoUtil.formatAmount(freeAmount);
	}

	public void setFreeAmount(Double freeAmount) {
		this.freeAmount = OrderInfoUtil.formatAmount(freeAmount);
	}

	public Double getPrepaidAmount() {
		return OrderInfoUtil.formatAmount(prepaidAmount);
	}

	public void setPrepaidAmount(Double prepaidAmount) {
		this.prepaidAmount = OrderInfoUtil.formatAmount(prepaidAmount);
	}

	public Double getPaidAmount() {
		return OrderInfoUtil.formatAmount(paidAmount);
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = OrderInfoUtil.formatAmount(paidAmount);
	}

	public Double getInvoiceAmount() {
		return OrderInfoUtil.formatAmount(invoiceAmount);
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = OrderInfoUtil.formatAmount(invoiceAmount);
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getEnterAt() {
		return enterAt;
	}

	public void setEnterAt(Date enterAt) {
		this.enterAt = enterAt;
	}

	public Date getLeaveAt() {
		return leaveAt;
	}

	public void setLeaveAt(Date leaveAt) {
		this.leaveAt = leaveAt;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Boolean getRequestedBill() {
		return requestedBill;
	}

	public void setRequestedBill(Boolean requestedBill) {
		this.requestedBill = requestedBill;
	}

	public OrderStatusEnum getStatus() {
		return status;
	}

	@JsonProperty("statusCode")
	public Integer getStatusCode() {
		return (Integer) status.getValue();
	}

	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}

	public OrderCarStatusType getCarStatus() {
		return carStatus;
	}

	@JsonProperty("carStatusCode")
	public Integer getOrderCarStatusCode() {
		return (Integer) carStatus.getValue();
	}

	public void setCarStatus(OrderCarStatusType carStatus) {
		this.carStatus = carStatus;
	}

	public OrderFromTypeEnum getFromType() {
		return fromType;
	}

	public void setFromType(OrderFromTypeEnum fromType) {
		this.fromType = fromType;
	}

	public Boolean getValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "OrderInfo{" +
			"id=" + id +
			", orderNumber=" + orderNumber +
			", userId=" + userId +
			", customerCarId=" + customerCarId +
			", plateNumber=" + plateNumber +
			", plateColor=" + plateColor +
			", parkingId=" + parkingId +
			", parkingName=" + parkingName +
			", totalAmount=" + totalAmount +
			", receivableAmount=" + receivableAmount +
			", freeAmount=" + freeAmount +
			", prepaidAmount=" + prepaidAmount +
			", paidAmount=" + paidAmount +
			", invoiceAmount=" + invoiceAmount +
			", createdAt=" + createdAt +
			", enterAt=" + enterAt +
			", leaveAt=" + leaveAt +
			", duration=" + duration +
			", requestedBill=" + requestedBill +
			", status=" + status +
			", carStatus=" + carStatus +
			", fromType=" + fromType +
			", isValid=" + isValid +
			", remark=" + remark +
			"}";
	}
}
