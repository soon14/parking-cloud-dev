package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.FeeRateRule;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.enums.FeeRateEnum;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-29
 */
public interface IFeeBillingService extends IService<FeeRateRule> {

    Boolean isInFreeTime(CarTypeEnum carType, Long parkingId, Long enterTime, Long leaveTime, Long payTime) throws ParseException;

    /**
     *
     * @param vehicle
     * @param parkingId
     * @param enterTime
     * @param leaveTime
     * @param freeList
     * @param freeTimes
     * @param freeValidTime
     * @param payTime
     * @return
     * @throws ParseException
     */
    Map<String, Object> feeBilling(
            CarTypeEnum vehicle,
            Long parkingId,
            Long enterTime,
            Long leaveTime,
            List<Map<String, String>> freeList,
            Integer freeTimes,
            Map<String, Date> freeValidTime, Long payTime) throws ParseException;

    Map<String, Object> feeBilling(
            CarTypeEnum vehicle,
            Long parkingId,
            Long enterTime,
            Long leaveTime,
            List<Map<String, String>> freeList,
            List<Map<String, Object>> freeTimesList, Long payTime) throws ParseException;

    Map<String, Object> feeBilling(
            CarTypeEnum vehicle,
            Long parkingId,
            String enterTime,
            String leaveTime,
            List<Map<String, String>> freeList,
            List<Map<String, Object>> freeTimesList, String payTime) throws ParseException;


    Map<String, Object> feeBilling(
            CarTypeEnum vehicle,
            Long parkingId,
            Long enterTime,
            Long leaveTime,
            List<Map<String, String>> freeList,
            Integer freeTimes,
            Map<String, Date> freeValidTime) throws ParseException;

    Map<String, Object> feeBilling(
            CarTypeEnum vehicle,
            Long parkingId,
            String enterTime,
            String leaveTime,
            List<Map<String, String>> freeList,
            Integer freeTimes,
            Map<String, Date> freeValidTime,
            String payTime) throws ParseException;

    FeeRateEnum getFeeRate(Long parkingId, Long enterAt, CarTypeEnum carType) throws ParseException;

}
