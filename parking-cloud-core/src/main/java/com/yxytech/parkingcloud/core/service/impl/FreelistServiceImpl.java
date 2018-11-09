package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Freelist;
import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.Timespan;
import com.yxytech.parkingcloud.core.enums.TimespanCycleEnum;
import com.yxytech.parkingcloud.core.mapper.FreelistMapper;
import com.yxytech.parkingcloud.core.service.IFreelistService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.service.ITimespanService;
import com.yxytech.parkingcloud.core.utils.FreeEntity;
import com.yxytech.parkingcloud.core.utils.TimespanUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwd
 * @since 2017-10-20
 */
@Service
public class FreelistServiceImpl extends ServiceImpl<FreelistMapper, Freelist> implements IFreelistService {
    @Autowired
    private ITimespanService timespanService;

    @Autowired
    private IParkingService parkingService;

    @Override
    @Transactional
    public Boolean create(Freelist freelist) throws IllegalArgumentException {
        if (StringUtils.isEmpty(freelist.getPlateNumber())) {
            throw new RuntimeException("车牌号码不能为空!");
        }

        if (freelist.getStartedAt().compareTo(freelist.getEndAt()) >= 0) {
            throw new RuntimeException("启用时间不能在结束时间之后!");
        }

        Parking parking = parkingService.selectById(freelist.getParkingId());

        if (parking == null) {
            throw new RuntimeException("停车场不存在!");
        }

        Wrapper<Freelist> freelistWrapper = new EntityWrapper<>();

        freelistWrapper.where("plate_number = {0}", freelist.getPlateNumber())
                .where("plate_color = {0}", freelist.getPlateColor())
                .where("parking_id = {0}", freelist.getParkingId())
                .where("is_valid is TRUE")
                .andNew()
                .where("(started_at <= {0} AND end_at >= {1}) OR (started_at <= {2} AND end_at >= {3}) OR " +
                        "(started_at >= {4} AND end_at <= {5})", freelist.getStartedAt(), freelist.getStartedAt(),
                        freelist.getEndAt(), freelist.getEndAt(), freelist.getStartedAt(), freelist.getEndAt());

        List<Freelist> freelistList = baseMapper.selectList(freelistWrapper);

        if (freelistList.size() > 0) {
            throw new RuntimeException("有效期冲突，请检查后重试");
        }

        if (freelist.getTotalTimes() == null) {
            if (freelist.getTimespans() == null) {
                throw new RuntimeException("频次和时间段至少选择一个!");
            } else if (freelist.getTimespans().size() == 0) {
                throw new RuntimeException("频次和时间段至少选择一个!");
            }
        } else {
            if (freelist.getTotalTimes() <= 0) {
                throw new RuntimeException("频次和时间段至少选择一个!");
            }
        }

        Integer insertResult = baseMapper.insert(freelist);

        if (insertResult > 0 && freelist.getTimespans().size() > 0) {
            // 插入时间刻度
            List<Timespan> timespanList = freelist.getTimespans();

            for (Timespan timespan : timespanList) {
                if (!parking.getAllDay() && ! timespan.getCycle().equals(TimespanCycleEnum.DAY.getType())) {
                    throw new RuntimeException("周期选择错误!");
                }

                timespan.setFreelistId(freelist.getId());

                timespanService.insert(timespan);
            }
        }

        return true;
    }

    @Override
    public Page<Freelist> customerSelect(Page<Freelist> page, Wrapper<Freelist> wrapper) {
        page.setRecords(baseMapper.customerSelect(page, wrapper));

        return page;
    }

    @Override
    public List<FreeEntity> getAllFreeTime(OrderInfo orderInfo, List<Freelist> freelists, Date startTime, Date endTime) {
        List<FreeEntity> freeEntities = new ArrayList<>();

        if (freelists != null) {
            for (Freelist freelist : freelists) {
                if (freelist.getTotalTimes() != 0) {
                    FreeEntity freeEntity = new FreeEntity();
                    // 判断剩余次数
                    if (freelist.getUsedTimes() >= freelist.getTotalTimes()) {
                        continue;
                    }

                    freeEntity.setType(1);
                    freeEntity.setId(freelist.getId());

                    // 时间
                    Date start = TimespanUtil.getMatchedTime(freelist, freelist.getStartedAt(), freelist.getEndAt(), startTime, true);
                    Date end = TimespanUtil.getMatchedTime(freelist, freelist.getStartedAt(), freelist.getEndAt(), endTime, false);

                    freeEntity.setStartedAt(start);
                    freeEntity.setEndAt(end);

                    freeEntities.add(freeEntity);
                } else {
                    Wrapper<Timespan> timespanWrapper = new EntityWrapper<>();

                    timespanWrapper.where("freelist_id = {0}", freelist.getId());

                    List<Timespan> timespanList = timespanService.selectList(timespanWrapper);

                    for (Timespan timespan : timespanList) {
                        // 判断是否在当前时间段内
                        Boolean ret = TimespanUtil.inRange(orderInfo, timespan.getStartedAt(), timespan.getEndAt(),
                                timespan.getCycle(), endTime);

                        if (ret) {
                            // 时间段内进行遍历获取
                            Long passedStarted = TimespanUtil.passedCycle(timespan, startTime, true);
                            Long passedEnd = TimespanUtil.passedCycle(timespan, endTime, false);

                            for (Long i = passedStarted; i <= (passedEnd + 1); i++) {
                                FreeEntity freeEntity = new FreeEntity();

                                freeEntity.setType(0);
                                freeEntity.setId(freelist.getId());

                                Date startTmp = TimespanUtil.getDate(timespan, i, true);
                                Date endTmp = TimespanUtil.getDate(timespan, i, false);

                                // 时间
                                Date start = TimespanUtil.getMatchedTime(freelist, orderInfo.getEnterAt(), endTime, startTmp, true);
                                Date end = TimespanUtil.getMatchedTime(freelist, orderInfo.getEnterAt(), endTime, endTmp, false);

                                freeEntity.setStartedAt(start);
                                freeEntity.setEndAt(end);

                                if (start.compareTo(end) >= 0) {
                                    continue;
                                }

                                freeEntities.add(freeEntity);
                            }
                        }
                    }
                }
            }
        }

        return freeEntities;
    }

    @Override
    public Boolean updateForUsedTimes(Long id, Integer times) {
        return baseMapper.updateForUsedTimes(id, times) > 0;
    }
}
