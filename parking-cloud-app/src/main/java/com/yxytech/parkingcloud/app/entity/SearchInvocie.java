package com.yxytech.parkingcloud.app.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class SearchInvocie {

    private String fpqqlsh;

    private String nsrsbh;

    public String getFpqqlsh() {
        return fpqqlsh;
    }

    public void setFpqqlsh(String fpqqlsh) {
        this.fpqqlsh = fpqqlsh;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }
}
