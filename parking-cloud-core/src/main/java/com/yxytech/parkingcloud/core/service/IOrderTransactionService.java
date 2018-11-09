package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.commons.entity.WechatPaymentResultResponse;
import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.entity.OrderTransaction;
import com.yxytech.parkingcloud.core.enums.TransactionPayWay;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-11-03
 */
public interface IOrderTransactionService extends IService<OrderTransaction> {
	OrderTransaction setInfoToOrderTransaction(WechatPaymentResultResponse resultResponse,
                                               OrderTransaction orderTransaction,
											   Boolean type);

	OrderTransaction setInfoToOrderTransaction(OrderInfo orderInfo, Double amount, String mchId,
											   TransactionPayWay payWay, String transactionId,
											   String promoCode);

	OrderTransaction setInfoToOrderTransaction(OrderInfo orderInfo, Double amount, String mchId,
											   TransactionPayWay payWay);

	Date getLastTransactionDate(Long orderId);

	OrderTransaction getLastPaymentTransaction(Long orderId, TransactionPayWay payWay);
}
