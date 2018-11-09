package com.yxytech.parkingcloud.platform.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.service.IParkingStatisticsInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class StatisticsUtil {
    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IParkingStatisticsInfoService parkingStatisticsInfoService;

    private List<Parking> getAllOrderInfoByRange(Date start, Date end) {
        Wrapper<OrderInfo> orderInfoWrapper = new EntityWrapper<>();
        orderInfoWrapper.where("yxy_order_info.enter_at < {0}", end)
                .where("yxy_order_info.leave_at >= {0}", start)
                .where("yxy_order_info.is_valid is TRUE")
                .where("yxy_parking.is_using is TRUE");

        return parkingService.customSelectOrderList(orderInfoWrapper);
    }
}
