package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.FeeFestival;
import com.yxytech.parkingcloud.core.mapper.FeeFestivalMapper;
import com.yxytech.parkingcloud.core.service.IFeeFestivalService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-10-29
 */
@Service
public class FeeFestivalServiceImpl extends ServiceImpl<FeeFestivalMapper, FeeFestival> implements IFeeFestivalService {


    @Override
    public void deleteThisYear(Integer year) {
        baseMapper.deleteThisYear(year);
    }

    @Override
    public List<FeeFestival> findByYear(Integer year) {
        List<FeeFestival> feeFestivals = baseMapper.findByYear(year);

        return feeFestivals;
    }

    @Override
    public Boolean isHoliday(Date date) {
        Wrapper<FeeFestival> feeFestivalWrapper = new EntityWrapper<>();
        feeFestivalWrapper.where("yxy_fee_festival.start_time <= {0}",date);
        feeFestivalWrapper.where("yxy_fee_festival.end_time >= {0}",date);
        List<FeeFestival> feeFestivals = baseMapper.selectList(feeFestivalWrapper);

        return !feeFestivals.isEmpty();
    }

    @Override
    public List<FeeFestival> intervalHolidays(Date startTime, Date endTime) {
        EntityWrapper<FeeFestival> ew = new EntityWrapper<>();
        ew.where("yxy_fee_festival.start_time >= {0}",startTime).
                where("yxy_fee_festival.end_time <= {0}",endTime);

        List<FeeFestival> feeFestivals = baseMapper.selectList(ew);

        return feeFestivals;
    }

    @Override
    public List<Date> getAllHolidayByStartAndEnd(Date startTime, Date endTime) {
        return baseMapper.getAllHolidayByStartAndEnd(startTime, endTime);
    }
}
