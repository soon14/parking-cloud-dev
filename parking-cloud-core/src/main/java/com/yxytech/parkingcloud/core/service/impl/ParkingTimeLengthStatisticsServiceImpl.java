package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingTimeLengthStaticForm;
import com.yxytech.parkingcloud.core.entity.ParkingTimeLengthStatistics;
import com.yxytech.parkingcloud.core.mapper.ParkingTimeLengthStatisticsMapper;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.service.IParkingTimeLengthStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwd
 * @since 2017-11-15
 */
@Service
public class ParkingTimeLengthStatisticsServiceImpl extends ServiceImpl<ParkingTimeLengthStatisticsMapper, ParkingTimeLengthStatistics> implements IParkingTimeLengthStatisticsService {

    @Autowired
    private IParkingService iParkingService;

    public Object formatDateSearch(ParkingTimeLengthStaticForm parkingTimeLengthStaticForm)
    {
        EntityWrapper<ParkingTimeLengthStatistics> parkingTimeLengthStatisticsEntityWrapper = new EntityWrapper<>();
        //按天月年查询
        if(parkingTimeLengthStaticForm.getType() == 1){//按天统计
            parkingTimeLengthStatisticsEntityWrapper.eq("type", 1);
        }
        if(parkingTimeLengthStaticForm.getType() == 2){//按月统计
            parkingTimeLengthStatisticsEntityWrapper.eq("type",2);
        }
        if(parkingTimeLengthStaticForm.getType() == 3){//按年统计
            parkingTimeLengthStatisticsEntityWrapper.eq("type",3);
        }

        //按区域查询
        if(parkingTimeLengthStaticForm.getAreaId()!=null){
            EntityWrapper<Parking> parkingEntityWrapper = new EntityWrapper<>();
            parkingEntityWrapper.eq("street_area_id", parkingTimeLengthStaticForm.getAreaId()).setSqlSelect("id");
            List<Parking> parking = iParkingService.selectList(parkingEntityWrapper);
            List<Long> Pin = new ArrayList<>();
            for(Parking park:parking) {
                Pin.add(park.getId());
            }
            parkingTimeLengthStatisticsEntityWrapper.in("parking_id",Pin);
        }
        parkingTimeLengthStatisticsEntityWrapper.eq(parkingTimeLengthStaticForm.getParkingId()!=null, "parking_id", parkingTimeLengthStaticForm.getParkingId());
        parkingTimeLengthStatisticsEntityWrapper.where(parkingTimeLengthStaticForm.getStartTime()!=null, "datetime >= {0}", parkingTimeLengthStaticForm.getStartTime());
        parkingTimeLengthStatisticsEntityWrapper.where(parkingTimeLengthStaticForm.getEndTime()!=null, "datetime <= {0}", parkingTimeLengthStaticForm.getEndTime());

        return parkingTimeLengthStatisticsEntityWrapper;
    }

}
