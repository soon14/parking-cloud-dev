package com.yxytech.parkingcloud.platform.form;


import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class OrgApproveForm implements Serializable {



    private String registrationNumber;

    @NotBlank(message = "组织机构代码不能为空")
    private String orgNumber;

    @NotBlank(message = "组织机构代码证不能为空")
    private String orgNumberCertificate;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getOrgNumberCertificate() {
        return orgNumberCertificate;
    }

    public void setOrgNumberCertificate(String orgNumberCertificate) {
        this.orgNumberCertificate = orgNumberCertificate;
    }
}
