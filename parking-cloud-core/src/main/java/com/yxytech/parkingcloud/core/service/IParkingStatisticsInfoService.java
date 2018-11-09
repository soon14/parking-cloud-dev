package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.ParkingStatisticsInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lwd
 * @since 2017-11-13
 */
public interface IParkingStatisticsInfoService extends IService<ParkingStatisticsInfo> {


    public Object sort(List<ParkingStatisticsInfo> parkingStatisticsInfos, Integer cycle,Integer type, Date startTime, Date endTime, Integer top) throws NotFoundException;

    public Object sortIncome(List<ParkingStatisticsInfo> parkingStatisticsInfos, Integer cycle, Date startTime, Date endTime, Integer top) throws NotFoundException;

    public List<ParkingStatisticsInfo> selectByMonth(@Param("ew") Wrapper<ParkingStatisticsInfo> wrapper);

    public Map<String, Date> dateSearch(Integer cycle, String time, String startTime, String endTime) throws Exception;

    List<ParkingStatisticsInfo> selectHistoryRank();

    List<ParkingStatisticsInfo> selectPassedMonth(Integer length, Wrapper<ParkingStatisticsInfo> wrapper);

    public List<ParkingStatisticsInfo> selectTopList(Date dateTime);

    public List<ParkingStatisticsInfo> selectNewTopList(Date dateTime);

    List<ParkingStatisticsInfo> incomeTop();

}
