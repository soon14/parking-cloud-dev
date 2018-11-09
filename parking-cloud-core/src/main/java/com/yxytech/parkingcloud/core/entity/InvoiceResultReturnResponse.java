package com.yxytech.parkingcloud.core.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceResultReturnResponse {
    private String rtncode;
    private String rtnmsg;
    private String fpdm;
    private String skm;//查询密码

    public String getSkm() {
        return skm;
    }

    public void setSkm(String skm) {
        this.skm = skm;
    }

    private Integer fphm;
    private Date kprq;
    private String ghdwmc;//购方名称

    public String getRtncode() {
        return rtncode;
    }

    public void setRtncode(String rtncode) {
        this.rtncode = rtncode;
    }

    public String getRtnmsg() {
        return rtnmsg;
    }

    public void setRtnmsg(String rtnmsg) {
        this.rtnmsg = rtnmsg;
    }

    public String getFpdm() {
        return fpdm;
    }

    public void setFpdm(String fpdm) {
        this.fpdm = fpdm;
    }

    public Integer getFphm() {
        return fphm;
    }

    public void setFphm(Integer fphm) {
        this.fphm = fphm;
    }

    public String getGhdwmc() {
        return ghdwmc;
    }

    public Date getKprq() {
        return kprq;
    }

    public void setKprq(Date kprq) {
        this.kprq = kprq;
    }

    public void setGhdwmc(String ghdwmc) {
        this.ghdwmc = ghdwmc;
    }

    public String getGhdwdm() {
        return ghdwdm;
    }

    public void setGhdwdm(String ghdwdm) {
        this.ghdwdm = ghdwdm;
    }

    public String getXhdwmc() {
        return xhdwmc;
    }

    public void setXhdwmc(String xhdwmc) {
        this.xhdwmc = xhdwmc;
    }

    public String getXhdwdm() {
        return xhdwdm;
    }

    public void setXhdwdm(String xhdwdm) {
        this.xhdwdm = xhdwdm;
    }

    public Double getHjje() {
        return hjje;
    }

    public void setHjje(Double hjje) {
        this.hjje = hjje;
    }

    public Double getHjse() {
        return hjse;
    }

    public void setHjse(Double hjse) {
        this.hjse = hjse;
    }

    public Double getJshj() {
        return jshj;
    }

    public void setJshj(Double jshj) {
        this.jshj = jshj;
    }

    public String getFpqqlsh() {
        return fpqqlsh;
    }

    public void setFpqqlsh(String fpqqlsh) {
        this.fpqqlsh = fpqqlsh;
    }

    public String getPdfurl() {
        return pdfurl;
    }

    public void setPdfurl(String pdfurl) {
        this.pdfurl = pdfurl;
    }

    private String ghdwdm;//购方客户税号
    private String xhdwmc;//销方名称
    private String xhdwdm;//销方单位税号
    private Double hjje;//合计金额
    private Double hjse;//合计税额
    private Double jshj;//价税合计
    private String fpqqlsh;//发票流水号
    private String pdfurl;//返回url路径
}
