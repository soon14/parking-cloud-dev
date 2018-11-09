package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.FeeFestival;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-29
 */
public interface IFeeFestivalService extends IService<FeeFestival> {

    public void deleteThisYear(Integer year);

    public List<FeeFestival> findByYear(Integer year);

    public Boolean isHoliday(Date date);

    List<FeeFestival> intervalHolidays(Date startTime,Date endTime);

    List<Date> getAllHolidayByStartAndEnd(Date startTime, Date endTime);
}
