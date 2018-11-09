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
@TableName("yxy_invoice_detail")
public class InvoiceDetail extends SuperModel<InvoiceDetail> {

    private static final long serialVersionUID = 1L;

	private Long id;
	@TableField("invoice_id")
	private Long invoiceId;
    /**
     * 发票流水号
     */
	@TableField("invoice_flow_number")
	private String invoiceFlowNumber;
    /**
     * 行号
     */
	@TableField("row_number")
	private String rowNumber;
    /**
     * 发票性质
     */
	@TableField("invoice_property")
	private String invoiceProperty;
    /**
     * 商品编号
     */
	@TableField("product_code")
	private String productCode;
    /**
     * 自定义编码
     */
	@TableField("self_defination_code")
	private String selfDefinationCode;
    /**
     * 商品名称
     */
	@TableField("product_name")
	private String productName;
    /**
     * 商品税目
     */
	@TableField("product_tax")
	private String productTax;
    /**
     * 规格型号
     */
	@TableField("variety_type")
	private String varietyType;
    /**
     * 计量单位
     */
	private String unit;
    /**
     * 商品数量
     */
	@TableField("product_num")
	private Integer productNum;
    /**
     * 商品单价
     */
	private String price;
    /**
     * 金额
     */
	private Double amout;
    /**
     * 扣除额
     */
	@TableField("diminish_amout")
	private String diminishAmout;
    /**
     * 税额
     */
	@TableField("tax_amout")
	private Double taxAmout;
    /**
     * 税率
     */
	@TableField("tax_rate")
	private Double taxRate;
    /**
     * 含税标志
     */
	@TableField("cotain_tax_mark")
	private Integer cotainTaxMark;
    /**
     * 增值税特殊管理
     */
	@TableField("vat_special_manage")
	private String vatSpecialManage;
    /**
     * 优惠政策标识
     */
	@TableField("preferential_policie_mark")
	private Integer preferentialPolicieMark;
    /**
     * 免税类型
     */
	@TableField("free_tax_type")
	private String freeTaxType;
	@TableField("created_at")
	private Date createdAt;
	@TableField("updated_at")
	private Date updatedAt;
	@TableField("order_id")
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getInvoiceProperty() {
		return invoiceProperty;
	}

	public void setInvoiceProperty(String invoiceProperty) {
		this.invoiceProperty = invoiceProperty;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSelfDefinationCode() {
		return selfDefinationCode;
	}

	public void setSelfDefinationCode(String selfDefinationCode) {
		this.selfDefinationCode = selfDefinationCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductTax() {
		return productTax;
	}

	public void setProductTax(String productTax) {
		this.productTax = productTax;
	}

	public String getVarietyType() {
		return varietyType;
	}

	public void setVarietyType(String varietyType) {
		this.varietyType = varietyType;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Double getAmout() {
		return amout;
	}

	public void setAmout(Double amout) {
		this.amout = amout;
	}

	public String getDiminishAmout() {
		return diminishAmout;
	}

	public void setDiminishAmout(String diminishAmout) {
		this.diminishAmout = diminishAmout;
	}

	public Double getTaxAmout() {
		return taxAmout;
	}

	public void setTaxAmout(Double taxAmout) {
		this.taxAmout = taxAmout;
	}

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public Integer getCotainTaxMark() {
		return cotainTaxMark;
	}

	public void setCotainTaxMark(Integer cotainTaxMark) {
		this.cotainTaxMark = cotainTaxMark;
	}

	public String getVatSpecialManage() {
		return vatSpecialManage;
	}

	public void setVatSpecialManage(String vatSpecialManage) {
		this.vatSpecialManage = vatSpecialManage;
	}

	public Integer getPreferentialPolicieMark() {
		return preferentialPolicieMark;
	}

	public void setPreferentialPolicieMark(Integer preferentialPolicieMark) {
		this.preferentialPolicieMark = preferentialPolicieMark;
	}

	public String getFreeTaxType() {
		return freeTaxType;
	}

	public void setFreeTaxType(String freeTaxType) {
		this.freeTaxType = freeTaxType;
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
		return "InvoiceDetail{" +
			"id=" + id +
			", invoiceId=" + invoiceId +
			", invoiceFlowNumber=" + invoiceFlowNumber +
			", rowNumber=" + rowNumber +
			", invoiceProperty=" + invoiceProperty +
			", productCode=" + productCode +
			", selfDefinationCode=" + selfDefinationCode +
			", productName=" + productName +
			", productTax=" + productTax +
			", varietyType=" + varietyType +
			", unit=" + unit +
			", productNum=" + productNum +
			", price=" + price +
			", amout=" + amout +
			", diminishAmout=" + diminishAmout +
			", taxAmout=" + taxAmout +
			", taxRate=" + taxRate +
			", cotainTaxMark=" + cotainTaxMark +
			", vatSpecialManage=" + vatSpecialManage +
			", preferentialPolicieMark=" + preferentialPolicieMark +
			", freeTaxType=" + freeTaxType +
			", createdAt=" + createdAt +
			", updatedAt=" + updatedAt +
			"}";
	}
}
