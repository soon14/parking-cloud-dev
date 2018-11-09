package com.yxytech.parkingcloud.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxytech.parkingcloud.app.entity.WechatUnifyResponse;
import com.yxytech.parkingcloud.app.form.PaymentForm;
import com.yxytech.parkingcloud.app.form.PrePaymentForm;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.commons.entity.WechatRequest;
import com.yxytech.parkingcloud.commons.entity.WechatResponse;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.enums.*;
import com.yxytech.parkingcloud.core.exception.ServiceException;
import com.yxytech.parkingcloud.core.service.*;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.core.utils.HttpRequest;
import com.yxytech.parkingcloud.core.utils.JaxbXMLUtil;
import com.yxytech.parkingcloud.core.utils.OrderInfoUtil;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/payment")
public class PaymentController extends BaseController {
    @Autowired
    private IPaymentService wechatPaymentService;

    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private IWechatPaymentSettingService wechatPaymentSettingService;

    @Autowired
    private IOrderTransactionService orderTransactionService;

    @Autowired
    private IPromoCodeService promoCodeService;

    @Autowired
    private ICouponHistoryService couponHistoryService;

    @Autowired
    private ICouponInfoService couponInfoService;

    @Autowired
    private IFeeBillingService feeBillingService;

    @Autowired
    private OrderInfoUtil orderInfoUtil;

    @Value("${payment.wechat.notifyUrl}")
    private String notifyUrl;

    private String success = "SUCCESS";
    private static final Integer promoCodeTimes = 2;

    /**
     * 验证订单表单
     * @param paymentForm
     * @param orderInfo
     * @throws ServiceException
     * @throws NotFoundException
     */
    private void validPaymentForm(PaymentForm paymentForm, OrderInfo orderInfo) throws ServiceException, NotFoundException {
        if (orderInfo.getStatus().equals(OrderStatusEnum.FINISHED)) {
            throw new ServiceException(ApiResponse.COMMON_BIZ_ERROR, "订单已完成，不需要再进行支付!");
        }

        if (orderInfo.getCarStatus().equals(OrderCarStatusType.PARKING)) {
            notFound(orderInfo.getCarType(), "订单信息还未更新不能进行支付!");
            notFound(orderInfo.getEnterAt(), "非法订单信息!");
            notFound(paymentForm.getCalculateTime(), "计算费率时间必须传递!");
        }

        if (orderInfoService.isWithinTheUncertainty(paymentForm.getCalculateTime())) {
            throw new ServiceException(ApiResponse.PAY_REQUEST_TIMEOUT_ERROR, "订单金额可能已经发生变化，请重新进行计算订单金额!");
        }
    }

    /**
     * 调起支付
     * @param paymentForm
     * @param orderInfo
     * @param userId
     * @return
     * @throws Exception
     */
    private ApiResponse doPayment(PaymentForm paymentForm, OrderInfo orderInfo, Long userId, Date date) throws Exception {
        this.validPaymentForm(paymentForm, orderInfo);

        Long lastPaymentTime = null;
        if (orderInfo.getLastPaymentTime() != null) {
            lastPaymentTime = orderInfo.getLastPaymentTime().getTime();

            OrderTransaction orderTransaction = orderTransactionService.getLastPaymentTransaction(orderInfo.getId(),
                    TransactionPayWay.WECHAT);

            if (orderTransaction != null) {
                if (orderTransaction.getStatus().equals(OrderTransactionEnum.WAIT_FOR_PAY)) {
                    try {
                        orderInfoUtil.dealNotTimeOut(orderTransaction.getUuid(), orderTransaction);
                    } catch (Exception e) {
                        return this.apiFail(e.getMessage());
                    }
                }
            }
        }

        if (! feeBillingService.isInFreeTime(orderInfo.getCarType(), orderInfo.getParkingId(),
                orderInfo.getEnterAt().getTime(), date.getTime(),
                lastPaymentTime)) {
            if (orderInfo.getPaidAmount() >= orderInfo.getReceivableAmount()) {
                return this.apiFail("不需要再进行支付!");
            }
        }
        orderInfo = orderInfoUtil.getAmountInfo(orderInfo, date, false);

        WechatPaymentSetting wechatPaymentSetting = wechatPaymentSettingService.getPaymentSetting(orderInfo.getParkingId());
        OrderTransaction orderTransaction = orderTransactionService.setInfoToOrderTransaction(orderInfo, 0D,
                wechatPaymentSetting.getMchId(), TransactionPayWay.WECHAT, null, null);
        String uuid = orderTransaction.getUuid();
        this.getCouponMoney(paymentForm.getPromoCodes(), orderInfo, userId, uuid);
        Double shouldPayAmount = OrderInfoUtil.formatAmount(orderInfo.getReceivableAmount() - orderInfo.getPaidAmount());

        Integer money = (int) (shouldPayAmount * 100);

        if (money > 0) {
            orderInfo.setLastPaymentTime(date);
            orderInfoService.updateById(orderInfo);

            orderTransaction.setCreatedAt(date);
            orderTransaction.setAmount(shouldPayAmount);
            orderTransaction.setStatus(OrderTransactionEnum.WAIT_FOR_PAY);
            orderTransactionService.insert(orderTransaction);

            return this.unifiedPay(uuid, money, "停车费用", "停车费用", wechatPaymentSetting);
        }

        return this.apiFail("支付金额已够应付金额，不需要再进行支付!");
    }

