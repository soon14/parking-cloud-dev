package com.yxytech.parkingcloud.app.controller.form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class SalesInformationForm implements Serializable{

    @NotNull(message = "单位Id不得为空")
    private Integer organzationId;

    @Length(max = 100, message = "发票抬头长度不得大于100位")
    private String invoiceHeader;

    @Length(max = 100, message = "纳税人识别号不得大于100位")
    private String taxpayerIdentificationNumber;

    private String location;
    private String mobile;
    private String bank;
    private String bankAccount;
    @NotNull(message = "启用状态不得为空")
    private Integer inUse;
    private Date usedTime;
    private Date unUsedTime;

    public Integer getOrganzationId() {
        return organzationId;
    }

    public void setOrganzationId(Integer organzationId) {
        this.organzationId = organzationId;
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

    public Integer getInUse() {
        return inUse;
    }

    public void setInUse(Integer inUse) {
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
}
