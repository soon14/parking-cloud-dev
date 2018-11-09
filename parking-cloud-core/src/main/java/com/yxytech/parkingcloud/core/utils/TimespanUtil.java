package com.yxytech.parkingcloud.core.utils;

import com.yxytech.parkingcloud.core.entity.Freelist;
import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.entity.Timespan;
import com.yxytech.parkingcloud.core.enums.TimespanCycleEnum;

import java.util.Date;

public class TimespanUtil {
    private static final Long DAY_TIMES = 86400000L;

    public static Date getMatchedTime(Freelist freelist, Date startedAt, Date endAt, Date date, Boolean type) {
        if (type) {
            Date start = freelist.getStartedAt().compareTo(startedAt) > 0 ? freelist.getStartedAt() : startedAt;

            if (start.compareTo(date) <= 0) {
                return date;
            } else {
                return start;
            }
        } else {
            Date end = freelist.getEndAt().compareTo(endAt) < 0 ? freelist.getEndAt() : endAt;

            if (end.compareTo(date) > 0) {
                return date;
            } else {
                return end;
            }
        }
    }

    public static Long passedCycle(Timespan timespan, Date date, Boolean type) {
        Double result = 0.0;

        if (type) {
            // 开始的周期
            if (TimespanCycleEnum.DAY.compareTo(timespan.getCycle())) {
                result = (double) (date.getTime() - timespan.getStartedAt().getTime()) / (DAY_TIMES * TimespanCycleEnum.DAY.getInterval());
            } else if (TimespanCycleEnum.WEEK.compareTo(timespan.getCycle())) {
                result = (double) (date.getTime() - timespan.getStartedAt().getTime()) / (DAY_TIMES * TimespanCycleEnum.WEEK.getInterval());
            }
        } else {
            // 结束的周期
            if (TimespanCycleEnum.DAY.compareTo(timespan.getCycle())) {
                result = (double) (date.getTime() - timespan.getEndAt().getTime()) / (DAY_TIMES * TimespanCycleEnum.DAY.getInterval());
            } else if (TimespanCycleEnum.WEEK.compareTo(timespan.getCycle())) {
                result = (double) (date.getTime() - timespan.getEndAt().getTime()) / (DAY_TIMES * TimespanCycleEnum.WEEK.getInterval());
            }
        }

        return Math.round(Math.floor(result));
    }

    public static Date getDate(Timespan timespan, Long passedCycle, Boolean type) {
        if (type) {
            if (TimespanCycleEnum.DAY.compareTo(timespan.getCycle())) {
                Long time = timespan.getStartedAt().getTime() + (passedCycle * DAY_TIMES);

                return new Date(time);
            } else if (TimespanCycleEnum.WEEK.compareTo(timespan.getCycle())) {
                Long time = timespan.getStartedAt().getTime() + (passedCycle * DAY_TIMES * TimespanCycleEnum.WEEK.getInterval());

                return new Date(time);
            }
        } else {
            if (TimespanCycleEnum.DAY.compareTo(timespan.getCycle())) {
                Long time = timespan.getEndAt().getTime() + (passedCycle * DAY_TIMES);

                return new Date(time);
            } else if (TimespanCycleEnum.WEEK.compareTo(timespan.getCycle())) {
                Long time = timespan.getEndAt().getTime() + (passedCycle * DAY_TIMES * TimespanCycleEnum.WEEK.getInterval());

                return new Date(time);
            }
        }

        return timespan.getStartedAt();
    }

    public static Long getPassedCycle(Date start, Date leaveAt, Integer cycle) {
        Long now = leaveAt.getTime();

        if (TimespanCycleEnum.DAY.compareTo(cycle)) {
            return (now - start.getTime()) / (DAY_TIMES * TimespanCycleEnum.DAY.getInterval());
        } else if (TimespanCycleEnum.WEEK.compareTo(cycle)) {
            return (now - start.getTime()) / (DAY_TIMES * TimespanCycleEnum.WEEK.getInterval());
        }

        return 0L;
    }

    public static Boolean inRange(OrderInfo orderInfo, Date start, Date end, Integer cycle, Date leaveAt) {
        Long times = TimespanUtil.getPassedCycle(start, leaveAt, cycle);
        Long passed = 0L;

        if (TimespanCycleEnum.DAY.compareTo(cycle)) {
            passed = DAY_TIMES * times;
        } else if (TimespanCycleEnum.WEEK.compareTo(cycle)) {
            passed = DAY_TIMES * times * TimespanCycleEnum.WEEK.getInterval();
        }

        return (orderInfo.getEnterAt().getTime() <= (end.getTime() + passed));
    }

    public static FreeEntity intersectionDate(FreeEntity startFreeEntity, FreeEntity endFreeEntity) {
        FreeEntity freeEntity = new FreeEntity();

        Date start = startFreeEntity.getStartedAt();
        Date end = startFreeEntity.getEndAt();
        Date startRange = endFreeEntity.getStartedAt();
        Date endRange = endFreeEntity.getEndAt();

        if (start.getTime() <= endRange.getTime() && end.getTime() < startRange.getTime()) {
            freeEntity.setStartedAt(start);
            freeEntity.setEndAt(endRange);
        } else if (endRange.getTime() <= start.getTime() && end.getTime() > endRange.getTime()) {
            freeEntity.setStartedAt(startRange);
            freeEntity.setEndAt(endRange);
        } else if (start.getTime() >= start.getTime() && end.getTime() >= endRange.getTime()) {
            freeEntity.setStartedAt(start);
            freeEntity.setEndAt(end);
        } else if (startRange.getTime() >= start.getTime() && endRange.getTime() >= end.getTime()) {
            freeEntity.setStartedAt(startRange);
            freeEntity.setEndAt(endRange);
        } else {
            freeEntity = null;
        }

        return freeEntity;
    }
}
