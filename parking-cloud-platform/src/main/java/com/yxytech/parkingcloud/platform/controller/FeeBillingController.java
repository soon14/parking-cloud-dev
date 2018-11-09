package com.yxytech.parkingcloud.platform.controller;

import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.service.IFeeBillingService;
import com.yxytech.parkingcloud.platform.form.BillForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cj
 * @since 2017-10-24
 */
@RestController
@Scope("prototype")
@RequestMapping("/fee")
public class FeeBillingController extends BaseController {

    @Autowired
    private IFeeBillingService billingService;

    Calendar calendar = Calendar.getInstance(Locale.CHINA);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
    SimpleDateFormat sdfHm = new SimpleDateFormat("HH:mm");
    static Long DAY_IN_MILLS = 24 * 60 * 60 * 1000l;

    /**
     * @return
     * @throws BindException;
     */
    @PostMapping("/billing")
    @ResponseBody
    public ApiResponse<Object> billing(@RequestBody BillForm form) throws BindException, ParseException {

        if(form.getEnterTime().compareTo(form.getLeaveTime()) >= 0){
            return this.apiFail("出场时间必须大于入场时间");
        }

        Map<String, Object> rs = billingService.feeBilling(
                form.getVehicle(),
                form.getParkingId(),
                form.getEnterTime(),
                form.getLeaveTime(),
                form.getFreeTimeList(),
                form.getFreeTimesList(), form.getPayTime());


        return this.apiSuccess(rs);
    }


}