    @PostMapping("/pay")
    @Transactional
    public ApiResponse payment(@Valid @RequestBody PaymentForm paymentForm, BindingResult bindingResult) throws Exception {
        validate(bindingResult);
        Customer user = getCurrentUser();

        OrderInfo orderInfo = null;
        try {
            orderInfo = this.validOrderInfo(paymentForm);
        } catch (Exception e) {
            return this.apiFail(e.getMessage());
        }

        this.validPaymentForm(paymentForm, orderInfo);

        try {
            return this.doPayment(paymentForm, orderInfo, user.getId(), paymentForm.getCalculateTime());
        } catch (Exception e) {
            return this.apiFail("统一下单失败: " + e.getMessage());
        }
    }

    @PostMapping("/prePay")
    @Transactional
    public ApiResponse prePay(@Valid @RequestBody PrePaymentForm form, BindingResult br) throws BindException {
        validate(br);

        Date date = new Date();
        Long userId = getCurrentUser().getId();
        OrderInfo orderInfo = orderInfoService.selectById(form.getOrderId());
        form.setAmount(form.getAmount() * 100);
        Integer amount = form.getAmount();

        if (amount <= 0) {
            return this.apiFail("请选择正确的金额!");
        }

        Double amountInDatabase = amount / 100.0;

        if (! orderInfo.getUserId().equals(userId)) {
            return this.apiFail("非法访问!");
        }

        if (! orderInfo.getPaidAmount().equals(0D)) {
            return this.apiFail("只能预支付一次!");
        }

        WechatPaymentSetting wechatPaymentSetting = wechatPaymentSettingService.getPaymentSetting(orderInfo.getParkingId());
        OrderTransaction orderTransaction = orderTransactionService.setInfoToOrderTransaction(orderInfo, amountInDatabase,
                wechatPaymentSetting.getMchId(), TransactionPayWay.WECHAT_PRE_PAY);
        String uuid = orderTransaction.getUuid();

        orderInfo.setLastPaymentTime(date);
        orderInfoService.updateById(orderInfo);

        orderTransaction.setCreatedAt(date);
        orderTransaction.setStatus(OrderTransactionEnum.WAIT_FOR_PAY);
        orderTransactionService.insert(orderTransaction);

        return this.unifiedPay(uuid, amount, "停车费用", "停车费用", wechatPaymentSetting);
    }

