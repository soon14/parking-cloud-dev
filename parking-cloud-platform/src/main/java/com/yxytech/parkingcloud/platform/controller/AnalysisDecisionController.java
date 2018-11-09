package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingStatisticsInfo;
import com.yxytech.parkingcloud.core.entity.UserAccount;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.service.IParkingStatisticsInfoService;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.task.RedisService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/analysisDecision")
public class AnalysisDecisionController extends BaseController{

    @Autowired
    private IParkingStatisticsInfoService parkingStatisticsInfoService;

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private RedisService redisService;

    private final Map<Integer, String> paramsMap = new HashMap<Integer, String>() {{
        put(1, "getCellUtilization");
        put(2, "getCellReverseRate");
        put(3, "getCellUsage");
        put(4, "getGrossIncome");
    }};

    @GetMapping("/historyUseRate")
    @Access(permissionName = "车位使用率",permissionCode = "CELL_USE_RATE",moduleCode = "statictical_analysis")
    public ApiResponse<Object> selectHistoryUseRate(@RequestParam(value = "cycle",defaultValue = "",required = false) Integer cycle,
                                                    @RequestParam(value = "type", defaultValue = "",required = false) Integer type,
                                                    @RequestParam(value = "areaId",defaultValue = "",required = false) Integer areaId,
                                                    @RequestParam(value = "parkingId",defaultValue = "",required = false) Long parkingId,
                                                    @RequestParam(value = "time",defaultValue = "",required = false) String time,
                                                    @RequestParam(value = "startTime",defaultValue = "",required = false) String startTime,
                                                    @RequestParam(value = "endTime", defaultValue = "",required = false) String endTime
                                                    ) {
        //返回包含 time starttime endtime 的事件对象map
        Map<String, Date> map = null;

        try {
            map = parkingStatisticsInfoService.dateSearch(cycle, time ,startTime, endTime);
        } catch (Exception e) {
            return apiFail(e.getMessage());
        }


        Map<String, Object>datemap = this.delDate(cycle, map);
        if(!(Boolean) datemap.get("success")){
            return apiFail((String) datemap.get("message"));
        }

        EntityWrapper<Parking> ew = new EntityWrapper<>();

        ew.eq(areaId!=null,"street_area_id", areaId).setSqlSelect("id");

        List<Parking> parkings = parkingService.selectList(ew);


        List<Long> l1 = new ArrayList<>();
        if(parkings!=null){
            parkings = parkings.size()>10 ? parkings.subList(0,10) : parkings;
        }
        for (Parking parking:parkings) {
            l1.add(parking.getId());
        }

        EntityWrapper<ParkingStatisticsInfo> parkingStatisticsInfoEntityWrapper = new EntityWrapper<ParkingStatisticsInfo>();
        parkingStatisticsInfoEntityWrapper.where(map.get("timeDate")!=null, "yxy_parking_statistics_info.datetime = {0}", map.get("timeDate"));
        parkingStatisticsInfoEntityWrapper.where(map.get("startTimeDate")!=null,"yxy_parking_statistics_info.datetime >= {0}", map.get("startTimeDate"));
        parkingStatisticsInfoEntityWrapper.where(map.get("endTimeDate")!=null,"yxy_parking_statistics_info.datetime <= {0}", map.get("endTimeDate"));

        if(l1.isEmpty())
            return apiSuccess("查不到合适的数据");

        parkingStatisticsInfoEntityWrapper.in(parkingId == null && areaId != null,"parking_id", l1);
        parkingStatisticsInfoEntityWrapper.eq(parkingId != null,"parking_id", parkingId);

        List<ParkingStatisticsInfo> parkingStatisticsInfos = new ArrayList<ParkingStatisticsInfo>();
        //返回查询结果的list
        parkingStatisticsInfos = this.selectToList(cycle, parkingStatisticsInfoEntityWrapper, parkingStatisticsInfos);
        if(parkingStatisticsInfos.size() == 0){
            return apiFail("查不到合适的数据");
        }
        Integer top = 5;
        // 排序，通过循环搞成前台要的数据形式
        Object result = null;

        try {
            result = parkingStatisticsInfoService.sort(parkingStatisticsInfos, cycle, type, map.get("startTimeDate"), map.get("endTimeDate"), top);
        } catch (NotFoundException e) {
            return apiFail(e.getMessage());
        }
        return apiSuccess(result);
    }

