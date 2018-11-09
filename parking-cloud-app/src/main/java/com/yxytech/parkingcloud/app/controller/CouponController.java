package com.yxytech.parkingcloud.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.enums.CouponStatus;
import com.yxytech.parkingcloud.core.service.ICouponHistoryService;
import com.yxytech.parkingcloud.core.service.ICouponInfoService;
import com.yxytech.parkingcloud.core.service.IOrderInfoService;
import com.yxytech.parkingcloud.core.service.IPromoCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController extends BaseController {
    @Autowired
    private ICouponInfoService couponInfoService;

    @Autowired
    private IPromoCodeService promoCodeService;

    @Autowired
    private ICouponHistoryService couponHistoryService;

    @Autowired
    private IOrderInfoService orderInfoService;

    @GetMapping("")
    public ApiResponse index(@RequestParam(value = "status", required = false) CouponStatus status,
                             @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                             @RequestParam(value = "size", required = false, defaultValue = pageSize) Integer size) {
        Customer user = getCurrentUser();
        Page<CouponHistory> couponHistoryPage = new Page<>(page, size);
        Page<PromoCode> promoCodePage = new Page<>(page, size);

        Wrapper<CouponHistory> historyWrapper = new EntityWrapper<>();
        historyWrapper.eq("received_by", user.getId())
                .eq(status != null, "status", status);

        promoCodePage = this.getPromoCodeByStatus(historyWrapper, couponHistoryPage, promoCodePage);

        return this.apiSuccess(promoCodePage);
    }

    @GetMapping("/valid")
    public ApiResponse valid(@RequestParam(value = "orderId") Long orderId) {
        // 查询符合条件的
        Customer user = getCurrentUser();

        Wrapper<CouponHistory> historyWrapper = new EntityWrapper<>();

        historyWrapper.eq("received_by", user.getId())
                .eq("status", CouponStatus.RECEIVED);

        Page<PromoCode> promoCodePage = this.getPromoCodeByStatus(historyWrapper, null, null);

        if (promoCodePage == null) {
            return this.apiSuccess(this.nullToList(null));
        }

        OrderInfo orderInfo = orderInfoService.selectById(orderId);

        return this.apiSuccess(this.nullToList(this.getValidPromoCode(promoCodePage.getRecords(), orderInfo)));
    }

    @PostMapping("/{promoCode}")
    @Transactional
    public ApiResponse receivePromoCode(@PathVariable String promoCode) {
        Wrapper<PromoCode> promoCodeWrapper = new EntityWrapper<>();
        promoCodeWrapper.eq("promo_code", promoCode);
        PromoCode promoCodeResult = promoCodeService.selectOne(promoCodeWrapper);

        if (promoCodeResult == null) {
            return this.apiFail("非法操作!");
        }

        if (! promoCodeResult.getStatus().equals(CouponStatus.CREATED)) {
            return this.apiFail("已经被领取了!");
        }

        Date date = new Date();
        if (promoCodeResult.getReceiveEnd().compareTo(date) < 0 || promoCodeResult.getEndAt().compareTo(date) < 0) {
            return this.apiFail("已过期!");
        }

        CouponHistory couponHistory = new CouponHistory();
        Customer user = getCurrentUser();

        couponHistory.setReceivedBy(user.getId());
        couponHistory.setPromoCodeId(promoCodeResult.getId());
        couponHistory.setStatus(CouponStatus.RECEIVED);
        promoCodeResult.setStatus(CouponStatus.RECEIVED);

        couponHistoryService.insert(couponHistory);
        promoCodeService.updateById(promoCodeResult);

        return this.apiSuccess("ok");
    }

    private Page<PromoCode> getPromoCodeByStatus(Wrapper<CouponHistory> wrapper, Page<CouponHistory> page,
                                                 Page<PromoCode> promoCodePage) {
        List<CouponHistory> couponHistoryList = null;
        Page<CouponHistory> couponHistoryPage = null;

        if (page != null) {
            couponHistoryPage = couponHistoryService.selectPage(page, wrapper);
            couponHistoryList = couponHistoryPage.getRecords();
        } else {
            couponHistoryList = couponHistoryService.selectList(wrapper);
        }

        List<Long> promoCodeList = new ArrayList<>();

        couponHistoryList.forEach((CouponHistory c) -> {
            promoCodeList.add(c.getPromoCodeId());
        });

        if (promoCodeList.size() > 0) {
            if (couponHistoryPage != null) {
                BeanUtils.copyProperties(couponHistoryPage, promoCodePage);
            } else {
                promoCodePage = new Page<PromoCode>(1, 1);
            }

            List<PromoCode> promoCodes = promoCodeService.selectBatchIds(promoCodeList);
            promoCodePage.setRecords(promoCodes);
            return promoCodePage;
        }

        return null;
    }

    private List<PromoCode> getValidPromoCode(List<PromoCode> promoCodes, OrderInfo orderInfo) {
        if (promoCodes != null) {
            // 获取优惠券信息
            List<Long> couponIds = new ArrayList<>();

            promoCodes.forEach((PromoCode code) -> {
                if (! couponIds.contains(code.getCouponId())) {
                    couponIds.add(code.getCouponId());
                }
            });

            List<CouponInfo> couponInfos = couponInfoService.selectBatchIds(couponIds);
            Date date = new Date();

            couponInfos.forEach((CouponInfo info) -> {
                if (info.getEndAt().compareTo(date) < 0 || (! info.getParkingId().equals(orderInfo.getParkingId()))) {
                    couponIds.remove(info.getId());
                }
            });

            if (couponIds.size() == 0) {
                return null;
            }

            for (int i = 0; i < couponIds.size(); i++) {
                PromoCode promoCode = promoCodes.get(i);

                if (! couponIds.contains(promoCode.getCouponId())) {
                    promoCodes.remove(i);
                }
            }
        }

        return promoCodes;
    }

    private List<PromoCode> nullToList(List<PromoCode> list) {
        if (list == null) {
            return new ArrayList<>();
        }

        return list;
    }
}
