package com.yxytech.parkingcloud.core.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yxytech.parkingcloud.core.utils.SuperModel;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangyiqing
 * @since 2017-11-07
 */
@TableName("yxy_order_invoice")
public class OrderInvoice extends SuperModel<OrderInvoice> {

    private static final long serialVersionUID = 1L;

	@TableField("order_id")
	private Long orderId;

	@TableField("org_id")
	private Long organizationId;

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	@TableField("invoice_id")
	private Long invoiceId;
    /**
     * 可开金额
     */
	@TableField("invoice_amout")
	private Double invoiceAmout;
    /**
     * 是否开具了发票 0 未开 1开了
     */
	@TableField("whether_invoice")
	private Integer whetherInvoice;
    /**
     * 是否合开了发票 0 未合开 1合开
     */
	@TableField("whether_together")
	private Integer whetherTogether;
	private Long id;


	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Double getInvoiceAmout() {
		return invoiceAmout;
	}

	public void setInvoiceAmout(Double invoiceAmout) {
		this.invoiceAmout = invoiceAmout;
	}

	public Integer getWhetherInvoice() {
		return whetherInvoice;
	}

	public void setWhetherInvoice(Integer whetherInvoice) {
		this.whetherInvoice = whetherInvoice;
	}

	public Integer getWhetherTogether() {
		return whetherTogether;
	}

	public void setWhetherTogether(Integer whetherTogether) {
		this.whetherTogether = whetherTogether;
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
		return "OrderInvoice{" +
			"orderId=" + orderId +
			", invoiceId=" + invoiceId +
			", invoiceAmout=" + invoiceAmout +
			", whetherInvoice=" + whetherInvoice +
			", whetherTogether=" + whetherTogether +
			", id=" + id +
			"}";
	}
}
