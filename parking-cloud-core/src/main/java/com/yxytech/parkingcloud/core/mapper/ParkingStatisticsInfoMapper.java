package com.yxytech.parkingcloud.core.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxytech.parkingcloud.core.entity.ParkingStatisticsInfo;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author lwd
 * @since 2017-11-13
 */
public interface ParkingStatisticsInfoMapper extends SuperMapper<ParkingStatisticsInfo> {

    List<ParkingStatisticsInfo> selectList(@Param("ew") Wrapper<ParkingStatisticsInfo> wrapper);

    List<ParkingStatisticsInfo> selectByMonth(@Param("ew")Wrapper<ParkingStatisticsInfo> wrapper);

    List<ParkingStatisticsInfo> selectHistoryRank();

    List<ParkingStatisticsInfo> selectPassedMonthInfo(@Param("length") Integer length,
                                                      @Param("ew") Wrapper<ParkingStatisticsInfo> wrapper);

    List<ParkingStatisticsInfo> selectTopList(Date dateTime);
    List<ParkingStatisticsInfo> selectNewTopList(Date dateTime);

    List<ParkingStatisticsInfo> incomeTop();

}