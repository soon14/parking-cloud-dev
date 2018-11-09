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
 * @author zhangyiqing
 * @since 2017-11-02
 */
@TableName("yxy_invoice_information_result")
public class InvoiceInformationResult extends SuperModel<InvoiceInformationResult> {

    private static final long serialVersionUID = 1L;

	@TableField("invoice_id")
	private Long invoiceId;

	@TableField("org_id")
	private Long organizationId;

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	/**
     * 发票流水号
     */
	@TableField("invoice_flow_number")
	private String invoiceFlowNumber;
    /**
     * 开票时间
     */
	@TableField("bill_date")
	private Date billDate;
    /**
     * 发票代码
     */
	@TableField("invocie_code")
	private String invocieCode;
    /**
     * 发票号码
     */
	@TableField("invoice_number")
	private Integer invoiceNumber;
    /**
     * 平台电子发票下载地址
     */
	@TableField("platform_invoice_url")
	private String platformInvoiceUrl;
    /**
     * 国税电子发票下载地址
     */
	@TableField("tax_invoice_url")
	private String taxInvoiceUrl;
    /**
     * 云平台电子链接下载地址
     */
	@TableField("cloud_platform_invoice_url")
	private String cloudPlatformInvoiceUrl;
	@TableField("cloud_platform_invoice_image")
	private String cloudPlatformInvoiceImage;

	public String getCloudPlatformInvoiceImage() {
		return cloudPlatformInvoiceImage;
	}

	public void setCloudPlatformInvoiceImage(String cloudPlatformInvoiceImage) {
		this.cloudPlatformInvoiceImage = cloudPlatformInvoiceImage;
	}

	@TableField("created_at")
	private Date createdAt;
	@TableField("updated_at")
	private Date updatedAt;
	private Long id;


	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceFlowNumber() {
		return invoiceFlowNumber;
	}

	public void setInvoiceFlowNumber(String invoiceFlowNumber) {
		this.invoiceFlowNumber = invoiceFlowNumber;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getInvocieCode() {
		return invocieCode;
	}

	public void setInvocieCode(String invocieCode) {
		this.invocieCode = invocieCode;
	}

	public Integer getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Integer invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getPlatformInvoiceUrl() {
		return platformInvoiceUrl;
	}

	public void setPlatformInvoiceUrl(String platformInvoiceUrl) {
		this.platformInvoiceUrl = platformInvoiceUrl;
	}

	public String getTaxInvoiceUrl() {
		return taxInvoiceUrl;
	}

	public void setTaxInvoiceUrl(String taxInvoiceUrl) {
		this.taxInvoiceUrl = taxInvoiceUrl;
	}

	public String getCloudPlatformInvoiceUrl() {
		return cloudPlatformInvoiceUrl;
	}

	public void setCloudPlatformInvoiceUrl(String cloudPlatformInvoiceUrl) {
		this.cloudPlatformInvoiceUrl = cloudPlatformInvoiceUrl;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "InvoiceInformationResult{" +
			"invoiceId=" + invoiceId +
			", invoiceFlowNumber=" + invoiceFlowNumber +
			", billDate=" + billDate +
			", invocieCode=" + invocieCode +
			", invoiceNumber=" + invoiceNumber +
			", platformInvoiceUrl=" + platformInvoiceUrl +
			", taxInvoiceUrl=" + taxInvoiceUrl +
			", cloudPlatformInvoiceUrl=" + cloudPlatformInvoiceUrl +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			", id=" + id +
			"}";
	}
}
