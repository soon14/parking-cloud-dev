package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.Timespan;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-10-20
 */
public interface ITimespanService extends IService<Timespan> {
    Long passedCycle(Timespan timespan, Date date, Boolean type);

    Date getDate(Timespan timespan, Long passedCycle, Boolean type);
}