    @PostMapping("/couponPay")
    @Transactional
    public ApiResponse couponPay(@Valid @RequestBody PaymentForm paymentForm, BindingResult br) throws BindException {
        validate(br);

        Customer user = getCurrentUser();

        OrderInfo orderInfo = null;
        try {
            orderInfo = this.validOrderInfo(paymentForm);
            this.validPaymentForm(paymentForm, orderInfo);
        } catch (Exception e) {
            return this.apiFail(e.getMessage());
        }

        try {
            orderInfo = orderInfoUtil.getAmountInfo(orderInfo, paymentForm.getCalculateTime(), false);
            this.getCouponMoney(paymentForm.getPromoCodes(), orderInfo, user.getId(), "");
            Integer money = (int) Math.ceil((orderInfo.getReceivableAmount() - orderInfo.getPaidAmount()) * 100);

            if (money > 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return this.apiFail("需要支付金额大于优惠金额!");
            }

            return this.apiSuccess("");
        } catch (Exception e) {
            return this.apiFail(e.getMessage());
        }
    }

    @PostMapping("/couponMoney")
    public ApiResponse getCouponMoneyForApp(@Valid @RequestBody PaymentForm paymentForm, BindingResult br) throws BindException {
        validate(br);
        Long userId = getCurrentUser().getId();

        OrderInfo orderInfo = null;
        try {
            orderInfo = this.validOrderInfo(paymentForm);
            this.validPaymentForm(paymentForm, orderInfo);
        } catch (Exception e) {
            return this.apiFail(e.getMessage());
        }

        try {
            orderInfo = orderInfoUtil.getAmountInfo(orderInfo, paymentForm.getCalculateTime(), false);
            List<CouponInfo> couponInfoList = this.getCouponInfoList(paymentForm.getPromoCodes(), orderInfo, userId);
            Double discountAmount = this.getCouponMoneyOnly(couponInfoList, orderInfo);

            return this.apiSuccess(new HashMap<String, Double>() {{
                put("discountAmount", discountAmount);
            }});
        } catch (Exception e) {
            return this.apiFail(e.getMessage());
        }
    }

    @GetMapping("/transaction")
    public ApiResponse getTransaction(@RequestParam(name = "orderId") Long orderId) {
        OrderInfo orderInfo = orderInfoService.selectById(orderId);
        Customer customer = getCurrentUser();

        if (orderInfo == null) {
            return this.apiFail("非法操作!");
        }

        if (!orderInfo.getValid()) {
            return this.apiFail("无效的订单信息!");
        }

        Wrapper<OrderTransaction> orderTransactionWrapper = new EntityWrapper<>();

        orderTransactionWrapper.eq("user_id", customer.getId())
            .eq("order_number", orderInfo.getOrderNumber());


        List<OrderTransaction> orderTransactionList = orderTransactionService.selectList(orderTransactionWrapper);

        return this.apiSuccess(orderTransactionList);
    }

    /**
     * 验证订单
     * @param paymentForm
     * @return
     * @throws Exception
     */
    private OrderInfo validOrderInfo(PaymentForm paymentForm) throws Exception {
        OrderInfo orderInfo = orderInfoService.selectById(paymentForm.getOrderId());

        if (orderInfo == null || !orderInfo.getValid()) {
            throw new Exception("非法的订单!");
        }

        orderInfo.setCalculateTime(paymentForm.getCalculateTime());
        return orderInfo;
    }

