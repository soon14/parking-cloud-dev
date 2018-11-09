package com.yxytech.parkingcloud.platform.form;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class WechatPaymentSettingForm implements Serializable {
    private Long parkingId;

    @NotBlank(message = "appID不能为空")
    private String appId;

    @NotBlank(message = "商户号不能为空")
    private String mchId;

    @NotBlank(message = "api key 不能为空")
    private String apiKey;

    @NotBlank(message = "商户证书不能为空")
    private String certificationPath;

    @NotBlank(message = "商户证书密码不能为空，默认为商户号!")
    private String password;

    public String getCertificationPath() {
        return certificationPath;
    }

    public void setCertificationPath(String certificationPath) {
        this.certificationPath = certificationPath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
