package com.yxytech.parkingcloud.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.app.entity.ParkingDotResult;
import com.yxytech.parkingcloud.app.form.CalculateAmountForm;
import com.yxytech.parkingcloud.app.form.ParkingInfoForm;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingCellUse;
import com.yxytech.parkingcloud.core.entity.ParkingInfoForApp;
import com.yxytech.parkingcloud.core.service.IFeeBillingService;
import com.yxytech.parkingcloud.core.service.IParkingCellUseService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.core.utils.OrderInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public/parking")
public class ParkingController extends BaseController {
    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IParkingCellUseService parkingCellUseService;

    @Autowired
    private IFeeBillingService feeBillingService;

    private static final Double radius = 0.01;

    @PostMapping("/nearParking")
    public ApiResponse search(@RequestBody ParkingInfoForm parkingInfoForApp) {
        Integer currentPage = 1;
        Integer size = 20;

        if (parkingInfoForApp.getPage() != null) {
            currentPage = parkingInfoForApp.getPage();
        }

        if (parkingInfoForApp.getSize() != null) {
            size = parkingInfoForApp.getSize();
        }

        Page<ParkingInfoForApp> page = new Page<>(currentPage, size);

        Page<ParkingInfoForApp> result = parkingService.getNearParkingPagination(page, parkingInfoForApp.getLongitude(),
                parkingInfoForApp.getLatitude(), radius);

        List<ParkingInfoForApp> list = result.getRecords();
        List<Long> parkingId = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            ParkingInfoForApp info = list.get(i);

            info.setUnUsedParkingCells(0);

            parkingId.add(info.getId());
            list.set(i, info);
        }

        List<ParkingCellUse> parkingCellUseList = parkingCellUseService.getInUseCells(parkingId);
        Map<Long, Integer> parkingCellUseMap = new HashMap<>();

        if (parkingCellUseList != null) {
            for (ParkingCellUse parkingCellUse : parkingCellUseList) {
                Integer usedCell = 0;

                if (parkingCellUse.getUsingNumber() != null) {
                    usedCell = parkingCellUse.getUsingNumber();
                }

                parkingCellUseMap.put(parkingCellUse.getParkingId(), usedCell);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            ParkingInfoForApp info = list.get(i);
            Integer usedCells = parkingCellUseMap.get(info.getId());

            usedCells = usedCells == null ? 0 : usedCells;

            info.setUnUsedParkingCells(info.getParkingCells() - usedCells);
            info.setParkingCells(usedCells);

            list.set(i, info);
        }

        result.setRecords(list);

        return this.apiSuccess(result);
    }

    @PostMapping("/calc")
    public ApiResponse calculateAmount(@Valid @RequestBody CalculateAmountForm calculateAmountForm, BindingResult br) throws BindException {
        validate(br);
        List<Map<String, String>> freeList = new ArrayList<>();
        List<Map<String, Object>> freeTimesList = new ArrayList<>();

        try {
            Map<String, Object> rs = feeBillingService.feeBilling(
                    calculateAmountForm.getCarType(),
                    calculateAmountForm.getParkingId(),
                    DateParserUtil.formatDate(calculateAmountForm.getEnterAt()),
                    DateParserUtil.formatDate(calculateAmountForm.getLeaveAt()),
                    freeList, freeTimesList, null);

            Map<String, Object> ret = new HashMap<>();
            ret.put("amount", OrderInfoUtil.formatAmount((Double) rs.get("totalFee")));

            return this.apiSuccess(ret);
        } catch (ParseException e) {
            return this.apiFail("计算失败: " + e.getMessage());
        }
    }

    @PostMapping("/dot")
    public ApiResponse getByDistance(@RequestBody ParkingInfoForm parkingInfoForm) {
        if (parkingInfoForm.getLeftTopLatitude() == null || parkingInfoForm.getLeftTopLongitude() == null) {
            return this.apiFail("左上角经纬度必须填写!");
        }

        Double customerRadius = parkingService.getRadius(parkingInfoForm.getLongitude(), parkingInfoForm.getLatitude(),
                parkingInfoForm.getLeftTopLongitude(), parkingInfoForm.getLeftTopLatitude());

        List<ParkingInfoForApp> list = parkingService.getNearParking(parkingInfoForm.getLongitude(),
                parkingInfoForm.getLatitude(), customerRadius);
        List<ParkingDotResult> resultList = new ArrayList<>();

        for (ParkingInfoForApp parkingInfoForApp : list) {
            ParkingDotResult parkingDotResult = new ParkingDotResult();

            parkingDotResult.setLatitude(parkingInfoForApp.getLatitude());
            parkingDotResult.setLongitude(parkingInfoForApp.getLongitude());

            resultList.add(parkingDotResult);
        }

        return this.apiSuccess(resultList);
    }

    @GetMapping("/detail/{id}")
    public ApiResponse detail(@PathVariable Long id) {
        Parking parkingInfo = parkingService.detail(id);

        if (parkingInfo == null) {
            return this.apiFail("不存在的停车场");
        }

        if (parkingInfo.getUsedCells() == null) {
            parkingInfo.setUnUsedCells(parkingInfo.getParkingCells());
            parkingInfo.setParkingCells(0);
        } else {
            parkingInfo.setUnUsedCells(parkingInfo.getParkingCells() - parkingInfo.getUsedCells());
            parkingInfo.setParkingCells(parkingInfo.getUsedCells());
        }

        return this.apiSuccess(parkingInfo);
    }
}
