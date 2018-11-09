package com.yxytech.parkingcloud.platform.task;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.enums.StatisticsTimeLengthEnum;
import com.yxytech.parkingcloud.core.enums.TransactionPayWay;
import com.yxytech.parkingcloud.core.service.IOrderTransactionService;
import com.yxytech.parkingcloud.core.service.IParkingAmountStatisticsService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.service.IParkingTimeLengthStatisticsService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Component
public class StatisticalReportTask {

    protected static Logger logger = LogManager.getLogger("root");

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IOrderTransactionService orderTransactionService;

    @Autowired
    private IParkingTimeLengthStatisticsService parkingTimeLengthStatisticsService;

    @Autowired
    private IParkingAmountStatisticsService parkingAmountStatisticsService;

    private static final Integer DAY = 0;
    private static final Integer MONTH = 1;

    @Scheduled(cron = "0 0 0 * * ?")
    public void dayStatistics() {
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();

        calendarStart.add(Calendar.DAY_OF_MONTH, -1);

        logger.error("报表统计" + DateParserUtil.formatDate(calendarStart.getTime()) + "开始!");
        this.statisticsTimeLength(calendarStart.getTime(), calendarEnd.getTime(), null);
        this.statisticsAmount(calendarStart.getTime(), calendarEnd.getTime(), null);
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void monthStatistics() {
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();

        Integer month = calendarStart.get(Calendar.MONTH);

        if (month == 0) {
            calendarStart.set(Calendar.YEAR, calendarEnd.get(Calendar.YEAR) - 1);
            calendarStart.set(Calendar.MONTH, 11);
        } else {
            calendarStart.set(Calendar.MONTH, calendarEnd.get(Calendar.MONTH) - 1);
        }

        Integer storeMonth = calendarStart.get(Calendar.MONTH) + 1;

        this.statisticsTimeLength(calendarStart.getTime(), calendarEnd.getTime(), storeMonth);
        this.statisticsAmount(calendarStart.getTime(), calendarEnd.getTime(), storeMonth);
    }

    /**
     * 时长统计
     * @param start
     * @param end
     */
    public void statisticsTimeLength(Date start, Date end, Integer month) {
        Integer type = DAY;

        if (month == null) {
            month = 0;
        } else {
            type = MONTH;
        }

        List<Parking> parkings = this.getParkingInfo(start, end);
        List<ParkingTimeLengthStatistics> result = new ArrayList<>();

        for (Parking parking : parkings) {
            ParkingTimeLengthStatistics parkingTimeLengthStatistics = new ParkingTimeLengthStatistics();

            parkingTimeLengthStatistics.setDatetime(start);
            parkingTimeLengthStatistics.setType(type);
            parkingTimeLengthStatistics.setMonth(month);
            parkingTimeLengthStatistics.setParkingId(parking.getId());
            parkingTimeLengthStatistics.setParkingName(parking.getFullName());
            Integer inTimes = 0;
            Integer outTimes = 0;

            for (OrderInfo orderInfo : parking.getOrderInfos()) {
                Long length = (orderInfo.getEnterAt().getTime() - orderInfo.getLeaveAt().getTime()) / 1000;

                if (orderInfo.getEnterAt().compareTo(start) >= 0) inTimes++;
                outTimes++;

                parkingTimeLengthStatistics = this.timeLengthStatistics(parkingTimeLengthStatistics, length);
            }

            parkingTimeLengthStatistics.setInTimes(inTimes);
            parkingTimeLengthStatistics.setOutTimes(outTimes);

            result.add(parkingTimeLengthStatistics);
        }

        logger.error("报表统计-停车时长统计结束" + DateParserUtil.formatDate(start) + "结束，结果有" + result.size() + "条! 结果: " + JSON.toJSONString(result));
        if (result.size() > 0) {
            parkingTimeLengthStatisticsService.insertBatch(result, result.size());
        }
    }

    /**
     * 金额统计
     * @param start
     * @param end
     */
    public void statisticsAmount(Date start, Date end, Integer month) {
        Integer type = DAY;

        if (month == null) {
            month = 0;
        } else {
            type = MONTH;
        }

        List<Parking> orderList = this.getParkingInfo(start, end);
        List<OrderTransaction> transactionList = this.getOrderTransaction(start, end);
        Map<Long, ParkingAmountStatistics> result = new HashMap<>();

        Map<Long, List<OrderTransaction>> transactions = this.formatDate(transactionList);

        // 处理除了预支付之外的支付
        for (Parking parking : orderList) {
            List<OrderInfo> list = parking.getOrderInfos();
            ParkingAmountStatistics amount = new ParkingAmountStatistics();

            amount.setDatetime(start);
            amount.setType(type);
            amount.setMonth(month);
            amount.setAlipayAmount(0.0);
            amount.setCashAmount(0.0);
            amount.setDiscountAmount(0.0);
            amount.setPaidAmount(0.0);
            amount.setReceivableAmount(0.0);
            amount.setUnpaidAmount(0.0);
            amount.setWechatAmount(0.0);
            amount.setPrePaidAmount(0.0);
            amount.setPrePaidRefundsAmount(0.0);
            amount.setParkingId(parking.getId());
            amount.setParkingName(parking.getFullName());

            for (OrderInfo orderInfo : list) {
                List<OrderTransaction> orderTransactions = transactions.get(orderInfo.getId());
                amount.setReceivableAmount(orderInfo.getReceivableAmount());

                if (orderTransactions == null) {
                    // 欠缴金额
                    amount.setUnpaidAmount(orderInfo.getReceivableAmount() - orderInfo.getPaidAmount());
                } else {
                    // 支付了的
                    for (OrderTransaction tmp : orderTransactions) {
                        this.statisticsAmount(amount, tmp);
                    }
                }

                transactions.remove(orderInfo.getId());
            }

            result.put(parking.getId(), amount);
        }

        // 插入数据
        List<ParkingAmountStatistics> list = new ArrayList<>();

        for (Map.Entry<Long, ParkingAmountStatistics> tmp : result.entrySet()) {
            list.add(tmp.getValue());
        }

        logger.error("报表统计-停车收费统计结束" + DateParserUtil.formatDate(start) + "结束，结果有" + list.size() + "条! 结果: " + JSON.toJSONString(list));

        if (list.size() > 0) {
            parkingAmountStatisticsService.insertBatch(list, list.size());
        }
    }

    /**
     * 符合条件的订单信息
     * @param start
     * @param end
     * @return
     */
    private List<Parking> getParkingInfo(Date start, Date end) {
        Wrapper<OrderInfo> orderInfoWrapper = new EntityWrapper<>();

        orderInfoWrapper.where("yxy_order_info.leave_at >= {0} AND yxy_order_info.leave_at < {1}", start, end);

        return parkingService.customSelectOrderList(orderInfoWrapper);
    }

    /**
     * 符合条件的交易信息
     * @param start
     * @param end
     * @return
     */
    private List<OrderTransaction> getOrderTransaction(Date start, Date end) {
        Wrapper<OrderTransaction> orderTransactionWrapper = new EntityWrapper<>();

        orderTransactionWrapper.where("yxy_order_transaction.finished_at >= {0} AND " +
                "yxy_order_transaction.finished_at < {1}", start, end);

        return orderTransactionService.selectList(orderTransactionWrapper);
    }

    /**
     * 统计次数
     * @param parkingTimeLengthStatistics
     * @param timeLength
     * @return
     */
    private ParkingTimeLengthStatistics timeLengthStatistics(ParkingTimeLengthStatistics parkingTimeLengthStatistics,
                                                             Long timeLength) {
        for (StatisticsTimeLengthEnum statisticsTimeLengthEnum : StatisticsTimeLengthEnum.values()) {
            Integer[] range = statisticsTimeLengthEnum.getValue();
            String methodName = statisticsTimeLengthEnum.getKey();
            Boolean isInRange = inRange(Math.toIntExact(timeLength), range[0], range[1]);

            if (isInRange) {
                try {
                    Method getMethod = ParkingTimeLengthStatistics.class.getMethod("get" + methodName);
                    Method setMethod = ParkingTimeLengthStatistics.class.getMethod("set" + methodName, Integer.class);

                    Integer times = (Integer) getMethod.invoke(parkingTimeLengthStatistics);

                    setMethod.invoke(parkingTimeLengthStatistics, times + 1);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return parkingTimeLengthStatistics;
    }

    /**
     * 是否在时间范围内
     * @param timelength
     * @param start
     * @param end
     * @return
     */
    private Boolean inRange(Integer timelength, Integer start, Integer end) {
        return timelength >= start && timelength < end;
    }

    /**
     * 格式化数据
     * @param data
     * @return
     */
    private Map<Long, List<OrderTransaction>> formatDate(List<OrderTransaction> data) {
        Map<Long, List<OrderTransaction>> result = new HashMap<>();

        for (OrderTransaction transaction : data) {
            Long id = transaction.getOrderId();
            List<OrderTransaction> list = result.get(id);

            if (list == null) {
                list = new ArrayList<>();
            }

            list.add(transaction);

            result.put(id, list);
        }

        return result;
    }

    /**
     * 统计相关金额
     * @param parkingAmountStatistics
     * @param orderTransaction
     * @return
     */
    private ParkingAmountStatistics statisticsAmount(ParkingAmountStatistics parkingAmountStatistics,
                                                     OrderTransaction orderTransaction) {
        Map<TransactionPayWay, String> payWays = new HashMap<TransactionPayWay, String>() {
            {
                put(TransactionPayWay.WECHAT, "WechatAmount");
                put(TransactionPayWay.ALIPAY, "AlipayAmount");
                put(TransactionPayWay.COUPON, "DiscountAmount");
                put(TransactionPayWay.CASH, "CashAmount");
                put(TransactionPayWay.WECHAT_PRE_PAY, "PrePaidAmount");
                put(TransactionPayWay.WECHAT_REFUND, "PrePaidRefundsAmount");
            }
        };

        parkingAmountStatistics.setPaidAmount(parkingAmountStatistics.getPrePaidAmount() + orderTransaction.getAmount());

        for (Map.Entry<TransactionPayWay, String> tmp : payWays.entrySet()) {
            if (orderTransaction.getPayWay().equals(tmp.getKey())) {
                try {
                    Method getMethod = parkingAmountStatistics.getClass().getMethod("get" + tmp.getValue());
                    Method setMethod = parkingAmountStatistics.getClass().getMethod("set" + tmp.getValue(), Double.class);

                    Double amount = (Double) getMethod.invoke(parkingAmountStatistics);

                    setMethod.invoke(parkingAmountStatistics, amount + orderTransaction.getAmount());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                break;
            }
        }

        return parkingAmountStatistics;
    }
}
