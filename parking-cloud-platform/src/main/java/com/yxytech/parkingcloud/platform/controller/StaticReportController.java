package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.ParkingAmountStatistics;
import com.yxytech.parkingcloud.core.entity.ParkingTimeLengthStaticForm;
import com.yxytech.parkingcloud.core.entity.ParkingTimeLengthStatistics;
import com.yxytech.parkingcloud.core.enums.StatisticsTimeLengthEnum;
import com.yxytech.parkingcloud.core.service.IParkingAmountStatisticsService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.service.IParkingTimeLengthStatisticsService;
import com.yxytech.parkingcloud.platform.config.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/staticReport")
public class StaticReportController extends BaseController{

    @Autowired
    private IParkingService iParkingService;

    @Autowired
    private IParkingAmountStatisticsService iParkingAmountStatisticsService;

    @Autowired
    private IParkingTimeLengthStatisticsService iParkingTimeLengthStatisticsService;


    /**
     * 停车点收费统计
     * @param type
     * @param areaId
     * @param parkingId
     * @param startTime
     * @param endTime
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/parkingLotCharge")
    @Access(permissionName = "停车点收费报表",permissionCode = "PARKING_LOT_CHARGE",moduleCode = "statictical_analysis")
    public ApiResponse<Object> parkingLotCharge(
                                                @RequestParam(value = "type", defaultValue = "", required = false) Integer type,
                                                @RequestParam(value = "areaId", defaultValue = "", required = false) Integer areaId,
                                                @RequestParam(value = "parkingId", defaultValue = "", required = false) Integer parkingId,
                                                @RequestParam(value = "startTime", defaultValue = "", required = false) String startTime,
                                                @RequestParam(value = "endTime", defaultValue = "", required = false) String endTime,
                                                @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                                @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    )
    {
        Page<ParkingAmountStatistics> p = new Page<>(page, size);
        Map<String, Object> searchMap = (Map<String, Object>)iParkingAmountStatisticsService.formatSearchDate(startTime, endTime);

        if(!(Boolean) searchMap.get("success")){
           return apiFail((String) searchMap.get("message"));
        }

        EntityWrapper<ParkingAmountStatistics> parkingAmountStatisticsEntityWrapper = new EntityWrapper<>();
        //按条件搜索
        parkingAmountStatisticsEntityWrapper = (EntityWrapper<ParkingAmountStatistics>)iParkingAmountStatisticsService.searchParkingLotCharge(type, areaId, parkingId, (Date) searchMap.get("startTimeDate"), (Date) searchMap.get("endTimeDate"));
        p = iParkingAmountStatisticsService.selectPage(p, parkingAmountStatisticsEntityWrapper);

        return apiSuccess(p);
    }


    /**
     * 停车时间统计
     * @param parkingTimeLengthStaticForm
     * @return
     */
    @PostMapping("parkingTime")
    @Access(permissionName = "停车报表",permissionCode = "PARKING_REPORT",moduleCode = "statictical_analysis")
    public ApiResponse<Object> parkingTime( @RequestBody ParkingTimeLengthStaticForm parkingTimeLengthStaticForm)
    {
        Page<ParkingTimeLengthStatistics> p = new Page<>();
        EntityWrapper<ParkingTimeLengthStatistics> parkingTimeLengthStatisticsEntityWrapper = new EntityWrapper<>();

//        return apiSuccess(parkingTimeLengthStatisticsEntityWrapper);

//        //按条件搜索
        parkingTimeLengthStatisticsEntityWrapper = (EntityWrapper<ParkingTimeLengthStatistics>)iParkingTimeLengthStatisticsService.formatDateSearch(parkingTimeLengthStaticForm);

//        return apiSuccess(parkingTimeLengthStatisticsEntityWrapper);
                p = iParkingTimeLengthStatisticsService.selectPage(p, parkingTimeLengthStatisticsEntityWrapper);
//
        return apiSuccess(p);
    }

    /**
     * 停车点筛选的枚举
     * @return
     */
    @GetMapping("/getShilterField")
    public ApiResponse<Object> shilterField()
    {
        //Map<String, String> map = new HashMap<>();
        List<Map<String,String>> maps = new ArrayList<>();
        //StatisticsTimeLengthEnum.values();
        for(StatisticsTimeLengthEnum statisticsTimeLengthEnum:StatisticsTimeLengthEnum.values()){
            Map<String, String> tmp = new HashMap<>();
            tmp.put("value", statisticsTimeLengthEnum.toString());
            tmp.put("label", statisticsTimeLengthEnum.getDesc());
            maps.add(tmp);
        }
        return apiSuccess(maps);
    }

}
