package com.yxytech.parkingcloud.platform.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class EtcBlacklistFileUrlForm {
    @NotBlank(message = "etc文件下载地址不能为空!")
    private String downloadUrl;
    @NotNull(message = "etc版本号不能为空!")
    private Integer version;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