    /**
     * 调起微信支付统一下单接口
     * @param uuid
     * @param money
     * @param attach
     * @param body
     * @param wechatPaymentSetting
     * @return
     */
    private ApiResponse unifiedPay(String uuid, Integer money, String attach, String body, WechatPaymentSetting wechatPaymentSetting) {
        WechatRequest wechatRequest = new WechatRequest();

        wechatRequest.setAppid(wechatPaymentSetting.getAppId());
        wechatRequest.setAttach(attach);
        wechatRequest.setBody(body);
        wechatRequest.setMch_id(wechatPaymentSetting.getMchId());
        wechatRequest.setNonce_str(wechatPaymentService.generateString());
        wechatRequest.setNotify_url(notifyUrl);
        wechatRequest.setOut_trade_no(uuid);
        wechatRequest.setSpbill_create_ip("14.23.150.211");
        wechatRequest.setTotal_fee(money);
        wechatRequest.setTrade_type("APP");

        String sign = wechatPaymentService.getSign(wechatRequest, wechatPaymentSetting.getApiKey());

        wechatRequest.setSign(sign);

        try {
            String response = HttpRequest.postByXml("https://api.mch.weixin.qq.com/pay/unifiedorder", JaxbXMLUtil.beanToXML(wechatRequest));

            WechatResponse wechatResponse = JaxbXMLUtil.xmlToBean(response, WechatResponse.class);

            if (wechatResponse == null) {
                return this.apiFail("统一下单失败!");
            }

            if (wechatResponse.getReturn_code().equals(success)) {
                WechatUnifyResponse unifyResponse = new WechatUnifyResponse();

                unifyResponse.setAppid(wechatResponse.getAppid());
                unifyResponse.setNoncestr(wechatPaymentService.generateString());
                unifyResponse.setPartnerid(wechatResponse.getMch_id());
                unifyResponse.setPrepayid(wechatResponse.getPrepay_id());
                unifyResponse.setTimestamp(new Date().getTime() / 1000);
                unifyResponse.setPackage("Sign=WXPay");

                sign = wechatPaymentService.getSign(unifyResponse, wechatPaymentSetting.getApiKey());

                unifyResponse.setSign(sign);

                return this.apiSuccess(unifyResponse);
            }

            return this.apiFail("统一下单失败: " + wechatResponse.getReturn_msg());
        } catch (IOException e) {
            return this.apiFail(e.getMessage());
        }
    }

    /**
     * 获取优惠券信息列表
     * @param promoCodes
     * @param orderInfo
     * @param userId
     * @return
     * @throws Exception
     */
    private List<CouponInfo> getCouponInfoList(List<Long> promoCodes, OrderInfo orderInfo, Long userId) throws Exception {
        if (promoCodes == null) {
            return null;
        }

        if (promoCodes.size() > promoCodeTimes) {
            throw new Exception("优惠码选择错误, 最大使用次数: " + promoCodeTimes + "次!");
        }

        if (promoCodes.size() == 0) {
            return null;
        }

        // 判断优惠码是否都属于该用户
        Wrapper<CouponHistory> couponHistoryWrapper = new EntityWrapper<>();
        couponHistoryWrapper.in("promo_code_id", promoCodes);
        List<CouponHistory> couponHistoryList = couponHistoryService.selectList(couponHistoryWrapper);
        if (couponHistoryList.size() != promoCodes.size()) {
            throw new Exception("优惠券状态错误!");
        }
        for (CouponHistory history : couponHistoryList) {
            if ((!history.getReceivedBy().equals(userId)) || (!history.getStatus().equals(CouponStatus.RECEIVED))) {
                throw new Exception("优惠券状态错误!");
            }
        }

        // 判断类型并且进行计算金额
        Wrapper<PromoCode> promoCodeWrapper = new EntityWrapper<>();
        promoCodeWrapper.in("yxy_promo_code.id", promoCodes);
        List<CouponInfo> couponInfoList = couponInfoService.getCouponByPromoCode(promoCodeWrapper);

        // 判断是否可以在当前时间用
        Date date = new Date();
        for (CouponInfo couponInfo : couponInfoList) {
            if (couponInfo.getEndAt().compareTo(date) < 0) {
                throw new Exception("优惠券选择错误，有过期的优惠券!");
            }

            if (! couponInfo.getParkingId().equals(orderInfo.getParkingId())) {
                throw new Exception("优惠券选择错误，在该停车场不能使用!");
            }
        }



        // 根据优惠券排序
        // 叠加次数判断
        Map<String, Integer> canSuperposedTimes = new HashMap<>();
        Map<String, Integer> superposedTimes = new HashMap<>();
        // 不能同时存在多个时长优惠券
        Boolean flag = true;
        Integer length = couponHistoryList.size();

        for (int i = 0; i < length; i++) {
            CouponInfo iCouponInfo = couponInfoList.get(i);
            // 判断和免费车同时使用
            if (! orderInfo.getFreeAmount().equals(0D) && iCouponInfo.getCanUseWithWhitelist().equals(false)) {
                throw new Exception("优惠券选择错误，不能和免费车同时使用!");
            }

            if (iCouponInfo.getEndAt().compareTo(date) < 0) {
                throw new Exception("优惠券选择错误，有过期的优惠券!");
            }

            if (! iCouponInfo.getParkingId().equals(orderInfo.getParkingId())) {
                throw new Exception("优惠券选择错误，在该停车场不能使用!");
            }

            if (iCouponInfo.getMinUseMoney().compareTo(0.0) > 0 &&
                    orderInfo.getReceivableAmount() < iCouponInfo.getMinUseMoney()) {
                throw new Exception("优惠券选择错误，订单未满足优惠券最少优惠金额!");
            }

            // 叠加次数的判断
            String key = iCouponInfo.getCouponUnique();
            Integer canSuperposedCount = ! iCouponInfo.getCanSuperposed() ? 1 : iCouponInfo.getMaxOfSuperposed();
            canSuperposedTimes.put(key, canSuperposedCount);
            Integer isuperposedTimes = superposedTimes.get(key);
            if (isuperposedTimes == null) {
                isuperposedTimes = 1;
            } else {
                isuperposedTimes++;
            }
            superposedTimes.put(key, isuperposedTimes);

            flag = flag && (iCouponInfo.getCouponType().equals(CouponEnum.TIME));

            for (int j = i + 1; j < length; j++) {
                CouponInfo jCouponInfo = couponInfoList.get(j);

                Integer iTypeValue = (Integer) iCouponInfo.getCouponType().getValue();
                Integer jTypeValue = (Integer) jCouponInfo.getCouponType().getValue();

                if (iTypeValue < jTypeValue) {
                    couponInfoList.set(i, jCouponInfo);
                    couponInfoList.set(j, iCouponInfo);
                }
            }
        }

        if (flag && promoCodes.size() > 1) {
            throw new Exception("优惠券不能都为时长优惠卡!");
        }

        for (Map.Entry<String, Integer> superposed : canSuperposedTimes.entrySet()) {
            Integer usedSuperposedTimes = superposedTimes.get(superposed.getKey());

            if (usedSuperposedTimes > superposed.getValue()) {
                throw new Exception("优惠券叠加次数出错!");
            }
        }

        return couponInfoList;
    }

