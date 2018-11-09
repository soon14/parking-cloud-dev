package com.yxytech.parkingcloud.core.mapper;

import com.yxytech.parkingcloud.core.entity.FeeFestival;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author cj
 * @since 2017-10-29
 */
public interface FeeFestivalMapper extends SuperMapper<FeeFestival> {

    public void deleteThisYear(Integer year);

    public List<FeeFestival> findByYear(Integer year);
    List<Date> getAllHolidayByStartAndEnd(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

}