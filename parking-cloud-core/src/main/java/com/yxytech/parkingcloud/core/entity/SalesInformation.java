package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.enums.InUseEnum;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangyiqing
 * @since 2017-10-31
 */
@TableName("yxy_sales_information")
public class SalesInformation extends SuperModel<SalesInformation> {

    private static final long serialVersionUID = 1L;

	private Long id;
    /**
     * 发票抬头
     */
	@TableField("invoice_header")
	private String invoiceHeader;
    /**
     * 纳税人识别号
     */
	@TableField("taxpayer_identification_number")
	private String taxpayerIdentificationNumber;
    /**
     * 地址
     */
	private String location;
    /**
     * 电话
     */
	private String mobile;
    /**
     * 开户行
     */
	private String bank;
    /**
     * 银行账户
     */
	@TableField("bank_account")
	private String bankAccount;
    /**
     * 是否启用
     */
	@TableField("in_use")
	private InUseEnum inUse;
    /**
     * 启用时间
     */
	@TableField("used_time")
	private Date usedTime;
    /**
     * 停用时间
     */
	@TableField("un_used_time")
	private Date unUsedTime;
	/**
	 * 开票服务代码
	 */
	@TableField("bill_sevice_code")
	private String billSeviceCode;
	/**
	 * 查询服务代码
	 */
	@TableField("search_service_code")
	private String searchServiceCode;
	/**
	 * 开票渠道
	 */
	@TableField("bill_channel")
	private Integer billChannel;
	/**
	 * 开票点编码
	 */
	@TableField("bill_code")
	private String billCode;
	/**
	 * 收款人
	 */
	@TableField("payee")
	private String payee;
	/**
	 * 开票人
	 */
	@TableField("issuer")
	private String issuer;
	/**
	 * 审核人
	 */
	@TableField("manager")
	private String manager;
	/**
	 * 税率
	 */
	@TableField("tax_rate")
	private Double taxRate;
	/**
	 * 增值税特殊管理
	 */
	@TableField("vat_tax_special_manage")
	private String vatTaxSpecialManage;

	public String getBillSeviceCode() {
		return billSeviceCode;
	}

	public void setBillSeviceCode(String billSeviceCode) {
		this.billSeviceCode = billSeviceCode;
	}

	public String getSearchServiceCode() {
		return searchServiceCode;
	}

	public void setSearchServiceCode(String searchServiceCode) {
		this.searchServiceCode = searchServiceCode;
	}

	public Integer getBillChannel() {
		return billChannel;
	}

	public void setBillChannel(Integer billChannel) {
		this.billChannel = billChannel;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public String getVatTaxSpecialManage() {
		return vatTaxSpecialManage;
	}

	public void setVatTaxSpecialManage(String vatTaxSpecialManage) {
		this.vatTaxSpecialManage = vatTaxSpecialManage;
	}

	@TableField("org_id")
	private Integer organzationId;
	@TableField("created_at")
	private Date createdAt;
	@TableField("updated_at")
	private Date updatedAt;

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	private transient Organization organization;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInvoiceHeader() {
		return invoiceHeader;
	}

	public void setInvoiceHeader(String invoiceHeader) {
		this.invoiceHeader = invoiceHeader;
	}

	public String getTaxpayerIdentificationNumber() {
		return taxpayerIdentificationNumber;
	}

	public void setTaxpayerIdentificationNumber(String taxpayerIdentificationNumber) {
		this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public InUseEnum getInUse() {
		return inUse;
	}

	public void setInUse(InUseEnum inUse) {
		this.inUse = inUse;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public Date getUnUsedTime() {
		return unUsedTime;
	}

	public void setUnUsedTime(Date unUsedTime) {
		this.unUsedTime = unUsedTime;
	}

	public Integer getOrganzationId() {
		return organzationId;
	}

	public void setOrganzationId(Integer organzationId) {
		this.organzationId = organzationId;
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
		return "SalesInformation{" +
			"id=" + id +
			", invoiceHeader=" + invoiceHeader +
			", taxpayerIdentificationNumber=" + taxpayerIdentificationNumber +
			", location=" + location +
			", mobile=" + mobile +
			", bank=" + bank +
			", bankAccount=" + bankAccount +
			", inUse=" + inUse +
			", usedTime=" + usedTime +
			", unUsedTime=" + unUsedTime +
			", organzationId=" + organzationId +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			"}";
	}
}
