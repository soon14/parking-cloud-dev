package com.yxytech.parkingcloud.app.entity;

import com.yxytech.parkingcloud.core.entity.InvoiceInformationRequest;
import com.yxytech.parkingcloud.core.entity.InvoiceInformationResult;

public class InvoiceVO {

    private long id;                //ID
    private long requestAt;         //申请时间
    private long createAt;          //开票时间
    private String invoiceImageUrl; //发票Image路径
    private String invoicePdfUrl;   //发票PDF文件路径
    private Double money;            //发票金额
    private String content;         //发票内容（停车费

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRequestAt() {
        return requestAt;
    }

    public void setRequestAt(long requestAt) {
        this.requestAt = requestAt;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public String getInvoiceImageUrl() {
        return invoiceImageUrl;
    }

    public void setInvoiceImageUrl(String invoiceImageUrl) {
        this.invoiceImageUrl = invoiceImageUrl;
    }

    public String getInvoicePdfUrl() {
        return invoicePdfUrl;
    }

    public void setInvoicePdfUrl(String invoicePdfUrl) {
        this.invoicePdfUrl = invoicePdfUrl;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public InvoiceVO(InvoiceInformationRequest request, InvoiceInformationResult result) {
        this.id = request.getInvoiceId();
        this.requestAt = request.getCreatedAt().getTime();
        this.createAt = request.getApplyBillDate().getTime();
        this.money = request.getPriceTaxTotal();
        this.content = "停车费";//先写死
        this.invoiceImageUrl = result.getCloudPlatformInvoiceImage();
        this.invoicePdfUrl = result.getPlatformInvoiceUrl();
    }
}
