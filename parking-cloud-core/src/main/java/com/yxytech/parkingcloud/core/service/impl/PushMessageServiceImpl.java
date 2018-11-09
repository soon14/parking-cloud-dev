package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.enums.InUseEnum;
import com.yxytech.parkingcloud.core.mapper.PushMessageMapper;
import com.yxytech.parkingcloud.core.service.ICustomerService;
import com.yxytech.parkingcloud.core.service.IPushMessageService;
import com.yxytech.parkingcloud.core.service.IUserPushTokenService;
import com.yxytech.parkingcloud.core.utils.UMengPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-12-06
 */
@Service
public class PushMessageServiceImpl extends ServiceImpl<PushMessageMapper, PushMessage> implements IPushMessageService {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IUserPushTokenService userPushTokenService;

    @Autowired
    private UMengPush uMengPush;

    @Async
    @Override
    public void createAndSendOrderCreatedMessage(OrderInfo orderInfo) {
        Customer customer = customerService.selectById(orderInfo.getUserId());
        if (customer == null) {
            return;
        }

        EntityWrapper<UserPushToken> ew = new EntityWrapper<>();
        ew.eq("user_id", customer.getId());
        ew.eq("in_use", InUseEnum.IN_USE);
        UserPushToken pushToken = userPushTokenService.selectOne(ew);
        if (pushToken == null) {
            return;
        }

        String title = "停车订单已创建";
        String content = orderInfo.getPlateNumber() + "于" + df.format(orderInfo.getCreatedAt()) + "进入" + orderInfo.getParkingName();
        PushMessage message = new PushMessage(title, content, customer.getId(), pushToken.getPushToken());
        baseMapper.insert(message);

        sendUmengNotification(message);
    }

    @Async
    @Override
    public void createAndSendOrderTransactionMessage(TransactionSuccess transactionSuccess, String parkingName) {
        Customer customer = customerService.selectById(transactionSuccess.getUserId());
        if (customer == null) {
            return;
        }

        EntityWrapper<UserPushToken> ew = new EntityWrapper<>();
        ew.eq("user_id", customer.getId());
        ew.eq("in_use", InUseEnum.IN_USE);
        UserPushToken pushToken = userPushTokenService.selectOne(ew);
        if (pushToken == null) {
            return;
        }

        String title = "停车订单已支付";
        String content = transactionSuccess.getPlateNumber() + "于" + df.format(transactionSuccess.getCreatedAt()) +
                "在" + parkingName +
                "支付了: " + transactionSuccess.getAmount() + "元.";
        PushMessage message = new PushMessage(title, content, customer.getId(), pushToken.getPushToken());
        baseMapper.insert(message);

        sendUmengNotification(message);
    }

    @Override
    public void sendUmengNotification(PushMessage pushMessage) {
        boolean sent = uMengPush.sendAndroidNotification(
                pushMessage.getUmengPushToken(),
                pushMessage.getTitle(),
                pushMessage.getContent());

        if (!sent) return;

        pushMessage.setUmengSentAt(new Date());
        baseMapper.updateById(pushMessage);
    }


}
