package com.yxytech.parkingcloud.core.service;

import java.util.Map;

public interface IPaymentService {
    String getSign(Object object, String apiKey);
    String generateString();
    Map<String, String> getConfigInfo();
}
