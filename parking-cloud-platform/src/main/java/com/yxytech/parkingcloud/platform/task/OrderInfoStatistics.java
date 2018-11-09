package com.yxytech.parkingcloud.platform.task;

import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingStatisticsInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderInfoStatistics {
    public static Map<Long, ParkingStatisticsInfo> statistics(List<Parking> orderInfos, Date start, Date end) {
        // 分别统计车位利用率，车位反转率，车位占用率
        Map<Long, RatioEntity> ratioEntities = new HashMap<>();

        for (Parking parking : orderInfos) {
            Long index = parking.getId();
            RatioEntity ratio = ratioEntities.get(index);

            if (ratio == null) {
                ratio = new RatioEntity();

                ratio.setParkingName(parking.getFullName());
                ratio.setParkingId(index);
                ratio.setCells(parking.getParkingCells());
                ratio.setInTimes(0);
                ratio.setOccupancy(0);
                ratio.setUsedTimeLength(0L);
            }

            if (parking.getOrderInfos() == null) {
                continue;
            }

            for (OrderInfo orderInfo : parking.getOrderInfos()) {
                ratio.setUsedTimeLength(ratio.getUsedTimeLength() + statisticsUsed(orderInfo, start, end));
                ratio.setInTimes(ratio.getInTimes() + usedTimes(orderInfo, end));
                ratio.setOccupancy(ratio.getOccupancy() + statisticsEnterTimes(orderInfo, start, end));
            }

            ratioEntities.put(index, ratio);
        }

        // 计算占用率，反转率，利用率
        Map<Long, ParkingStatisticsInfo> parkingStatisticsInfos = new HashMap<>();
        // 时间间隔
        Long timespan = Math.round((double) (end.getTime() - start.getTime()) / (3600 * 1000));

        for (RatioEntity ratioEntity : ratioEntities.values()) {
            // 分母
            Double allCell = ratioEntity.getCells() * Double.valueOf(timespan);
            ParkingStatisticsInfo parkingStatisticsInfo = new ParkingStatisticsInfo();

            parkingStatisticsInfo.setParkingId(ratioEntity.getParkingId());
            parkingStatisticsInfo.setParkingName(ratioEntity.getParkingName());
            parkingStatisticsInfo.setCellCount(Long.valueOf(ratioEntity.getCells()));
            parkingStatisticsInfo.setCellUsedTimeLength(ratioEntity.getUsedTimeLength());

            if (allCell.equals(0D)) {
                allCell = 1D * timespan;
            }

            // 利用率
            parkingStatisticsInfo.setCellUtilization(ratioEntity.getUsedTimeLength() / (allCell * 3600 * 1000));
            // 反转率
            parkingStatisticsInfo.setCellReverseRate(ratioEntity.getOccupancy() / allCell);
            // 占用率
            parkingStatisticsInfo.setCellUsage(ratioEntity.getInTimes() / allCell);

            parkingStatisticsInfos.put(ratioEntity.getParkingId(), parkingStatisticsInfo);
        }

        return parkingStatisticsInfos;
    }

    /**
     * 统计利用率
     * @param orderInfo
     * @param start
     * @param end
     * @return
     */
    private static Long statisticsUsed(OrderInfo orderInfo, Date start, Date end) {
        if (orderInfo == null) {
            return 0L;
        }

        Date startTime = start;
        Date endTime = end;

        if (orderInfo.getEnterAt().compareTo(start) > 0) {
            startTime = orderInfo.getEnterAt();
        }

        if (orderInfo.getLeaveAt() == null) {
            endTime = end;
        } else {
            if (orderInfo.getLeaveAt().compareTo(end) < 0) {
                endTime = orderInfo.getLeaveAt();
            }
        }


        return endTime.getTime() - startTime.getTime();
    }

    /**
     * 统计反转率
     * @param orderInfo
     * @param end
     * @return
     */
    private static Integer statisticsEnterTimes(OrderInfo orderInfo, Date start, Date end) {
        if (orderInfo == null) {
            return 0;
        }

        if (orderInfo.getEnterAt().compareTo(start) >= 0 && orderInfo.getEnterAt().compareTo(end) < 0) {
            return 1;
        }

        return 0;
    }

    /**
     * 统计占用率
     * @param orderInfo
     * @param end
     * @return
     */
    private static Integer usedTimes(OrderInfo orderInfo, Date end) {
        if (orderInfo == null) {
            return 0;
        }

        return orderInfo.getLeaveAt() == null || orderInfo.getLeaveAt().compareTo(end) >= 0 ? 1 : 0;
    }
}
