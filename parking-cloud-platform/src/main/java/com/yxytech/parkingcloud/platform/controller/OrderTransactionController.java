package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.OrderTransaction;
import com.yxytech.parkingcloud.core.entity.TransactionSuccess;
import com.yxytech.parkingcloud.core.enums.OrderTransactionEnum;
import com.yxytech.parkingcloud.core.service.IOrderTransactionService;
import com.yxytech.parkingcloud.core.service.ITransactionSuccessService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/orderTransaction")
public class OrderTransactionController extends BaseController {

    @Autowired
    private IOrderTransactionService orderTransactionService;

    @Autowired
    private ITransactionSuccessService transactionSuccessService;

    @GetMapping("")
    @Access(permissionName = "交易查询",permissionCode = "ORDER_TRANSACTION_QUERY",moduleCode = "transaction_manage")
    public ApiResponse index (@RequestParam(value = "orderNumber", required = false) String orderNumber,
                              @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(value = "size", required = false, defaultValue = pageSize) Integer size,
                              @RequestParam(value = "parkingId", required = false) Long parkingId,
                              @RequestParam(value = "start", required = false) String start,
                              @RequestParam(value = "end", required = false) String end,
                              @RequestParam(value = "plateNumber",required = false)String plateNumber,
                              @RequestParam(value = "status",required = false)Integer status) {
        Wrapper<OrderTransaction> orderTransactionWrapper = new EntityWrapper<>();
        orderTransactionWrapper.orderBy("id",true);
        Date startTime = null;
        Date endTime = null;

        Page<OrderTransaction> selectPage = new Page<>(page, size);

        try {
            startTime = DateParserUtil.parseDate(start, true);
            endTime = DateParserUtil.parseDate(end, false);
        } catch (Exception e) {
            return this.apiFail(e.getMessage());
        }

        orderTransactionWrapper
            .eq(StringUtils.isNotEmpty(orderNumber), "order_number", orderNumber)
            .eq(parkingId != null, "parking_id", parkingId)
            .eq(StringUtils.isNotBlank(plateNumber),"plate_number",plateNumber)
            .eq(status!=null,"status",status)
            .where(startTime != null, "created_at >= {0}", startTime)
            .where(endTime != null, "created_at <= {0}", endTime);

        return this.apiSuccess(orderTransactionService.selectPage(selectPage, orderTransactionWrapper));
    }

    @GetMapping("/success")
    public ApiResponse getAllSuccess(@RequestParam(value = "orderNumber", required = false) String orderNumber,
                                     @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", required = false, defaultValue = pageSize) Integer size,
                                     @RequestParam(value = "parkingId", required = false) Long parkingId,
                                     @RequestParam(value = "start", required = false) String start,
                                     @RequestParam(value = "end", required = false) String end) {
        Wrapper<TransactionSuccess> transactionSuccessEntityWrapper = new EntityWrapper<>();
        transactionSuccessEntityWrapper.orderBy("id",true);
        Date startTime = null;
        Date endTime = null;

        Page<TransactionSuccess> selectPage = new Page<>(page, size);

        try {
            startTime = DateParserUtil.parseDate(start, true);
            endTime = DateParserUtil.parseDate(end, false);
        } catch (Exception e) {
            return this.apiFail(e.getMessage());
        }

        transactionSuccessEntityWrapper
                .eq(StringUtils.isNotEmpty(orderNumber), "order_number", orderNumber)
                .eq(parkingId != null, "parking_id", parkingId)
                .where(startTime != null, "created_at >= {0}", startTime)
                .where(endTime != null, "created_at <= {0}", endTime);

        return this.apiSuccess(transactionSuccessService.selectPage(selectPage, transactionSuccessEntityWrapper));
    }

    @GetMapping("/payStatus")
    public ApiResponse allStatus(){
        List data = new ArrayList();
        for(OrderTransactionEnum orderTransactionEnum : OrderTransactionEnum.values()){
            Map<String,Object> map = new HashMap<>();
            map.put("id",orderTransactionEnum.getValue());
            map.put("name",orderTransactionEnum.getDesc());
            data.add(map);
        }

        return apiSuccess(data);
    }
}
