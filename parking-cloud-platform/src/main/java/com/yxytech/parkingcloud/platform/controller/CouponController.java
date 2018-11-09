package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.CouponHistory;
import com.yxytech.parkingcloud.core.entity.CouponInfo;
import com.yxytech.parkingcloud.core.entity.PromoCode;
import com.yxytech.parkingcloud.core.enums.CouponEnum;
import com.yxytech.parkingcloud.core.enums.CouponStatus;
import com.yxytech.parkingcloud.core.service.ICouponHistoryService;
import com.yxytech.parkingcloud.core.service.ICouponInfoService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.service.IPromoCodeService;
import com.yxytech.parkingcloud.core.utils.UniqueCode;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.CouponForm;
import com.yxytech.parkingcloud.platform.form.GiveCouponForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/coupon")
public class CouponController extends BaseController {

    @Autowired
    private ICouponInfoService couponInfoService;

    @Autowired
    private ICouponHistoryService couponHistoryService;

    @Autowired
    private IPromoCodeService promoCodeService;

    @Autowired
    private IParkingService parkingService;

    @GetMapping("")
    public ApiResponse index(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                             @RequestParam(value = "size", defaultValue = pageSize, required = false) Integer size,
                             @RequestParam(value = "org_id", required = false) Long orgId) {
        Page<CouponInfo> pageObj = new Page<>(page, size);
        Wrapper<CouponInfo> couponInfoWrapper = new EntityWrapper<>();
        couponInfoWrapper.orderBy("id",true);

        Set<Long> parkingIds = new HashSet<>();


        couponInfoWrapper.eq(orgId != null, "organization_id", orgId);
        Page result = couponInfoService.selectPage(pageObj, couponInfoWrapper);

        if (result == null || result.getRecords() == null) {
            return this.apiSuccess(new HashMap<String, Object>() {{
                put("parkings", new HashMap<>());
                put("coupons", result);
            }});
        }

        for (CouponInfo v : (List<CouponInfo>) result.getRecords()) {
            parkingIds.add(v.getParkingId());
        }

        Map parkingNames = parkingService.selectIdNameMap(parkingIds, "id", "full_name");

        return this.apiSuccess(new HashMap<String, Object>() {{
            put("parkings", parkingNames);
            put("coupons", result);
        }});
    }

    @PostMapping("")
    @Transactional
    public ApiResponse create(@Valid @RequestBody CouponForm couponForm, BindingResult bindingResult) throws BindException {
        validate(bindingResult);

        if (couponForm.getCanSuperposed()) {
            if (couponForm.getMaxOfSuperposed() == null || couponForm.getMaxOfSuperposed() < 1) {
                return this.apiFail("最大叠加次数不能为空!");
            }
        }

        CouponInfo couponInfo = new CouponInfo();
        BeanUtils.copyProperties(couponForm, couponInfo);

        try {
            couponInfo.setCouponUnique(UniqueCode.generateUniqueCode((int) (Math.random() * 1024)));
            couponInfoService.insert(couponInfo);

            List<PromoCode> promoCodes = promoCodeService.createPromoCode(couponForm.getTimes(), couponInfo);

            promoCodeService.insertBatch(promoCodes, promoCodes.size());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return this.apiFail(e.getMessage());
        }

        return this.apiSuccess("");
    }

    @PostMapping("/giveCouponToSomeBody")
    @Transactional
    public ApiResponse giveCouponToSomeBody(@Valid @RequestBody GiveCouponForm couponForm, BindingResult bindingResult) throws BindException {
        validate(bindingResult);

        CouponInfo couponInfo = couponInfoService.selectById(couponForm.getCouponId());
        if (couponInfo == null) {
            return this.apiFail("非法的优惠券信息!");
        }

        if (! couponInfo.getValid() || couponInfo.getEndAt().compareTo(new Date()) < 0) {
            return this.apiFail("优惠券已过期!");
        }

        PromoCode promoCode = promoCodeService.selectById(couponForm.getPromoCodeId());
        if (promoCode == null) {
            return this.apiFail("非法的优惠码!");
        }

        // 插入
        CouponHistory couponHistory = new CouponHistory();
        couponHistory.setPromoCodeId(couponForm.getPromoCodeId());
        couponHistory.setReceivedBy(couponForm.getUserId());
        couponHistoryService.insert(couponHistory);

        promoCode.setStatus(CouponStatus.RECEIVED);
        promoCodeService.updateById(promoCode);

        return this.apiSuccess("");
    }

    @GetMapping("/promoCode/{id}")
    public ApiResponse getPromoCode(@PathVariable Long id,
                                    @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                    @RequestParam(value = "size", defaultValue = pageSize, required = false) Integer size) {
        Page<PromoCode> promoCodePage = new Page<>(page, size);

        Wrapper<PromoCode> promoCodeWrapper = new EntityWrapper<>();

        promoCodeWrapper.eq("coupon_id", id);

        return this.apiSuccess(promoCodeService.selectPage(promoCodePage, promoCodeWrapper));
    }

    @GetMapping("/type")
    public ApiResponse getCouponType() {
        ObjectMapper mapper = new ObjectMapper();

        Map<Object, String> map = new HashMap<>();

        for (CouponEnum couponEnum : CouponEnum.values()) {
            map.put(couponEnum.getValue(), couponEnum.getDesc());
        }

        return this.apiSuccess(map);
    }

    @GetMapping("/detail/{id}")
    public ApiResponse couponDetail(@PathVariable Long id) {
        CouponInfo couponInfo = couponInfoService.selectById(id);

        if (couponInfo == null) {
            return this.apiFail(null);
        }

        Map parking = parkingService.selectIdNameMap(new ArrayList<Long>() {{ add(couponInfo.getParkingId()); }}, "id", "full_name");

        return this.apiSuccess(new HashMap<String, Object>() {{
            put("couponInfo", couponInfo);
            put("parking", parking);
        }});
    }
}
