package com.yxytech.parkingcloud.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.app.entity.OrderInfoDetailResponse;
import com.yxytech.parkingcloud.app.entity.OrderInfoResponse;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.entity.OrderTransaction;
import com.yxytech.parkingcloud.core.enums.*;
import com.yxytech.parkingcloud.core.service.IOrderInfoService;
import com.yxytech.parkingcloud.core.service.IOrderTransactionService;
import com.yxytech.parkingcloud.core.utils.OrderInfoUtil;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private OrderInfoUtil orderInfoUtil;

    @Autowired
    private IOrderTransactionService orderTransactionService;

    @GetMapping("/paymentState/{id}")
    public ApiResponse paymentState(@PathVariable Long id) {
        OrderInfo orderInfo = orderInfoService.getDetail(id);

        if (orderInfo.getLastPaymentTime() != null) {
            OrderTransaction orderTransaction = orderTransactionService.getLastPaymentTransaction(orderInfo.getId(),
                    TransactionPayWay.WECHAT);

            if (orderTransaction == null) {
                orderTransaction = orderTransactionService.getLastPaymentTransaction(orderInfo.getId(),
                        TransactionPayWay.WECHAT_PRE_PAY);
            }

            if (orderTransaction != null) {
                try {
                    orderInfoUtil.dealNotTimeOut(orderTransaction.getUuid(), orderTransaction);

                    if (orderTransaction.getStatus().equals(OrderTransactionEnum.SUCCESS)) {
                        return this.apiSuccess(null);
                    } else {
                        return this.apiFail("支付失败!");
                    }
                } catch (Exception e) {
                    return this.apiFail("获取支付状态失败: " + e.getMessage());
                }
            } else {
                return this.apiSuccess(null);
            }
        }

        return this.apiFail("访问出错!");
    }

    @GetMapping("/detail/{id}")
    public ApiResponse detail(@PathVariable Long id) {
        try {
            OrderInfo orderInfo = orderInfoService.getDetail(id);
            Date date = new Date();

            if (orderInfo == null) {
                return this.apiFail("非法访问!");
            }

            if (orderInfo.getStatus().equals(OrderStatusEnum.CREATED)) {
                orderInfo = orderInfoUtil.getAmountInfo(orderInfo, date, false);
            }

            orderInfo.setCalculateTime(date);

            OrderInfoDetailResponse orderInfoDetailResponse = new OrderInfoDetailResponse();
            BeanUtils.copyProperties(orderInfo, orderInfoDetailResponse);
            BeanUtils.copyProperties(orderInfo.getParkingInfo(), orderInfoDetailResponse, "id");

            // fix 应付 = 应付 - 实付
            orderInfoDetailResponse.setReceivableAmount(
                    OrderInfoUtil.formatAmount(orderInfoDetailResponse.getReceivableAmount() - orderInfoDetailResponse.getPaidAmount())
            );

            return this.apiSuccess(orderInfoDetailResponse);
        } catch (Exception e) {
            return this.apiFail("获取订单详情失败!");
        }
    }

    @PostMapping("/search")
    public ApiResponse searchOrder(@RequestBody OrderInfo orderInfo) throws NotFoundException {
        String plateNumber = orderInfo.getPlateNumber();
        ColorsEnum plateColor = orderInfo.getPlateColor();

        notFound(plateNumber, "车牌号码必须填写!");

        Wrapper<OrderInfo> orderInfoWrapper = new EntityWrapper<>();
        orderInfoWrapper.eq("plate_number", plateNumber)
            .eq("is_valid", true)
            .eq("car_status", OrderCarStatusType.PARKING)
            .eq("status", OrderStatusEnum.CREATED);
        List<OrderInfo> orderInfos = orderInfoService.selectList(orderInfoWrapper);
        try {
            return this.apiSuccess(this.getResponseList(orderInfos, new Date(), true));
        } catch (ParseException e) {
            return this.apiFail("获取出错: " + e.getMessage());
        }
    }

    @GetMapping("")
    public ApiResponse index(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                             @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
                             @RequestParam(value = "status", required = false) Integer status) {
        Page<OrderInfo> orderInfoPage = new Page<>(page, size);

        Wrapper<OrderInfo> orderInfoWrapper = new EntityWrapper<>();

        orderInfoWrapper.eq("user_id", getCurrentUser().getId())
                .eq("is_valid", true)
                .eq(status != null, "status", status)
                .orderBy("created_at", false);

        Page<OrderInfo> result = orderInfoService.selectPage(orderInfoPage, orderInfoWrapper);
        Page<OrderInfoResponse> responsePage = new Page<>();
        List<OrderInfo> list = result.getRecords();
        Date date = new Date();

        try {
            List<OrderInfoResponse> responseList = this.getResponseList(list, date, true);

            BeanUtils.copyProperties(result, responsePage);
            responsePage.setRecords(responseList);

            return this.apiSuccess(responsePage);
        } catch (ParseException e) {
            return this.apiFail("获取出错: " + e.getMessage());
        }
    }

    private List<OrderInfoResponse> getResponseList(List<OrderInfo> orderInfos, Date date, Boolean flag) throws ParseException {
        List<OrderInfoResponse> responseList = new ArrayList<>();

        for (OrderInfo orderInfo : orderInfos) {
            OrderInfoResponse response = new OrderInfoResponse();

            if (flag && orderInfo.getStatus().equals(OrderStatusEnum.CREATED)) {
                if (orderInfo.getLeaveAt() != null) date = orderInfo.getLeaveAt();

                orderInfo = orderInfoUtil.getAmountInfo(orderInfo, date, false);
            }

            BeanUtils.copyProperties(orderInfo, response);
            response.setReceivableAmount(OrderInfoUtil.formatAmount(orderInfo.getReceivableAmount() - orderInfo.getPaidAmount()));
            responseList.add(response);
        }

        return responseList;
    }
}
