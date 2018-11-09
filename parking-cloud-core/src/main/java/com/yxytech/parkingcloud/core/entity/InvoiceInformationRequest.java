package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yxytech.parkingcloud.core.enums.BillStatusEnum;
import com.yxytech.parkingcloud.core.enums.BillTypeEnum;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangyiqing
 * @since 2017-11-02
 */
@TableName("yxy_invoice_information_request")
public class InvoiceInformationRequest extends SuperModel<InvoiceInformationRequest> {

    private static final long serialVersionUID = 1L;

    /**
     * 发票流水号
     */
	@TableField("invoice_flow_number")
	private String invoiceFlowNumber;
    /**
     * 开票点编码
     */
    @TableField("org_id")
	private Long organizationId;

    @TableField("original_invoice_id")
	private Long originalInvoiceId;

	public Long getOriginalInvoiceId() {
		return originalInvoiceId;
	}

	public void setOriginalInvoiceId(Long originalInvoiceId) {
		this.originalInvoiceId = originalInvoiceId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@TableField("bill_point_code")
	private String billPointCode;
    /**
     * 发票类型代码
     */
	@TableField("invoice_type_code")
	private String invoiceTypeCode;
    /**
     * 申请途经
     */
	@TableField("apply_way")
	private String applyWay;
    /**
     * 购方id
     */
	@TableField("buy_id")
	private Long buyId;
    /**
     * 销方Id
     */
    @TableField("customer_id")
    private Long customerId;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@TableField("sale_id")
	private Long saleId;

	public String getCancelImg() {
		return cancelImg;
	}

	public void setCancelImg(String cancelImg) {
		this.cancelImg = cancelImg;
	}

	@TableField("cancel_img")

	private String cancelImg;

    /**
     * 申请电话
     */
	private String mobile;
    /**
     * 申请邮箱
     */
	private String email;
    /**
     * 收款人名称
     */
	private String payee;
    /**
     * 开票人名称
     */
	private String ssuer;
    /**
     * 审核人
     */
	private String manager;
    /**
     * 开票类型 0:开票 1：红冲
     */
	@TableField("bill_type")
	private BillTypeEnum billType;
    /**
     * 原发票代码
     */
	@TableField("original_invoice_code")
	private String originalInvoiceCode;
    /**
     * 原发票号码
     */
	@TableField("original_invoice_number")
	private String originalInvoiceNumber;
    /**
     * 备注
     */
	private String remarks;
    /**
     * 开票渠道
     */
	@TableField("bill_channel")
	private String billChannel;
    /**
     * 申请开票时间
     */
	@TableField("apply_bill_date")
	private Date applyBillDate;
    @TableId("invoice_id")
	private Long invoiceId;
	@TableField("total_amout")
	private Double totalAmout;
	@TableField("total_tax_amout")
	private Double totalTaxAmout;
	@TableField("price_tax_total")
	private Double priceTaxTotal;
    /**
     * 0：开票中，1：已完成，2：开票失败
     */
    @TableField("request_data")
    private String requestData;

    @TableField("request_xml")
	private String requestXml;

	public String getRequestXml() {
		return requestXml;
	}

	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	@TableField("bill_status")
	private BillStatusEnum billStatus;
	@TableField(value = "created_at", fill = FieldFill.INSERT)
	private Date createdAt;
	@TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
	private Date updatedAt;

	private transient List<OrderInfo> orderInfo;

	private transient SalesInformation salesInformation;

	private transient InvoiceDetail invoiceDetail;

	private transient InvoiceInformationResult invoiceInformationResult;

	private transient Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
//private transient BuyerInformation buyerInformation;

//	//public BuyerInformation getBuyerInformation() {
//		return buyerInformation;
//	}

//	public void setBuyerInformation(BuyerInformation buyerInformation) {
//		this.buyerInformation = buyerInformation;
//	}

	public InvoiceInformationResult getInvoiceInformationResult() {
		return invoiceInformationResult;
	}

	public void setInvoiceInformationResult(InvoiceInformationResult invoiceInformationResult) {
		this.invoiceInformationResult = invoiceInformationResult;
	}



	public InvoiceDetail getInvoiceDetail() {
		return invoiceDetail;
	}

	public void setInvoiceDetail(InvoiceDetail invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}

	public SalesInformation getSalesInformation() {
		return salesInformation;
	}

	public void setSalesInformation(SalesInformation salesInformation) {
		this.salesInformation = salesInformation;
	}

	public List<OrderInfo> getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(List<OrderInfo> orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getInvoiceFlowNumber() {
		return invoiceFlowNumber;
	}

	public void setInvoiceFlowNumber(String invoiceFlowNumber) {
		this.invoiceFlowNumber = invoiceFlowNumber;
	}

	public String getBillPointCode() {
		return billPointCode;
	}

	public void setBillPointCode(String billPointCode) {
		this.billPointCode = billPointCode;
	}

	public String getInvoiceTypeCode() {
		return invoiceTypeCode;
	}

	public void setInvoiceTypeCode(String invoiceTypeCode) {
		this.invoiceTypeCode = invoiceTypeCode;
	}

	public String getApplyWay() {
		return applyWay;
	}

	public void setApplyWay(String applyWay) {
		this.applyWay = applyWay;
	}

	public Long getBuyId() {
		return buyId;
	}

	public void setBuyId(Long buyId) {
		this.buyId = buyId;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getSsuer() {
		return ssuer;
	}

	public void setSsuer(String ssuer) {
		this.ssuer = ssuer;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public BillTypeEnum getBillType() {
		return billType;
	}

	public void setBillType(BillTypeEnum billType) {
		this.billType = billType;
	}

	public String getOriginalInvoiceCode() {
		return originalInvoiceCode;
	}

	public void setOriginalInvoiceCode(String originalInvoiceCode) {
		this.originalInvoiceCode = originalInvoiceCode;
	}

	public String getOriginalInvoiceNumber() {
		return originalInvoiceNumber;
	}

	public void setOriginalInvoiceNumber(String originalInvoiceNumber) {
		this.originalInvoiceNumber = originalInvoiceNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBillChannel() {
		return billChannel;
	}

	public void setBillChannel(String billChannel) {
		this.billChannel = billChannel;
	}

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyBillDate() {
		return applyBillDate;
	}

	public void setApplyBillDate(Date applyBillDate) {
		this.applyBillDate = applyBillDate;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Double getTotalAmout() {
		return totalAmout;
	}

	public void setTotalAmout(Double totalAmout) {
		this.totalAmout = totalAmout;
	}

	public Double getTotalTaxAmout() {
		return totalTaxAmout;
	}

	public void setTotalTaxAmout(Double totalTaxAmout) {
		this.totalTaxAmout = totalTaxAmout;
	}

	public Double getPriceTaxTotal() {
		return priceTaxTotal;
	}

	public void setPriceTaxTotal(Double priceTaxTotal) {
		this.priceTaxTotal = priceTaxTotal;
	}

	public BillStatusEnum getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(BillStatusEnum billStatus) {
		this.billStatus = billStatus;
	}

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	protected Serializable pkVal() {
		return this.invoiceId;
	}

	@Override
	public String toString() {
		return "InvoiceInformationRequest{" +
			"invoiceFlowNumber=" + invoiceFlowNumber +
			", billPointCode=" + billPointCode +
			", invoiceTypeCode=" + invoiceTypeCode +
			", applyWay=" + applyWay +
			", buyId=" + buyId +
			", saleId=" + saleId +
			", mobile=" + mobile +
			", email=" + email +
			", payee=" + payee +
			", ssuer=" + ssuer +
			", manager=" + manager +
			", billType=" + billType +
			", originalInvoiceCode=" + originalInvoiceCode +
			", originalInvoiceNumber=" + originalInvoiceNumber +
			", remarks=" + remarks +
			", billChannel=" + billChannel +
			", applyBillDate=" + applyBillDate +
			", invoiceId=" + invoiceId +
			", totalAmout=" + totalAmout +
			", totalTaxAmout=" + totalTaxAmout +
			", priceTaxTotal=" + priceTaxTotal +
			", billStatus=" + billStatus +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			"}";
	}
}
