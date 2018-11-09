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
 * @since 2017-11-03
 */
@TableName("yxy_buyer_information")
public class BuyerInformation extends SuperModel<BuyerInformation> {

    private static final long serialVersionUID = 1L;

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
	 * 邮箱
	 */
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
     * 开户行
     */
	private String bank;
    /**
     * 银行账户
     */
	@TableField("bank_account")
	private String bankAccount;
	@TableField("used_time")
	private Date usedTime;
    /**
     * 停用时间
     */
	@TableField("un_used_time")
	private Date unUsedTime;
	@TableField("customer_id")
	private Long customerId;
    /**
     * 0未启用，1启用，2停用
     */
	@TableField("in_use")
	private InUseEnum inUse;
	@TableField("created_at")
	private Date createdAt;
	@TableField("updated_at")
	private Date updatedAt;
	private Long id;

	private transient Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public InUseEnum getInUse() {
		return inUse;
	}

	public void setInUse(InUseEnum inUse) {
		this.inUse = inUse;
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
		return "BuyerInformation{" +
			"invoiceHeader=" + invoiceHeader +
			", taxpayerIdentificationNumber=" + taxpayerIdentificationNumber +
			", location=" + location +
			", mobile=" + mobile +
			", bank=" + bank +
			", bankAccount=" + bankAccount +
			", usedTime=" + usedTime +
			", unUsedTime=" + unUsedTime +
			", customerId=" + customerId +
			", inUse=" + inUse +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			", id=" + id +
			"}";
	}
}
