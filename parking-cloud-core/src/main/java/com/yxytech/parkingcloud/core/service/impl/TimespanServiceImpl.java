package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Timespan;
import com.yxytech.parkingcloud.core.mapper.TimespanMapper;
import com.yxytech.parkingcloud.core.service.ITimespanService;
import com.yxytech.parkingcloud.core.utils.TimespanUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwd
 * @since 2017-10-20
 */
@Service
public class TimespanServiceImpl extends ServiceImpl<TimespanMapper, Timespan> implements ITimespanService {

    private static final Long DAY_TIMES = 86400000L;

    @Override
    public Long passedCycle(Timespan timespan, Date date, Boolean type) {
        return TimespanUtil.passedCycle(timespan, date, type);
    }

    @Override
    public Date getDate(Timespan timespan, Long passedCycle, Boolean type) {
        return TimespanUtil.getDate(timespan, passedCycle, type);
    }
}
