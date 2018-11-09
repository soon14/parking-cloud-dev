package com.yxytech.parkingcloud.platform.task;

import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.service.IOrderInfoService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SyncUsedParkingCells {

    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private IParkingService parkingService;

    @Scheduled(fixedDelay = 3600 * 1000L)
    public void statisticInUseParkingCells() {
        List<Map<String, Integer>> ret = orderInfoService.selectInUseParkingCells();

        parkingService.syncParkingCellUsedCount(ret);
    }
}
