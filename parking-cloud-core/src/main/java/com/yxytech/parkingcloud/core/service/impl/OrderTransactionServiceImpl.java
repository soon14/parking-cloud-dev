package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.commons.entity.WechatPaymentResultResponse;
import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.entity.OrderTransaction;
import com.yxytech.parkingcloud.core.enums.OrderTransactionCreateWayEnum;
import com.yxytech.parkingcloud.core.enums.OrderTransactionEnum;
import com.yxytech.parkingcloud.core.enums.TransactionPayWay;
import com.yxytech.parkingcloud.core.mapper.OrderTransactionMapper;
import com.yxytech.parkingcloud.core.service.IOrderTransactionService;
import com.yxytech.parkingcloud.core.utils.UniqueCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwd
 * @since 2017-11-03
 */
@Service
public class OrderTransactionServiceImpl extends ServiceImpl<OrderTransactionMapper, OrderTransaction> implements IOrderTransactionService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public OrderTransaction setInfoToOrderTransaction(WechatPaymentResultResponse resultResponse,
                                                      OrderTransaction orderTransaction,
                                                      Boolean type) {
        orderTransaction.setTransactionId(resultResponse.getTransaction_id());
        orderTransaction.setFinishedAt(new Date());

        if (! type) {
            if (resultResponse.getTrade_state() == null || resultResponse.getTrade_state().equals("SUCCESS")) {
                orderTransaction.setStatus(OrderTransactionEnum.SUCCESS);
            } else {
                orderTransaction.setStatus(OrderTransactionEnum.FAILED);
                orderTransaction.setRemark(resultResponse.getTrade_state_desc());
            }
        } else {
            orderTransaction.setStatus(OrderTransactionEnum.SUCCESS);
        }


        return orderTransaction;
    }

    @Override
    public OrderTransaction setInfoToOrderTransaction(OrderInfo orderInfo, Double amount, String mchId, TransactionPayWay payWay, String transactionId, String promoCode) {
        String id = UniqueCode.generateId(redisTemplate, "transaction");
        OrderTransaction orderTransaction = new OrderTransaction();
        amount = this.getFinalDiscountAmount(orderInfo, amount);

        orderTransaction.setOrderNumber(orderInfo.getOrderNumber());
        orderTransaction.setOrderId(orderInfo.getId());
        orderTransaction.setCreatedWay(OrderTransactionCreateWayEnum.SYSTEM);
        orderTransaction.setPlateNumber(orderInfo.getPlateNumber());
        orderTransaction.setPlateColor(orderInfo.getPlateColor());
        orderTransaction.setMchId(mchId);
        orderTransaction.setParkingId(orderInfo.getParkingId());
        orderTransaction.setUuid(id);
        orderTransaction.setAmount(amount);
        orderTransaction.setUserId(orderInfo.getUserId());
        orderTransaction.setPayWay(payWay);
        orderTransaction.setStatus(OrderTransactionEnum.SUCCESS);

        if (transactionId != null) {
            orderTransaction.setTransactionId(transactionId);
            orderTransaction.setTransactionDetail(promoCode);
            orderTransaction.setFinishedAt(new Date());
        }

        return orderTransaction;
    }

    @Override
    public OrderTransaction setInfoToOrderTransaction(OrderInfo orderInfo, Double amount, String mchId, TransactionPayWay payWay) {
        String id = UniqueCode.generateId(redisTemplate, "transaction");
        OrderTransaction orderTransaction = new OrderTransaction();

        orderTransaction.setOrderNumber(orderInfo.getOrderNumber());
        orderTransaction.setOrderId(orderInfo.getId());
        orderTransaction.setCreatedWay(OrderTransactionCreateWayEnum.SYSTEM);
        orderTransaction.setPlateNumber(orderInfo.getPlateNumber());
        orderTransaction.setPlateColor(orderInfo.getPlateColor());
        orderTransaction.setMchId(mchId);
        orderTransaction.setParkingId(orderInfo.getParkingId());
        orderTransaction.setUuid(id);
        orderTransaction.setAmount(amount);
        orderTransaction.setUserId(orderInfo.getUserId());
        orderTransaction.setPayWay(payWay);
        orderTransaction.setStatus(OrderTransactionEnum.SUCCESS);

        return orderTransaction;
    }

    @Override
    public Date getLastTransactionDate(Long orderId) {
        return baseMapper.getLastTransactionDate(orderId);
    }

    @Override
    public OrderTransaction getLastPaymentTransaction(Long orderId, TransactionPayWay payWay) {
        OrderTransaction transaction = new OrderTransaction();

        transaction.setOrderId(orderId);
        transaction.setStatus(OrderTransactionEnum.WAIT_FOR_PAY);
        transaction.setPayWay(payWay);

        return baseMapper.selectOne(transaction);
    }

    private Double getFinalDiscountAmount(OrderInfo orderInfo, Double amount) {
        Double discountAmount = orderInfo.getReceivableAmount() - orderInfo.getPaidAmount();

        if (amount > discountAmount) {
            return discountAmount;
        }

        return amount;
    }
}
