package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.entity.PushMessage;
import com.yxytech.parkingcloud.core.entity.TransactionSuccess;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-12-06
 */
public interface IPushMessageService extends IService<PushMessage> {


    /**
     * 创建并发送订单创建消息
     * @param orderInfo
     */
    void createAndSendOrderCreatedMessage(OrderInfo orderInfo);


    /**
     * 创建并发送订单支付信息
     */
    void createAndSendOrderTransactionMessage(TransactionSuccess transactionSuccess, String parkingName);


    /**
     * 发送umeng notifcation
     * @param pushMessage
     */
    void sendUmengNotification(PushMessage pushMessage);
}
