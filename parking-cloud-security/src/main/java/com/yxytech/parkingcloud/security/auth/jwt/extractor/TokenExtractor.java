package com.yxytech.parkingcloud.security.auth.jwt.extractor;

public interface TokenExtractor {

    /**
     * 提取token
     * @param payload
     * @return
     */
    public String extract(String payload);
}