    private Map<String, Object> delDate(Integer cycle, Map<String, Date> map)
    {
        Map<String, Object> tmpmap = new HashMap<>();
        if(cycle == 1){
            if(map.get("timeDate")==null){
                tmpmap.put("success", false);
                tmpmap.put("message", "实时查询，时间必传");
                return tmpmap;
            }
        }else{
            if(map.get("startTimeDate")==null || map.get("endTimeDate")==null){
                tmpmap.put("success", false);
                tmpmap.put("message", "起始时间，结束时间必传");
                return tmpmap;
            }
        }
        tmpmap.put("success", true);
        tmpmap.put("message", "");
        return tmpmap;
    }


    @GetMapping("/historyIncome")
    @Access(permissionName = "收益统计",permissionCode = "CELL_INCOME",moduleCode = "statictical_analysis")
    public ApiResponse<Object> selectHistoryIncome(
                                                    @RequestParam(value = "cycle",defaultValue = "",required = false) Integer cycle,
                                                    @RequestParam(value = "areaId",defaultValue = "",required = false) Integer areaId,
                                                    @RequestParam(value = "parkingId",defaultValue = "",required = false) Integer parkingId,
                                                    @RequestParam(value = "time",defaultValue = "",required = false) String time,
                                                    @RequestParam(value = "startTime",defaultValue = "",required = false) String startTime,
                                                    @RequestParam(value = "endTime", defaultValue = "",required = false) String endTime
                                                    )
    {
        //格式化时间
        Map<String, Date> map = null;
        try {
            map = parkingStatisticsInfoService.dateSearch(cycle, time ,startTime, endTime);
        } catch (Exception e) {
            return apiFail(e.getMessage());
        }

        Map<String, Object>datemap = this.delDate(cycle, map);
        if(!(Boolean) datemap.get("success")){
            return apiFail((String) datemap.get("message"));
        }

        EntityWrapper<Parking> ew = new EntityWrapper<>();
        ew.eq(areaId!=null,"street_area_id", areaId).setSqlSelect("id");
        List<Parking> parkings = parkingService.selectList(ew);
        List<Long> l1 = new ArrayList<>();
        if(parkings!=null){
            parkings = parkings.size()>10 ? parkings.subList(0,10) : parkings;
        }
        for (Parking parking:parkings) {
            l1.add(parking.getId());
        }

        EntityWrapper<ParkingStatisticsInfo> parkingStatisticsInfoEntityWrapper = new EntityWrapper<ParkingStatisticsInfo>();
        parkingStatisticsInfoEntityWrapper.where(map.get("timeDate")!=null, "yxy_parking_statistics_info.datetime = {0}", map.get("timeDate"));
        parkingStatisticsInfoEntityWrapper.where(map.get("startTimeDate")!=null,"yxy_parking_statistics_info.datetime >= {0}", map.get("startTimeDate"));
        parkingStatisticsInfoEntityWrapper.where(map.get("endTimeDate")!=null,"yxy_parking_statistics_info.datetime <= {0}", map.get("endTimeDate"));

        if(l1.isEmpty())
            return apiSuccess("查不到合适的数据");

        parkingStatisticsInfoEntityWrapper.in(parkingId == null && areaId != null,"parking_id", l1);
        parkingStatisticsInfoEntityWrapper.eq(parkingId != null,"parking_id", parkingId);

        //返回查询结果的list
        List<ParkingStatisticsInfo> parkingStatisticsInfos = new ArrayList<ParkingStatisticsInfo>();
        parkingStatisticsInfos = this.selectToList(cycle, parkingStatisticsInfoEntityWrapper, parkingStatisticsInfos);

        if(parkingStatisticsInfos.size() == 0){
            return apiFail("没有合适的数据");
        }

        Integer top = 5;
        Object result = null;
        try {
            result = parkingStatisticsInfoService.sortIncome(parkingStatisticsInfos,cycle,map.get("startTimeDate"), map.get("endTimeDate"), top);
        } catch (NotFoundException e) {
            return apiFail(e.getMessage());
        }
        return apiSuccess(result);
    }

