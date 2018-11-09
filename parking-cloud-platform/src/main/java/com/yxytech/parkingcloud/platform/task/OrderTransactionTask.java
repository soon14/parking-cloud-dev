package com.yxytech.parkingcloud.platform.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxytech.parkingcloud.core.entity.OrderTransaction;
import com.yxytech.parkingcloud.core.enums.OrderTransactionEnum;
import com.yxytech.parkingcloud.core.enums.TransactionPayWay;
import com.yxytech.parkingcloud.core.service.IOrderTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class OrderTransactionTask {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IOrderTransactionService orderTransactionService;

    @Value("${error.time.length}")
    private Integer validTime;

    private static final String transactionInfoQueueName = "transactionQueue";

    @Scheduled(fixedDelay = 2000 * 60)
    public void getAllFailedOrderTransaction() {
        // 因为微信的预支付是两个小时有效期
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - validTime);

        Wrapper<OrderTransaction> orderTransactionWrapper = new EntityWrapper<>();

        orderTransactionWrapper.where("created_at <= {0}", calendar.getTime())
                .eq("pay_way", TransactionPayWay.WECHAT)
                .eq("status", OrderTransactionEnum.WAIT_FOR_PAY);

        List<OrderTransaction> orderTransactionList = orderTransactionService.selectList(orderTransactionWrapper);
        List<String> result = new ArrayList<>();

        orderTransactionList.forEach((OrderTransaction o) -> {
            result.add(o.getUuid());
        });

        if (result.size() > 0) {
            String[] finalArr = new String[result.size()];
            finalArr = result.toArray(finalArr);
            result.clear();

            redisTemplate.opsForSet().add(transactionInfoQueueName, finalArr);
        }
    }
}
