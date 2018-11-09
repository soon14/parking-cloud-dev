package com.yxytech.parkingcloud.platform.task;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.entity.OrderTransaction;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingStatisticsInfo;
import com.yxytech.parkingcloud.core.enums.OrderTransactionEnum;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.service.IParkingStatisticsInfoService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class ParkingInfoTask {

    protected static Logger logger = LogManager.getLogger("root");

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private IParkingStatisticsInfoService parkingStatisticsInfoService;

    /**
     * 利用率
     */
    private final String cellUtilization = "getCellUtilization";
    /**
     * 反转率
     */
    private final String cellReverseRate = "getCellReverseRate";
    /**
     * 占用率
     */
    private final String cellUsage = "getCellUsage";

    @Scheduled(cron = "0 0 * * * ?")
    public void timeStatistics() {
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();

        Integer hour = calendarEnd.get(Calendar.HOUR_OF_DAY);
        Date start = null;
        Date end = calendarEnd.getTime();

        if (hour == 0) {
            // 额外统计当天的
            calendarStart = calendarEnd;
            calendarStart.set(Calendar.DATE, calendarEnd.get(Calendar.DATE) - 1);

            start = calendarStart.getTime();

            // 统计当天
            logger.error("统计" + DateParserUtil.formatDate(start) + "开始");
            this.startStatistics(start, end, 24);

            // 统计之前的排行榜
//            this.statisticsHistoryRank();
        }

        calendarStart = this.formatStart(calendarStart, calendarEnd);
        start = calendarStart.getTime();
        hour = (hour == 0) ? 23 : hour - 1;

        if (hour.equals(23)) {
            calendarStart.set(Calendar.DATE, calendarStart.get(Calendar.DATE) - 1);
            calendarStart.set(Calendar.HOUR_OF_DAY, 23);
            start = calendarStart.getTime();
        }

        logger.error("统计" + DateParserUtil.formatDate(start) + "开始");
        this.startStatistics(start, end, hour);
    }

    @Scheduled(cron = "0 0 2 1 * ?")
    public void statisticsMonth() {
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();

        calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);

        Integer month = calendarStart.get(Calendar.MONTH);

        if (month == 0) {
            calendarStart.set(Calendar.YEAR, calendarEnd.get(Calendar.YEAR) - 1);
            calendarStart.set(Calendar.MONTH, 11);
        } else {
            calendarStart.set(Calendar.MONTH, calendarEnd.get(Calendar.MONTH) - 1);
        }

        this.startStatistics(calendarStart.getTime(), calendarEnd.getTime(), 25);
    }

    private void startStatistics(Date start, Date end, Integer hour) {
        // 启动两个线程池去分别完成各种比率的计算和金额的计算
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        // 各种比率
        Map<Long, ParkingStatisticsInfo> parkingStatisticsInfoMap = this.statisticsOrderInfo(threadPool, start, end);
        // 各种金额
        Map<Long, ParkingStatisticsInfo> transactionStatisticsMap = this.statisticsTransaction(threadPool, start, end);

        threadPool.shutdown();

        while (true) {
            if (threadPool.isTerminated()) {
                Date date = hour == 25 ? end : start;

                this.merge(parkingStatisticsInfoMap, transactionStatisticsMap, hour, date);

                break;
            }
        }
    }

    /**
     * 格式化开始时间
     * @param calendarStart
     * @param calendarEnd
     * @return
     */
    private Calendar formatStart(Calendar calendarStart, Calendar calendarEnd) {
        Date date = new Date();

        calendarStart.setTime(date);
        calendarEnd.setTime(date);

        Integer hour = calendarEnd.get(Calendar.HOUR_OF_DAY);

        if (hour == 0) {
            // 统计前一天23:00 - 24:00的订单，并完成当天信息的统计
            calendarStart.set(Calendar.DAY_OF_MONTH, calendarEnd.get(Calendar.DAY_OF_MONTH));
            calendarStart.set(Calendar.HOUR_OF_DAY, 23);
            calendarStart.set(Calendar.MINUTE, 0);
            calendarStart.set(Calendar.SECOND, 0);
        } else {
            calendarStart.set(Calendar.HOUR_OF_DAY, hour - 1);
            calendarStart.set(Calendar.MINUTE, 0);
            calendarStart.set(Calendar.SECOND, 0);
        }

        return calendarStart;
    }

    /**
     * 排行榜统计
     */
    private void statisticsHistoryRank() {
        Calendar now = Calendar.getInstance();

        now.add(Calendar.DAY_OF_YEAR, -1);

        Wrapper<ParkingStatisticsInfo> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("min(datetime) AS datetime");

        Map<String, Object> minDate = parkingStatisticsInfoService.selectMap(wrapper);

        Date start = (Date) minDate.get("datetime");
        Long length = (now.getTimeInMillis() - start.getTime()) / (24 * 3600 * 1000L);
        length = length == 0 ? 1 : length;

        List<ParkingStatisticsInfo> list = parkingStatisticsInfoService.selectHistoryRank();

        for (int i = 0; i < list.size(); i++) {
            if (length > 0) {
                ParkingStatisticsInfo info = list.get(i);

                info.setCellUtilization(info.getCellUtilization() / length);
                info.setCellReverseRate(info.getCellReverseRate() / length);
                info.setCellUsage(info.getCellUsage() / length);

                list.set(i, info);
            }
        }

        redisService.setRankList(list, cellUtilization);
        redisService.setRankList(list, cellReverseRate);
        redisService.setRankList(list, cellUsage);
        redisService.setData(list);
    }

    /**
     * 统计交易金额
     * @param threadPool
     * @param start
     * @param end
     * @return
     */
    private Map<Long, ParkingStatisticsInfo> statisticsTransaction(ExecutorService threadPool, Date start, Date end) {
        Wrapper<OrderTransaction> transactionWrapper = new EntityWrapper<>();

        transactionWrapper.eq("yxy_order_transaction.status", OrderTransactionEnum.SUCCESS)
                .where("finished_at >= {0} and finished_at < {1}", start, end);

        List<Parking> transactionList = parkingService.customSelectTransactionList(transactionWrapper);
        Method method = null;

        try {
            method = OrderTransactionStatistics.class.getMethod("statistics", List.class, Date.class, Date.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        StatisticsCallable<Parking, ParkingStatisticsInfo> transactionStatisticsCallable =
                new StatisticsCallable<Parking, ParkingStatisticsInfo>(transactionList, method, start, end);

        Future<Map<Long, ParkingStatisticsInfo>> o = threadPool.submit(transactionStatisticsCallable);

        try {
            return o.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 订单信息统计
     * @param threadPool
     * @param start
     * @param end
     * @return
     */
    private Map<Long, ParkingStatisticsInfo> statisticsOrderInfo(ExecutorService threadPool, Date start, Date end) {
        Wrapper<OrderInfo> orderInfoWrapper = new EntityWrapper<>();

        orderInfoWrapper.where("is_valid is not FALSE")
                .where("(leave_at is null) OR (leave_at >= {0} And leave_at < {1})", start, end);

        List<Parking> orderInfos = parkingService.customSelectOrderList(orderInfoWrapper);
        Method method = null;

        try {
            method = OrderInfoStatistics.class.getMethod("statistics", List.class, Date.class, Date.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        StatisticsCallable<Parking, ParkingStatisticsInfo> orderInfoStatisticsCallable =
                new StatisticsCallable<Parking, ParkingStatisticsInfo>(orderInfos, method, start, end);

        Future<Map<Long, ParkingStatisticsInfo>> o = threadPool.submit(orderInfoStatisticsCallable);

        try {
            return o.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void merge(Map<Long, ParkingStatisticsInfo> parkingStatisticsInfoMap,
                       Map<Long, ParkingStatisticsInfo> transactionStatisticsMap,
                       Integer hour, Date date) {
        // 合并
        List<ParkingStatisticsInfo> parkingStatisticsInfos = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        Integer month = calendar.get(Calendar.MONTH) + 1;

        for (Long key : parkingStatisticsInfoMap.keySet()) {
            ParkingStatisticsInfo parkingStatisticsInfo = parkingStatisticsInfoMap.get(key);
            ParkingStatisticsInfo transactionInfo = transactionStatisticsMap.get(key);

            parkingStatisticsInfo.setGrossIncome(transactionInfo.getGrossIncome());
            parkingStatisticsInfo.setWechatIncome(transactionInfo.getWechatIncome());
            parkingStatisticsInfo.setAlipayIncome(transactionInfo.getAlipayIncome());
            parkingStatisticsInfo.setUnionPayIncome(transactionInfo.getUnionPayIncome());
            parkingStatisticsInfo.setOtherIncome(transactionInfo.getOtherIncome());
            parkingStatisticsInfo.setDiscountIncome(transactionInfo.getDiscountIncome());
            parkingStatisticsInfo.setHour(hour);
            parkingStatisticsInfo.setMonth(month);
            parkingStatisticsInfo.setDatetime(date);

            parkingStatisticsInfos.add(parkingStatisticsInfo);
        }

        logger.error("统计" + DateParserUtil.formatDate(date) + " " + hour + "结束, 结果有" + parkingStatisticsInfos.size() + "条! 结果: " + JSON.toJSONString(parkingStatisticsInfos));
        if (parkingStatisticsInfos.size() > 0) {
            parkingStatisticsInfoService.insertBatch(parkingStatisticsInfos, parkingStatisticsInfos.size());
        }
    }
}
