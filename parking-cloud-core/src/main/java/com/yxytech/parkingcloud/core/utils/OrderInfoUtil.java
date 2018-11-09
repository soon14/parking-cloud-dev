package com.yxytech.parkingcloud.core.utils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxytech.parkingcloud.commons.entity.WechatPaymentResultResponse;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.enums.CouponStatus;
import com.yxytech.parkingcloud.core.enums.OrderTransactionEnum;
import com.yxytech.parkingcloud.core.enums.TransactionPayWay;
import com.yxytech.parkingcloud.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class OrderInfoUtil {

    @Autowired
    private IOrderTransactionService orderTransactionService;

    @Autowired
    private IWhitelistService whitelistService;

    @Autowired
    private IFreelistService freelistService;

    @Autowired
    private IBlacklistService blacklistService;

    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private IFeeBillingService feeBillingService;

    @Autowired
    private IOrderParkingInfoService orderParkingInfoService;

    @Autowired
    private IOrderVoucherService orderVoucherService;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IWechatPaymentSettingService wechatPaymentSettingService;

    @Autowired
    private ICouponHistoryService couponHistoryService;

    @Autowired
    private IPromoCodeService promoCodeService;

    private static final String requestUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
    private static final String refundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 离场或者更改入场/离场时间引起的金额的变化的更新
     * @Param orderInfo
     * @Param leaveAt
     */
    @Transactional
    public OrderInfo getAmountInfo(OrderInfo orderInfo, Date leaveAt, Boolean flag) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        List<FreeEntity> freeEntities = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        Date enterAt = orderInfo.getEnterAt();
        Long lastTransactionDate = null;
        Date lastTransaction = orderTransactionService.getLastTransactionDate(orderInfo.getId());
        if (lastTransaction != null) lastTransactionDate = lastTransaction.getTime();

        if (! flag) {
            if (! feeBillingService.isInFreeTime(orderInfo.getCarType(), orderInfo.getParkingId(), orderInfo.getEnterAt().getTime(),
                    leaveAt.getTime(), lastTransactionDate)) {
                orderInfo.setCalculateTime(orderInfo.getLastPaymentTime());
                return orderInfo;
            }
        } else {
            lastTransaction = null;
        }

        Wrapper<Whitelist> whitelistWrapper = new EntityWrapper<>();
        whitelistWrapper.where("plate_number = {0}", orderInfo.getPlateNumber())
                .and("parking_id = {0}", orderInfo.getParkingId())
                .and("plate_color = {0}", orderInfo.getPlateColor())
                .where("end_at >= {0}", leaveAt)
                .and("is_valid = TRUE");

        List<Whitelist> whitelists = whitelistService.selectList(whitelistWrapper);

        Boolean isInBlack = blacklistService.isInBlackList(orderInfo.getPlateNumber(),
                (Integer) orderInfo.getPlateColor().getValue(), orderInfo.getParkingId());

        if (whitelists != null && whitelists.size() > 0) {
            result = this.getFee(orderInfo.getCarType(), orderInfo.getParkingId(),
                    enterAt, leaveAt,
                    null, null, lastTransaction
            );

            orderInfo.setTotalAmount((Double) result.get("totalFee"));

            if (isInBlack) {
                // TODO 不进行免费
                orderInfo.setReceivableAmount((Double) result.get("totalFee"));

                return orderInfo;
            } else {
                // TODO 全部免费
                orderInfo.setReceivableAmount(0.0);
                orderInfo.setFreeAmount((Double) result.get("totalFee"));

                return orderInfo;
            }
        } else {
            Wrapper<Freelist> freelistWrapper = new EntityWrapper<>();
            freelistWrapper.where("plate_number = {0}", orderInfo.getPlateNumber())
                    .and("plate_color = {0}", orderInfo.getPlateColor())
                    .and("parking_id = {0}", orderInfo.getParkingId())
                    .and("end_at > {0}", enterAt)
                    .and("is_valid is TRUE")
                    .orderBy("end_at", false);

            // 一般来说都是一个，不会存在两个的情况
            List<Freelist> freelists = freelistService.selectList(freelistWrapper);

            if (freelists != null && freelists.size() > 0) {
                // 次数是否为0
                List<Map<String, Object>> freelistTimes = new ArrayList<>();
                List<Freelist> finalList = new ArrayList<>();
                Long maxFreelistId = 0L;

                for (Freelist f : freelists) {
                    if (f.getTotalTimes() != 0) {
                        if (f.getTotalTimes() > f.getUsedTimes()) {
                            if (f.getId() > maxFreelistId) {
                                maxFreelistId = f.getId();
                            }

                            Map<String, Object> map = new HashMap<String, Object>() {{
                                put("freeTimes", Math.toIntExact(f.getTotalTimes() - f.getUsedTimes()));
                                put("start", format.format(f.getStartedAt()));
                                put("end", format.format(f.getEndAt()));
                            }};

                            freelistTimes.add(map);
                        }
                    } else {
                        finalList.add(f);
                    }
                }

                List<Map<String, String>> freeLists = null;
                if (finalList.size() > 0) {
                    freeEntities.addAll(freelistService.getAllFreeTime(orderInfo, freelists, enterAt, leaveAt));

                    freeLists = new ArrayList<>();

                    if (freeEntities.size() > 0) {
                        for (FreeEntity tmp : freeEntities) {
                            freeLists.add(new HashMap<String, String>() {{
                                put("start", format.format(tmp.getStartedAt()));
                                put("end", format.format(tmp.getEndAt()));
                            }});
                        }
                    }
                }

                result = this.getFee(orderInfo.getCarType(), orderInfo.getParkingId(),
                        enterAt, leaveAt, freeLists, freelistTimes, lastTransaction);
                Map freeResult = this.getFee(orderInfo.getCarType(), orderInfo.getParkingId(), enterAt, leaveAt, null,
                        null, lastTransaction);

                Double total = 0.0;
                Double need = 0.0;

                Integer usedTimes = (Integer) result.get("lastUsedTimes");
                if (flag && usedTimes != null && !usedTimes.equals(0)) {
                    // 更新
                    freelistService.updateForUsedTimes(maxFreelistId, usedTimes);
                }

                if (freelistTimes.size() > 0) {
                    total = (Double) result.get("needFee");
                    need = (Double) result.get("totalFee");

                    if (total.equals(0D)) {
                        total = need;
                    }
                } else {
                    total = (Double) freeResult.get("totalFee");
                    need = (Double) result.get("totalFee");
                }

                Double free = total - need;

                orderInfo.setTotalAmount(total);
                orderInfo.setReceivableAmount(need);
                orderInfo.setFreeAmount(free);

                return orderInfo;
            }

            // TODO 全部收费
            result = this.getFee(orderInfo.getCarType(), orderInfo.getParkingId(),
                    enterAt, leaveAt,
                    null, null, lastTransaction
            );

            orderInfo.setFreeAmount(0D);
            orderInfo.setTotalAmount((Double) result.get("totalFee"));
            orderInfo.setReceivableAmount((Double) result.get("totalFee"));
        }

        return orderInfo;
    }

    /**
     * 更新订单信息
     * @param id
     * @param orderInfo
     * @param orderParkingInfo
     * @param orderVouchers
     * @throws Exception
     */
    public void updateOrder(Long id, OrderInfo orderInfo, OrderParkingInfo orderParkingInfo,
                             List<OrderVoucher> orderVouchers) throws Exception {
        if (orderInfo.getId() == null) {
            OrderInfo orderInfoResult = orderInfoService.selectById(id);


            if (orderInfoResult == null || (! orderInfoResult.getValid())) {
                throw new Exception("非法的订单信息!");
            }
        }

        if (orderInfo != null) {
            orderInfo.setId(id);
            orderInfoService.updateById(orderInfo);
        }

        if (orderParkingInfo != null) {
            orderParkingInfo.setOrderId(id);
            Wrapper<OrderParkingInfo> orderParkingInfoWrapper = new EntityWrapper<>();
            orderParkingInfoWrapper.eq("order_id", id);

            OrderParkingInfo orderParkingInfoResult = orderParkingInfoService.selectOne(orderParkingInfoWrapper);

            if (orderParkingInfoResult == null) {
                orderParkingInfoService.insert(orderParkingInfo);
            } else {
                orderParkingInfoService.updateById(orderParkingInfo);
            }
        }

        if (orderVouchers != null) {
            List<OrderVoucher> orderVoucherList = new ArrayList<>();

            for (OrderVoucher orderVoucher : orderVouchers) {
                orderVoucher.setOrderId(id);
                orderVoucherList.add(orderVoucher);
            }

            orderVoucherService.insertBatch(orderVoucherList);
        }
    }

    /**
     * 处理支付在未超时范围内的情况
     * @param transactionNumber
     * @param orderTransaction
     * @throws Exception
     */
    public void dealNotTimeOut(String transactionNumber, OrderTransaction orderTransaction) throws Exception {
        WechatPaymentResultResponse response = JaxbXMLUtil.xmlToBean(this.validOrderTransaction(transactionNumber, orderTransaction),
                WechatPaymentResultResponse.class);

        Boolean flag = false;
        if (response == null) {
            flag = true;
        } else {
            if (response.getReturn_code().equals("SUCCESS")) {
                if (! response.getTrade_state().equals("USERPAYING")) {
                    OrderInfo orderInfo = orderInfoService.selectById(orderTransaction.getOrderId());

                    orderTransaction = orderTransactionService.setInfoToOrderTransaction(response, orderTransaction, false);
                    orderTransactionService.updateById(orderTransaction);

                    if (! response.getTrade_state().equals("SUCCESS")) {
                        this.dealCouponInfo(orderInfo, orderTransaction, response);
                    } else {
                        orderInfo = orderInfoService.paymentSuccess(orderInfo, orderTransaction);
                        orderInfoService.updateById(orderInfo);
                    }
                } else {
                    flag = true;
                }
            } else {
                flag = true;
            }
        }

        if (flag) {
            throw new Exception("系统错误，轻稍后重试!");
        }
    }

    /**
     * 请求微信接口查看支付结果
     * @param transactionNumber
     * @param orderTransaction
     * @return
     * @throws IOException
     */
    public String validOrderTransaction(String transactionNumber, OrderTransaction orderTransaction) throws IOException {
        Map config = paymentService.getConfigInfo();
        WechatOrderQueryRequest request = new WechatOrderQueryRequest();
        request.setAppid((String) config.get("appId"));
        request.setMch_id((String) config.get("mchId"));
        request.setOut_trade_no(transactionNumber);
        request.setNonce_str(paymentService.generateString());
        String selfApiKey = (String) config.get("apiKey");

        Wrapper<WechatPaymentSetting> wechatPaymentSettingWrapper = new EntityWrapper<>();
        wechatPaymentSettingWrapper.eq("mch_id", orderTransaction.getMchId())
                .eq("parking_id", orderTransaction.getParkingId());
        WechatPaymentSetting setting = wechatPaymentSettingService.selectOne(wechatPaymentSettingWrapper);
        if (setting != null) {
            request.setAppid(setting.getAppId());
            request.setMch_id(setting.getMchId());
            selfApiKey = setting.getApiKey();
        }

        request.setSign(paymentService.getSign(request, selfApiKey));

        String requestParam = JaxbXMLUtil.beanToXML(request);

        return HttpRequest.postByXml(requestUrl, requestParam);
    }

    /**
     * 失败修改相关信息
     * @param orderTransaction
     * @param response
     */
    public void dealCouponInfo(OrderInfo orderInfo, OrderTransaction orderTransaction, WechatPaymentResultResponse response) {
        Wrapper<OrderTransaction> orderTransactionWrapper = new EntityWrapper<>();
        orderTransactionWrapper.eq("transaction_id", orderTransaction.getUuid())
                .eq("status", OrderTransactionEnum.SUCCESS);
        List<OrderTransaction> orderTransactions = orderTransactionService.selectList(orderTransactionWrapper);

        if (orderTransactions != null && orderInfo != null) {
            Double amount = 0.0;
            List<Long> couponIds = new ArrayList<>();
            List<String> transactionIds = new ArrayList<>();
            OrderTransaction transaction = new OrderTransaction();
            transaction.setStatus(OrderTransactionEnum.FAILED);
            transaction.setRemark(response.getTrade_state_desc());

            for (OrderTransaction orderTransactionTmp : orderTransactions) {
                amount += orderTransactionTmp.getAmount();
                couponIds.add(Long.valueOf(orderTransactionTmp.getTransactionDetail()));
                transactionIds.add(orderTransactionTmp.getUuid());
            }

            // 更新优惠码状态
            if (! couponIds.isEmpty()) {
                CouponHistory couponHistory = new CouponHistory();
                couponHistory.setStatus(CouponStatus.RECEIVED);
                Wrapper<CouponHistory> couponHistoryWrapper = new EntityWrapper<>();
                couponHistoryWrapper.in("promo_code_id", couponIds);
                couponHistoryService.update(couponHistory, couponHistoryWrapper);

                PromoCode promoCode = new PromoCode();
                promoCode.setStatus(CouponStatus.RECEIVED);
                Wrapper<PromoCode> promoCodeWrapper = new EntityWrapper<>();
                promoCodeWrapper.in("id", couponIds);
                promoCodeService.update(promoCode, promoCodeWrapper);

                // 更新订单信息
                orderInfo.setPaidAmount(orderInfo.getPaidAmount() - amount);
                orderInfoService.updateById(orderInfo);
            }

            // 更新优惠码交易状态
            Wrapper<OrderTransaction> transactionWrapper = new EntityWrapper<>();
            transactionWrapper.in("id", transactionIds);

            if (transactionIds.size() > 0) {
                orderTransactionService.update(transaction, transactionWrapper);
            }
        }
    }

    @Transactional
    public OrderInfo refundAmount(OrderInfo orderInfo) {
        Double money = formatAmount(orderInfo.getPaidAmount() - orderInfo.getReceivableAmount());
        Integer wechatMoney = (int) (money * 100);

        if (wechatMoney > 0) {
            Wrapper<OrderTransaction> wrapper = new EntityWrapper<>();
            wrapper.eq("order_id", orderInfo.getId())
                    .eq("pay_way", TransactionPayWay.WECHAT_PRE_PAY)
                    .eq("status", OrderTransactionEnum.SUCCESS);

            OrderTransaction orderTransaction = orderTransactionService.selectOne(wrapper);

            if (orderTransaction == null) {
                return orderInfo;
            }

            // 进行退款
            WechatPaymentSetting wechatPaymentSetting = wechatPaymentSettingService.getPaymentSetting(orderInfo.getParkingId());
            OrderTransaction transaction = orderTransactionService.setInfoToOrderTransaction(orderInfo, money, wechatPaymentSetting.getMchId(),
                    TransactionPayWay.WECHAT_REFUND);

            WechatRefundRequestForm form = this.setInfo(wechatMoney, wechatPaymentSetting, orderInfo, orderTransaction, transaction.getUuid());
            String request = JaxbXMLUtil.beanToXML(form);

            try {
                String response = HttpRequest.sslPost(wechatPaymentSetting.getCertificationPath(),
                        wechatPaymentSetting.getPassword(), refundUrl, request);
                WechatRefundResponseFrom responseForm = JaxbXMLUtil.xmlToBean(response, WechatRefundResponseFrom.class);

                if (responseForm == null) {
                    return orderInfo;
                }

                if (responseForm.getReturn_code().equals("SUCCESS") && responseForm.getResult_code().equals("SUCCESS")) {
                    // 更新订单信息及交易记录信息
                    Double decreaseAmount = responseForm.getRefund_fee() / 100.0;

                    orderInfo.setPaidAmount(formatAmount(orderInfo.getPaidAmount() - decreaseAmount));
                    orderInfo.setInvoiceAmount(formatAmount(orderInfo.getInvoiceAmount() - decreaseAmount));

                    transaction.setStatus(OrderTransactionEnum.SUCCESS);
                    transaction.setFinishedAt(new Date());
                    transaction.setTransactionDetail(response);
                    transaction.setRemark("退款成功");
                } else {
                    transaction.setStatus(OrderTransactionEnum.FAILED);
                    transaction.setFinishedAt(new Date());
                    transaction.setTransactionDetail(response);
                    transaction.setRemark("退款失败");
                }
            } catch(Exception e) {
                return orderInfo;
            } finally {
                orderTransactionService.insert(transaction);
            }
        }

        return orderInfo;
    }

    private WechatRefundRequestForm setInfo(Integer money, WechatPaymentSetting setting, OrderInfo orderInfo,
                                            OrderTransaction orderTransaction, String newTransactionId) {
        WechatRefundRequestForm form = new WechatRefundRequestForm();

        form.setAppid(setting.getAppId());
        form.setMch_id(setting.getMchId());
        form.setNonce_str(paymentService.generateString());
        form.setOut_trade_no(orderTransaction.getUuid());
        form.setOut_refund_no(newTransactionId);
        form.setRefund_fee(money);
        form.setTotal_fee((int) (orderTransaction.getAmount() * 100));

        form.setSign(paymentService.getSign(form, setting.getApiKey()));

        return form;
    }

    @Async
    Map<String, Object> getFee(CarTypeEnum vehicle,
                               Long parkingId,
                               Date enterTime,
                               Date leaveTime,
                               List<Map<String, String>> freeList,
                               List<Map<String, Object>> freeTimesList,
                               Date payTime) throws ParseException {
        if (freeTimesList != null && freeTimesList.size() <= 0)
            freeTimesList = null;

        if (payTime == null) {
            return feeBillingService.feeBilling(vehicle, parkingId, DateParserUtil.formatDate(enterTime),
                    DateParserUtil.formatDate(leaveTime),
                    freeList, freeTimesList, null);
        } else {
            return feeBillingService.feeBilling(vehicle, parkingId, DateParserUtil.formatDate(enterTime),
                    DateParserUtil.formatDate(leaveTime),
                    freeList, freeTimesList, DateParserUtil.formatDate(payTime));
        }
    }

    public static Double formatAmount(Double amount) {
        if (amount == null) {
            return null;
        }

        if (amount < 0) {
            amount = 0.0;
        }

        BigDecimal bd = new BigDecimal(amount);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }
}
