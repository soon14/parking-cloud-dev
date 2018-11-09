package com.yxytech.parkingcloud.app.entity;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;

public class InvoiceInformationRequestQuery {

    private Integer type;
    private String header;
    private String tax_id_number;
    private String location;
    private String bank;
    private String bank_account;
    private String mobile;
    private String email;
    private String orderids;

    private String telephone;
    private Integer customer_type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTax_id_number() {
        return tax_id_number;
    }

    public void setTax_id_number(String tax_id_number) {
        this.tax_id_number = tax_id_number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
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

    public String getOrderids() {
        return orderids;
    }

    public void setOrderids(String orderids) {
        this.orderids = orderids;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(Integer customer_type) {
        this.customer_type = customer_type;
    }
}
