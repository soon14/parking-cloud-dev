package com.yxytech.parkingcloud.core.utils;

import okhttp3.*;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

public class HttpRequest {
    /**
     * XML请求
     * @param url
     * @param body
     * @return
     * @throws IOException
     */
    public static String postByXml(String url, String body) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        MediaType xml = MediaType.parse("application/xml; charset=utf-8");

        RequestBody requestBody = RequestBody.create(xml, body);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = httpClient.newCall(request).execute();

        return response.body().string();
    }

    public static String sslPost(String filePath, String password, String url, String body) throws Exception {
        SSLSocketFactory sslSocketFactory = WechatSSLFactory.getSocketFactory(filePath, password);

        if (sslSocketFactory == null) {
            throw new Exception("请求失败!");
        }

        OkHttpClient httpClient = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory).build();
        MediaType xml = MediaType.parse("application/xml; charset=utf-8");

        RequestBody requestBody = RequestBody.create(xml, body);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = httpClient.newCall(request).execute();

        return response.body().string();
    }
}
