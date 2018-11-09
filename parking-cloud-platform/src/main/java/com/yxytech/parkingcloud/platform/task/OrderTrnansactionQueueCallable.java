package com.yxytech.parkingcloud.platform.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxytech.parkingcloud.commons.entity.WechatPaymentResultResponse;
import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.entity.OrderTransaction;
import com.yxytech.parkingcloud.core.enums.OrderTransactionEnum;
import com.yxytech.parkingcloud.core.service.IOrderInfoService;
import com.yxytech.parkingcloud.core.service.IOrderTransactionService;
import com.yxytech.parkingcloud.core.service.IPaymentService;
import com.yxytech.parkingcloud.core.utils.JaxbXMLUtil;
import com.yxytech.parkingcloud.core.utils.OrderInfoUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

@Component
public class OrderTrnansactionQueueCallable implements Callable {

    private StringRedisTemplate redisUtil;
    private IOrderTransactionService orderTransactionService;
    private IOrderInfoService orderInfoService;
    private OrderInfoUtil orderInfoUtil;

    private static final String transactionQueueName = "transactionQueue";

    public OrderTrnansactionQueueCallable(StringRedisTemplate redisUtil,
                                          IOrderTransactionService orderTransactionService,
                                          IPaymentService paymentService,
                                          IOrderInfoService orderInfoService,
                                          OrderInfoUtil orderInfoUtil) {
        this.redisUtil = redisUtil;
        this.orderTransactionService = orderTransactionService;
        this.orderInfoService = orderInfoService;
        this.orderInfoUtil = orderInfoUtil;

        Map<String, String> map = paymentService.getConfigInfo();
    }

    @Override
    @Transactional
    public Object call() throws Exception {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String orderNumber = redisUtil.opsForSet().pop(transactionQueueName);

                if (orderNumber != null) {
                    Wrapper<OrderTransaction> orderTransactionWrapper = new EntityWrapper<>();
                    orderTransactionWrapper.eq("id", orderNumber);
                    OrderTransaction orderTransaction = orderTransactionService.selectOne(orderTransactionWrapper);

                    if (orderTransaction != null && orderTransaction.getStatus().equals(OrderTransactionEnum.WAIT_FOR_PAY)) {
                        try {
                            String response =
                                    orderInfoUtil.validOrderTransaction(orderNumber, orderTransaction);
                            WechatPaymentResultResponse resultResponse = JaxbXMLUtil.xmlToBean(response,
                                    WechatPaymentResultResponse.class);

                            if (resultResponse == null) {
                                redisUtil.opsForSet().add(transactionQueueName, orderNumber);
                            } else {
                                if (resultResponse.getReturn_code().equals("SUCCESS")) {
                                    if (! resultResponse.getTrade_state().equals("USERPAYING")) {
                                        orderTransaction.setTransactionDetail(response);
                                        orderTransaction = orderTransactionService.setInfoToOrderTransaction(resultResponse,
                                                orderTransaction, false);

                                        OrderInfo orderInfo = orderInfoService.selectById(orderTransaction.getOrderId());

                                        orderTransactionService.updateById(orderTransaction);

                                        if (! resultResponse.getTrade_state().equals("SUCCESS")) {
                                            orderInfoUtil.dealCouponInfo(orderInfo, orderTransaction, resultResponse);
                                        } else {
                                            orderInfo = orderInfoService.paymentSuccess(orderInfo, orderTransaction);
                                            orderInfoService.updateById(orderInfo);
                                        }
                                    }
                                } else if (resultResponse.getErr_code().equals("SYSTEMERROR")) {
                                    redisUtil.opsForSet().add(transactionQueueName, orderNumber);
                                }
                            }
                        } catch (Exception e) {
                            redisUtil.opsForSet().add(transactionQueueName, orderNumber);
                        }
                    }
                }
            }
        }, 0, 10000);

        return null;
    }
}