    /**
     * 排行榜
     * @param orgId
     * @param type
     * @return
     */
    @GetMapping("/getTopTen")
    public ApiResponse getHistoryRank(@RequestParam(value = "org_id", required = false) Long orgId,
                                      @RequestParam(value = "type", required = false) Integer type) {
        String name = "";

        for (Map.Entry<Integer, String > tmp : paramsMap.entrySet()) {
            if (type.equals(tmp.getKey())) {
                name = tmp.getValue();
                break;
            }
        }

        if (orgId != null) {
            List<String> parkingIds = new ArrayList<>();

            Wrapper<Parking> parkingWrapper = new EntityWrapper<>();

            parkingWrapper.eq("operator_org_id", orgId);

            List<Parking> parkings = parkingService.selectList(parkingWrapper);

            for (Parking parking : parkings) {
                parkingIds.add("parking:" + parking.getId());
            }

            return this.apiSuccess(redisService.getData(parkingIds, name));
        } else {
            return this.apiSuccess(redisService.getData(null, name));
        }
    }

    /**
     * 返回数据对象
     * @param cycle
     * @param parkingStatisticsInfoEntityWrapper
     * @param parkingStatisticsInfos
     * @return
     */
    private List<ParkingStatisticsInfo> selectToList(Integer cycle,
                                               EntityWrapper<ParkingStatisticsInfo> parkingStatisticsInfoEntityWrapper,
                                               List<ParkingStatisticsInfo> parkingStatisticsInfos
                                               )
    {
        // 实时查询或按日期范围查询
        if(cycle == 1 || cycle == 2) {
            // 按日期范围查询
            if(cycle == 1) parkingStatisticsInfoEntityWrapper.where("yxy_parking_statistics_info.hour < 24");
            if (cycle == 2) parkingStatisticsInfoEntityWrapper.where("yxy_parking_statistics_info.hour = 24");
            parkingStatisticsInfos = parkingStatisticsInfoService.selectList(parkingStatisticsInfoEntityWrapper);
        }
        // 按月查询
        if(cycle == 3){
            parkingStatisticsInfoEntityWrapper.where("yxy_parking_statistics_info.hour = 25 ");
            parkingStatisticsInfos = parkingStatisticsInfoService.selectByMonth(parkingStatisticsInfoEntityWrapper);
        }
        return parkingStatisticsInfos;
    }


    @GetMapping("/utlizationTop5")
    @Access(permissionName = "当日统计信息",permissionCode = "SAME_DAY_INFORMATION",moduleCode = "index_manage")
    public ApiResponse<Object> useRateTopFive()
    {

        //按天查询，top5
        //EntityWrapper<ParkingStatisticsInfo> parkingStatisticsInfoEntityWrapper = new EntityWrapper<>();
        //parkingStatisticsInfoEntityWrapper.where("yxy_parking_statistics_info.hour < 24");
        Date dateTime = new Date();
        List<ParkingStatisticsInfo> parkingStatisticsInfos = parkingStatisticsInfoService.selectTopList(dateTime);
        if(parkingStatisticsInfos.size() == 0){
            return apiFail("没有数据");
        }
        return apiSuccess(parkingStatisticsInfos);
    }

    /**
     * 按总收入排名前五
     * @return
     */
    @GetMapping("/topFive")
    public ApiResponse<Object> incomeTopFive(){
        List<ParkingStatisticsInfo> parkingStatisticsInfos = parkingStatisticsInfoService.incomeTop();
        if(parkingStatisticsInfos.size() == 0){
            return apiFail("没有数据");
        }
        return apiSuccess(parkingStatisticsInfos);
    }

}
