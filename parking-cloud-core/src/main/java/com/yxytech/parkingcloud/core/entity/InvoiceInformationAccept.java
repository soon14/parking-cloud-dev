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
@TableName("yxy_invoice_information_accept")
public class InvoiceInformationAccept extends SuperModel<InvoiceInformationAccept> {

    private static final long serialVersionUID = 1L;

    /**
     * 停车人id
     */
	@TableField("customer_id")
	private Integer customerId;
    /**
     * 电话
     */
    @TableField("org_id")
	private Long organizationId;

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	private String mobile;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 记录时间
     */
	@TableField("record_date")
	private Date recordDate;
    /**
     * 创建时间
     */
	@TableField("created_at")
	private Date createdAt;
    /**
     * 修改时间
     */
	@TableField("updated_at")
	private Date updatedAt;
	private Long id;


	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
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
		return "InvoiceInformationAccept{" +
			"customerId=" + customerId +
			", mobile=" + mobile +
			", email=" + email +
			", recordDate=" + recordDate +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			", id=" + id +
			"}";
	}
}
