package com.yxytech.parkingcloud.core.entity;

import com.yxytech.parkingcloud.core.enums.BillTypeEnum;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InvoiceInformationRequestForm implements Serializable{

    @Length(min = 10, max = 30, message = "发票流水号长度10-30位")
    private String invoiceFlowNumber;

    @NotBlank(message = "开票点编码必填")
    @Length(max = 30, message = "开票点编码不得大于30位")
    private String billPointCode;

    @Length(max = 3, message = "发票种类编码不得大于3位")
    private String invoiceTypeCode;

    private String applyWay;

    @NotNull(message = "购方id不得为空")
    private Integer buyId;

    @NotNull(message = "销方Id不得为空")
    private Integer saleId;

    @Pattern(regexp = "^\\d{11}$|^$", message = "手机号格式不正确")
    private String mobile;

    @Email(message = "邮箱格式不正确")
    private String email;

    @Length(max = 100, message = "收款人名称长度不得大于100")
    private String payee;

    @Length(max = 100, message = "开票人名称不得大于100位")
    private String ssuer;

    @Length(max = 100, message = "审核人名称长度不得大于100位")
    private String manager;

    @NotNull(message = "开票类型必填")
    private BillTypeEnum billType;

    private List<InvoiceDetail> invoiceLists;

    public List<JylshList> getTransactionSerialNumberList() {
        return transactionSerialNumberList;
    }

    public void setTransactionSerialNumberList(List<JylshList> transactionSerialNumberList) {
        this.transactionSerialNumberList = transactionSerialNumberList;
    }

    private List<JylshList> transactionSerialNumberList;

    public List<InvoiceDetail> getInvoiceLists() {
        return invoiceLists;
    }

    public void setInvoiceLists(List<InvoiceDetail> invoiceLists) {
        this.invoiceLists = invoiceLists;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @NotNull(message = "订单id不得为空")
    private Long orderId;

    private String originalInvoiceCode;
    private String originalInvoiceNumber;
    private String remarks;
    private String billChannel;
    private Integer billStatus;
    private Date applyBillDate;
    private Double totalAmout;
    private Double totalTaxAmout;
    private Double priceTaxTotal;

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

    private Date createdAt;
    private Date updatedAt;

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

    public Integer getBuyId() {
        return buyId;
    }

    public void setBuyId(Integer buyId) {
        this.buyId = buyId;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
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

    public Integer getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Integer billStatus) {
        this.billStatus = billStatus;
    }

    public Date getApplyBillDate() {
        return applyBillDate;
    }

    public void setApplyBillDate(Date applyBillDate) {
        this.applyBillDate = applyBillDate;
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


}
