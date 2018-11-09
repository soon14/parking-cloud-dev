package com.yxytech.parkingcloud.platform.task;

import com.yxytech.parkingcloud.core.service.IOrderInfoService;
import com.yxytech.parkingcloud.core.service.IOrderTransactionService;
import com.yxytech.parkingcloud.core.service.IPaymentService;
import com.yxytech.parkingcloud.core.utils.OrderInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ApplicationListener implements ApplicationRunner {

    @Autowired
    private StringRedisTemplate redisUtil;

    @Autowired
    private IOrderTransactionService orderTransactionService;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private OrderInfoUtil orderInfoUtil;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Integer cpuNumbers = Runtime.getRuntime().availableProcessors();
        ExecutorService threadPool = Executors.newFixedThreadPool(cpuNumbers);

        OrderTrnansactionQueueCallable callable = new OrderTrnansactionQueueCallable(
            redisUtil,
            orderTransactionService,
            paymentService,
            orderInfoService,
            orderInfoUtil
        );

        for (Integer i = 0; i < cpuNumbers; i++) {
            threadPool.submit(callable);
        }
    }
}
