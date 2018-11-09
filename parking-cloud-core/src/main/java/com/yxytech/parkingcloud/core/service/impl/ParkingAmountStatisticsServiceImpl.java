package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingAmountStatistics;
import com.yxytech.parkingcloud.core.mapper.ParkingAmountStatisticsMapper;
import com.yxytech.parkingcloud.core.service.IParkingAmountStatisticsService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwd
 * @since 2017-11-16
 */
@Service
public class ParkingAmountStatisticsServiceImpl extends ServiceImpl<ParkingAmountStatisticsMapper, ParkingAmountStatistics> implements IParkingAmountStatisticsService {

    @Autowired
    private IParkingService iParkingService;

    @Override
    public Object searchParkingLotCharge(Integer type, Integer areaId, Integer parkingId, Date startTimeDate, Date endTimeDate)
    {
        EntityWrapper<ParkingAmountStatistics> parkingAmountStatisticsEntityWrapper = new EntityWrapper<>();
        if(type == 0){//按天统计
            parkingAmountStatisticsEntityWrapper.eq("type", 0);
        }
        if(type == 1){//按月统计
            parkingAmountStatisticsEntityWrapper.eq("type",1);
        }
        if(type == 2){//按年统计
            parkingAmountStatisticsEntityWrapper.eq("type",2);
        }

        if(areaId!=null){
            EntityWrapper<Parking> parkingEntityWrapper = new EntityWrapper<>();
            parkingEntityWrapper.eq("street_area_id", areaId).setSqlSelect("id");
            List<Parking> parking = iParkingService.selectList(parkingEntityWrapper);
            List<Long> Pin = new ArrayList<>();
            for(Parking park:parking) {
                Pin.add(park.getId());
            }
            parkingAmountStatisticsEntityWrapper.in("parking_id",Pin);
        }
        parkingAmountStatisticsEntityWrapper.eq(parkingId!=null, "parking_id", parkingId);
        parkingAmountStatisticsEntityWrapper.where(startTimeDate!=null, "datetime >= {0}", startTimeDate);
        parkingAmountStatisticsEntityWrapper.where(endTimeDate!=null, "datetime <= {0}", endTimeDate);

        return parkingAmountStatisticsEntityWrapper;
    }

    @Override
    public Object formatSearchDate(String startTime, String endTime)
    {
        Date startTimeDate = null;
        Date endTimeDate = null;
        Map<String, Object> map = new HashMap<>();
        try {
            startTimeDate = DateParserUtil.parseDate(startTime,true);
            endTimeDate = DateParserUtil.parseDate(endTime,false);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        }

        if(startTimeDate == null || endTimeDate == null){
            map.put("success", false);
            map.put("message", "请选择起始时间和结束时间");
            return map;
        }
        map.put("success", true);
        map.put("message", "");
        map.put("startTimeDate", startTimeDate);
        map.put("endTimeDate", endTimeDate);
        return map;
    }

}
