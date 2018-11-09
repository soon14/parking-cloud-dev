package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Blacklist;
import com.yxytech.parkingcloud.core.mapper.BlacklistMapper;
import com.yxytech.parkingcloud.core.service.IBlacklistService;
import org.springframework.stereotype.Service;

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
public class BlacklistServiceImpl extends ServiceImpl<BlacklistMapper, Blacklist> implements IBlacklistService {
    @Override
    public void isReflect(Blacklist blacklist) {
        if (blacklist.getStartedAt().compareTo(blacklist.getEndAt()) >= 0) {
            throw new RuntimeException("启用时间不能在结束时间之后!");
        }

        Wrapper<Blacklist> blacklistWrapper = new EntityWrapper<>();

        blacklistWrapper.where("plate_number = {0}", blacklist.getPlateNumber())
                .where("plate_color = {0}", blacklist.getPlateColor())
                .where("parking_id = {0}", blacklist.getParkingId())
                .where("is_valid is TRUE")
                .andNew()
                .where("(started_at <= {0} AND end_at >= {1}) OR (started_at <= {2} AND end_at >= {3}) OR " +
                                "(started_at >= {4} AND end_at <= {5})", blacklist.getStartedAt(), blacklist.getStartedAt(),
                        blacklist.getEndAt(), blacklist.getEndAt(), blacklist.getStartedAt(), blacklist.getEndAt());

        List<Blacklist> list = baseMapper.selectList(blacklistWrapper);

        if (list.size() > 0) {
            throw new RuntimeException("车牌号为" + blacklist.getPlateNumber() + "的有效期冲突，请检查后重试!");
        }
    }

    @Override
    public Page<Blacklist> customerSelect(Page<Blacklist> page, Wrapper<Blacklist> wrapper) {
        page.setRecords(baseMapper.customerSelect(page, wrapper));

        return page;
    }

    @Override
    public Boolean isInBlackList(String plateNumber, Integer plateColor, Long parkingId) {
        Wrapper<Blacklist> blacklistWrapper = new EntityWrapper<>();

        blacklistWrapper.where("plate_number = {0}", plateNumber)
                .and("plate_color = {0}", plateColor)
                .and("parking_id = {0}", parkingId)
                .and("end_at > now()")
                .and("started_at <= now()")
                .and("is_valid = TRUE");

        Integer ret = baseMapper.selectCount(blacklistWrapper);

        return ret > 0;
    }
}