    /**
     * 获取金额
     * @param couponInfoList
     * @param orderInfo
     * @return
     * @throws Exception
     */
    private Double getCouponMoneyOnly(List<CouponInfo> couponInfoList, OrderInfo orderInfo) throws Exception {
        Double discountAmount = 0.0;

        if (couponInfoList == null) {
            return discountAmount;
        }

        for (CouponInfo couponInfo : couponInfoList) {
            Double amount = this.calculateAmount(orderInfo, couponInfo);
            amount = OrderInfoUtil.formatAmount(this.getFinalDiscountAmount(orderInfo, amount));
            orderInfo.setPaidAmount(amount + orderInfo.getPaidAmount());

            discountAmount += amount;
        }

        orderInfo.setPaidAmount(orderInfo.getPaidAmount() - discountAmount);
        discountAmount = this.getFinalDiscountAmount(orderInfo, discountAmount);

        return discountAmount;
    }

    /**
     * 获取优惠码交易记录
     * @param couponInfos
     * @param orderInfo
     * @param amount
     * @return
     */
    private List<OrderTransaction> getCouponTransactionList(List<CouponInfo> couponInfos,
                                                            OrderInfo orderInfo, Double amount, String uuid) {
        List<OrderTransaction> orderTransactions = new ArrayList<>();

        if (couponInfos == null) {
            return orderTransactions;
        }

        Double discountAmount = 0.0;
        OrderInfo oldOrderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderInfo, oldOrderInfo);

        for (CouponInfo couponInfo : couponInfos) {
            amount = this.calculateAmount(orderInfo, couponInfo);
            amount = OrderInfoUtil.formatAmount(this.getFinalDiscountAmount(orderInfo, amount));
            orderInfo.setPaidAmount(amount + orderInfo.getPaidAmount());

            discountAmount += amount;

            orderTransactions.add(orderTransactionService.setInfoToOrderTransaction(oldOrderInfo, amount, null,
                    TransactionPayWay.COUPON, uuid, couponInfo.getPromoCode().getId().toString()));
        }

