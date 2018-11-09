package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.OrderTransaction;
import com.yxytech.parkingcloud.core.entity.TransactionSuccess;
import com.yxytech.parkingcloud.core.enums.TransactionPayWay;
import com.yxytech.parkingcloud.core.mapper.TransactionSuccessMapper;
import com.yxytech.parkingcloud.core.service.ITransactionSuccessService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwd
 * @since 2017-11-06
 */
@Service
public class TransactionSuccessServiceImpl extends ServiceImpl<TransactionSuccessMapper, TransactionSuccess> implements ITransactionSuccessService {

    @Override
    public TransactionSuccess setInfo(OrderTransaction orderTransaction, Double totalFee) {
        TransactionSuccess transactionSuccess = new TransactionSuccess();

        transactionSuccess.setUserId(orderTransaction.getUserId());
        transactionSuccess.setOrderId(orderTransaction.getOrderId());
        transactionSuccess.setPlateColor(orderTransaction.getPlateColor());
        transactionSuccess.setPlateNumber(orderTransaction.getPlateNumber());
        transactionSuccess.setPayWay(TransactionPayWay.WECHAT);
        transactionSuccess.setPayMethod("");
        transactionSuccess.setCreatedAt(new Date());
        transactionSuccess.setAmount(totalFee);

        return transactionSuccess;
    }
}
