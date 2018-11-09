package com.yxytech.parkingcloud.core.entity;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class InvoiceRequestData {


    //发票流水号
    private String fpqqlsh;
    //开票点编码
    private String kpddm;
    //发票类型代码
    private String fplxdm;
    //购方客户名称
    private String ghdwmc;
    //购方客户税号
    private String ghdwdm;
    //购方地址及电话
    private String ghdwdzdh;
    //购方开户行及账户
    private String ghdwyhzh;
    //购方客户电话
    private String gfkhdh;
    //购方客户邮箱
    private String gfkhyx;
    //销方客户名称
    private String xsdwmc;
    //销方客户税号
    private String xsdwdm;
    //销方地址及电话
    private String xsdwdzdh;
    //销方开户行及账户
    private String xsdwyhzh;
    //收款人名称
    private String skr;
    //开票人名称
    private String kpr;
    //审核人名称
    private String fhr;
    //开票类型
    private Integer kplx;
    //原发票代码
    private String yfpdm;
    //原发票号码
    private String yfphm;
    //合计金额
    private Double hjje;
    //合计税额
    private Double hjse;
    //价税合计
    private Double jshj;
    //备注
    private String bz;
    //商品编码版本号
    private String bbh;
    //备用字段1
    private String byzd1;
    //备用字段2
    private String byzd2;
    //备用字段3
    private String byzd3;
    //详情

    public String getFpqqlsh() {
        return fpqqlsh;
    }

    public void setFpqqlsh(String fpqqlsh) {
        this.fpqqlsh = fpqqlsh;
    }

    public String getKpddm() {
        return kpddm;
    }

    public void setKpddm(String kpddm) {
        this.kpddm = kpddm;
    }

    public String getFplxdm() {
        return fplxdm;
    }

    public void setFplxdm(String fplxdm) {
        this.fplxdm = fplxdm;
    }

    public String getGhdwmc() {
        return ghdwmc;
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

    public String getGhdwdzdh() {
        return ghdwdzdh;
    }

    public void setGhdwdzdh(String ghdwdzdh) {
        this.ghdwdzdh = ghdwdzdh;
    }

    public String getGhdwyhzh() {
        return ghdwyhzh;
    }

    public void setGhdwyhzh(String ghdwyhzh) {
        this.ghdwyhzh = ghdwyhzh;
    }

    public String getGfkhdh() {
        return gfkhdh;
    }

    public void setGfkhdh(String gfkhdh) {
        this.gfkhdh = gfkhdh;
    }

    public String getGfkhyx() {
        return gfkhyx;
    }

    public void setGfkhyx(String gfkhyx) {
        this.gfkhyx = gfkhyx;
    }

    public String getXsdwmc() {
        return xsdwmc;
    }

    public void setXsdwmc(String xsdwmc) {
        this.xsdwmc = xsdwmc;
    }

    public String getXsdwdm() {
        return xsdwdm;
    }

    public void setXsdwdm(String xsdwdm) {
        this.xsdwdm = xsdwdm;
    }

    public String getXsdwdzdh() {
        return xsdwdzdh;
    }

    public void setXsdwdzdh(String xsdwdzdh) {
        this.xsdwdzdh = xsdwdzdh;
    }

    public String getXsdwyhzh() {
        return xsdwyhzh;
    }

    public void setXsdwyhzh(String xsdwyhzh) {
        this.xsdwyhzh = xsdwyhzh;
    }

    public String getSkr() {
        return skr;
    }

    public void setSkr(String skr) {
        this.skr = skr;
    }

    public String getKpr() {
        return kpr;
    }

    public void setKpr(String kpr) {
        this.kpr = kpr;
    }

    public String getFhr() {
        return fhr;
    }

    public void setFhr(String fhr) {
        this.fhr = fhr;
    }

    public Integer getKplx() {
        return kplx;
    }

    public void setKplx(Integer kplx) {
        this.kplx = kplx;
    }

    public String getYfpdm() {
        return yfpdm;
    }

    public void setYfpdm(String yfpdm) {
        this.yfpdm = yfpdm;
    }

    public String getYfphm() {
        return yfphm;
    }

    public void setYfphm(String yfphm) {
        this.yfphm = yfphm;
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

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getBbh() {
        return bbh;
    }

    public void setBbh(String bbh) {
        this.bbh = bbh;
    }

    public String getByzd1() {
        return byzd1;
    }

    public void setByzd1(String byzd1) {
        this.byzd1 = byzd1;
    }

    public String getByzd2() {
        return byzd2;
    }

    public void setByzd2(String byzd2) {
        this.byzd2 = byzd2;
    }

    public String getByzd3() {
        return byzd3;
    }

    public void setByzd3(String byzd3) {
        this.byzd3 = byzd3;
    }

    public List<InvoiceRequestDataList> getFpmxList() {
        return fpmxList;
    }

    public void setFpmxList(List<InvoiceRequestDataList> fpmxList) {
        this.fpmxList = fpmxList;
    }

    @XmlElementWrapper(name = "fpmxList")
    @XmlElement(name = "fpmx")
    private List<InvoiceRequestDataList> fpmxList;

    private List<JylshList> jylshList;

    public List<JylshList> getJylshList() {
        return jylshList;
    }

    public void setJylshList(List<JylshList> jylshList) {
        this.jylshList = jylshList;
    }
}
