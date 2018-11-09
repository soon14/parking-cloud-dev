package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxytech.parkingcloud.commons.entity.WechatPaymentResultResponse;
import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.entity.OrderTransaction;
import com.yxytech.parkingcloud.core.entity.TransactionSuccess;
import com.yxytech.parkingcloud.core.enums.OrderTransactionEnum;
import com.yxytech.parkingcloud.core.service.IOrderInfoService;
import com.yxytech.parkingcloud.core.service.IOrderTransactionService;
import com.yxytech.parkingcloud.core.service.ITransactionSuccessService;
import com.yxytech.parkingcloud.core.utils.JaxbXMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/notify")
public class TransactionController {

    @Autowired
    private IOrderTransactionService orderTransactionService;

    @Autowired
    private ITransactionSuccessService transactionSuccessService;

    @Autowired
    private IOrderInfoService orderInfoService;

    @RequestMapping(value = "/wechat", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    public String wechatNotify(@RequestBody String response) {
        String success = "SUCCESS";
        String wechatReturnSuccess = "<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>";
        String wechatRetrunFail = "<xml><return_code><![CDATA[FAIL]]></return_code></xml>";

        WechatPaymentResultResponse paymentResultResponse = JaxbXMLUtil.xmlToBean(response, WechatPaymentResultResponse.class);

        if (paymentResultResponse != null && paymentResultResponse.getReturn_code().equals(success)) {
            try {
                Wrapper<OrderTransaction> orderTransactionWrapper = new EntityWrapper<>();

                orderTransactionWrapper.eq("id", paymentResultResponse.getOut_trade_no())
                        .eq("status", OrderTransactionEnum.WAIT_FOR_PAY);

                OrderTransaction orderTransaction = orderTransactionService.selectOne(orderTransactionWrapper);

                if (orderTransaction == null) {
                    // TODO 是否要去做处理
//                OrderTransaction orderTransactionParam = new OrderTransaction();
//
//                orderTransactionParam.setAmount((double) (paymentResultResponse.getTotal_fee() / 100));
//                orderTransactionParam.setPlateNumber("");
//                orderTransactionParam.setPlateColor(ColorsEnum.BLACK);
//                orderTransactionParam.setPayWay("微信支付");
//                orderTransactionParam.setCreatedWay(OrderTransactionCreateWayEnum.WECHAT);
//                orderTransactionParam.setId(paymentResultResponse.getOut_trade_no());
//                orderTransactionParam.setOrderNumber("");
//                orderTransactionParam.setFinishedAt(new Date());
//                orderTransactionParam.setTransactionDetail(response);
//                orderTransactionParam.setTransactionId(paymentResultResponse.getTransaction_id());
//
//                Boolean ret = orderTransactionService.insert(orderTransactionParam);
//
//                if (! ret) {
//                    return wechatRetrunFail;
//                }

                    return wechatReturnSuccess;
                } else {
                    if (! orderTransaction.getStatus().equals(OrderTransactionEnum.WAIT_FOR_PAY)) {
                        return success;
                    }

                    // 更新相关信息
                    Double totalFee = this.formatAmount((double) (paymentResultResponse.getTotal_fee() / 100.0));
                    Double amount = this.formatAmount(orderTransaction.getAmount());

                    if (! totalFee.equals(amount)) {
                        return wechatRetrunFail;
                    }

                    OrderInfo orderInfo = orderInfoService.selectById(orderTransaction.getOrderId());
                    orderTransaction.setTransactionDetail(response);
                    orderTransaction = orderTransactionService.setInfoToOrderTransaction(paymentResultResponse,
                            orderTransaction, true);

                    orderInfo = orderInfoService.paymentSuccess(orderInfo, orderTransaction);

                    orderInfoService.updateById(orderInfo);
                    orderTransactionService.updateById(orderTransaction);

                    return wechatReturnSuccess;
                }
            } catch (RuntimeException e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                e.printStackTrace();
                return wechatRetrunFail;
            }

        }

        return wechatRetrunFail;
    }

    private Double formatAmount(Double amount) {
        BigDecimal bd = new BigDecimal(amount);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }
}
