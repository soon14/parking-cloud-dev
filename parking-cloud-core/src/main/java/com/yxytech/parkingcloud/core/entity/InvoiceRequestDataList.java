package com.yxytech.parkingcloud.core.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "fpmx")
public class InvoiceRequestDataList {


    //行号
    private String fpmxxh;
    //发票行性质
    private String fphxz;
    //商品编码
    private String spbm;
    //自定义编码
    private String zxbm;
    //商品名称
    private String spmc;
    //商品税目
    private String spsm;
    //规格型号
    private String ggxh;
    //计量单位
    private String dw;
    //商品数量
    private String spsl;
    //单价
    private String spdj;
    //金额
    private Double je;
    //扣除额
    private Double kce;
    //税额
    private Double se;
    //税率
    private Double sl;
    //折行对应行号
    private String zhdyhh;
    //
    private Integer hsbz;
    //增值税特殊管理
    private String zzstsgl;
    //优惠政策标识
    private Integer yhzcbs;

    public String getFpmxxh() {
        return fpmxxh;
    }

    public void setFpmxxh(String fpmxxh) {
        this.fpmxxh = fpmxxh;
    }

    public String getFphxz() {
        return fphxz;
    }

    public void setFphxz(String fphxz) {
        this.fphxz = fphxz;
    }

    public String getSpbm() {
        return spbm;
    }

    public void setSpbm(String spbm) {
        this.spbm = spbm;
    }

    public String getZxbm() {
        return zxbm;
    }

    public void setZxbm(String zxbm) {
        this.zxbm = zxbm;
    }

    public String getSpmc() {
        return spmc;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getSpsm() {
        return spsm;
    }

    public void setSpsm(String spsm) {
        this.spsm = spsm;
    }

    public String getGgxh() {
        return ggxh;
    }

    public void setGgxh(String ggxh) {
        this.ggxh = ggxh;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getSpsl() {
        return spsl;
    }

    public void setSpsl(String spsl) {
        this.spsl = spsl;
    }

    public String getSpdj() {
        return spdj;
    }

    public void setSpdj(String spdj) {
        this.spdj = spdj;
    }

    public Double getJe() {
        return je;
    }

    public void setJe(Double je) {
        this.je = je;
    }

    public Double getKce() {
        return kce;
    }

    public void setKce(Double kce) {
        this.kce = kce;
    }

    public Double getSe() {
        return se;
    }

    public void setSe(Double se) {
        this.se = se;
    }

    public Double getSl() {
        return sl;
    }

    public void setSl(Double sl) {
        this.sl = sl;
    }

    public String getZhdyhh() {
        return zhdyhh;
    }

    public void setZhdyhh(String zhdyhh) {
        this.zhdyhh = zhdyhh;
    }

    public Integer getHsbz() {
        return hsbz;
    }

    public void setHsbz(Integer hsbz) {
        this.hsbz = hsbz;
    }

    public String getZzstsgl() {
        return zzstsgl;
    }

    public void setZzstsgl(String zzstsgl) {
        this.zzstsgl = zzstsgl;
    }

    public Integer getYhzcbs() {
        return yhzcbs;
    }

    public void setYhzcbs(Integer yhzcbs) {
        this.yhzcbs = yhzcbs;
    }

    public String getLslbs() {
        return lslbs;
    }

    public void setLslbs(String lslbs) {
        this.lslbs = lslbs;
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

    //免税类型
    private String lslbs;
    //备用字段1
    private String byzd1;
    //备用字段2
    private String byzd2;

    private String byzd3;

    public String getByzd3() {
        return byzd3;
    }

    public void setByzd3(String byzd3) {
        this.byzd3 = byzd3;
    }
}