        orderInfo.setPaidAmount(orderInfo.getPaidAmount() - discountAmount);

        return orderTransactions;
    }

    /**
     * 计算优惠金额并更新相关信息
     * @param promoCodes
     * @param orderInfo
     * @param userId
     * @return
     * @throws Exception
     */
    private Double getCouponMoney(List<Long> promoCodes, OrderInfo orderInfo, Long userId, String uuid) throws Exception {
        List<CouponInfo> couponInfoList = this.getCouponInfoList(promoCodes, orderInfo, userId);
        Double discountAmount = this.getCouponMoneyOnly(couponInfoList, orderInfo);

        if (discountAmount == 0) {
            return discountAmount;
        }

        List<OrderTransaction> orderTransactions = this.getCouponTransactionList(couponInfoList, orderInfo,
                discountAmount, uuid);

        // 判断优惠码是否都属于该用户
        Wrapper<CouponHistory> couponHistoryWrapper = new EntityWrapper<>();
        couponHistoryWrapper.in("promo_code_id", promoCodes);

        // 判断类型并且进行计算金额
        Wrapper<PromoCode> promoCodeWrapper = new EntityWrapper<>();
        promoCodeWrapper.in("yxy_promo_code.id", promoCodes);

        try {
            orderTransactionService.insertBatch(orderTransactions, orderTransactions.size());

            // 修改优惠码的状态，及领取的状态
            CouponHistory couponHistory = new CouponHistory();
            couponHistory.setStatus(CouponStatus.USED);
            couponHistoryService.update(couponHistory, couponHistoryWrapper);

            PromoCode promoCode = new PromoCode();
            promoCode.setStatus(CouponStatus.USED);
            promoCodeService.update(promoCode, promoCodeWrapper);

            // 修改订单的已支付金额
            orderInfo.setPaidAmount(OrderInfoUtil.formatAmount(orderInfo.getPaidAmount() + discountAmount));
            orderInfoService.updateById(orderInfo);
        } catch (Exception e) {
            throw new Exception("交易记录记录失败!");
        }

        return discountAmount;
    }

    /**
     * 计算金额
     * @param orderInfo
     * @param couponInfo
     * @return
     */
    private Double calculateAmount(OrderInfo orderInfo, CouponInfo couponInfo) {
        CouponEnum type = couponInfo.getCouponType();
        Double discount = couponInfo.getCouponInfo();
        Double amount = OrderInfoUtil.formatAmount(orderInfo.getReceivableAmount() - orderInfo.getPaidAmount());

        if (type.equals(CouponEnum.TIME)) {
            Date start = orderInfo.getEnterAt();
            Long end = start.getTime() + Math.round(discount * 3600 * 1000);

            try {
                FeeRateEnum feeRate = feeBillingService.getFeeRate(orderInfo.getParkingId(), orderInfo.getEnterAt().getTime(), orderInfo.getCarType());

                if (feeRate.equals(FeeRateEnum.T)) {
                    return 0.0;
                }

                Map<String, Object> ret = feeBillingService.feeBilling(orderInfo.getCarType(), orderInfo.getParkingId(),
                        DateParserUtil.formatDate(start), DateParserUtil.formatDate(new Date(end)),
                        null, null, null);

                return (Double) ret.get("totalFee");
            } catch (ParseException e) {
                return 0.0;
            }
        } else if (type.equals(CouponEnum.DISCOUNT)) {
            Double discountMoney = amount * discount;

            Double canSuperposedMoney = couponInfo.getMaxSuperposedMoney();
            if (canSuperposedMoney != 0) {
                if (canSuperposedMoney < discountMoney) {
                    return canSuperposedMoney;
                }
            }

            return discountMoney;
        } else if (type.equals(CouponEnum.VOUCHER)) {
            return discount;
        }

        return 0.0;
    }

    private Double getFinalDiscountAmount(OrderInfo orderInfo, Double amount) {
        Double discountAmount = orderInfo.getReceivableAmount() - orderInfo.getPaidAmount();

        if (amount > discountAmount) {
            return discountAmount;
        }

        return amount;
    }
}
