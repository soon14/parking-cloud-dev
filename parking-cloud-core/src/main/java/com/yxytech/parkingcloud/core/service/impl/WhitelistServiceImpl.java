package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Whitelist;
import com.yxytech.parkingcloud.core.mapper.WhitelistMapper;
import com.yxytech.parkingcloud.core.service.IWhitelistService;
import com.yxytech.parkingcloud.core.utils.FreeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class WhitelistServiceImpl extends ServiceImpl<WhitelistMapper, Whitelist> implements IWhitelistService {

    @Override
    public void isReflect(Whitelist whitelist) {
        Wrapper<Whitelist> whitelistWrapper = new EntityWrapper<>();

        if (whitelist.getStartedAt().compareTo(whitelist.getEndAt()) >= 0) {
            throw new RuntimeException("启用时间不能在结束时间之后!");
        }

        whitelistWrapper.where("plate_number = {0}", whitelist.getPlateNumber())
                .where("plate_color = {0}", whitelist.getPlateColor())
                .where("parking_id = {0}", whitelist.getParkingId())
                .where("is_valid is TRUE")
                .andNew()
                .where("(started_at <= {0} AND end_at >= {1}) OR (started_at <= {2} AND end_at >= {3}) OR " +
                                "(started_at >= {4} AND end_at <= {5})", whitelist.getStartedAt(), whitelist.getStartedAt(),
                        whitelist.getEndAt(), whitelist.getEndAt(), whitelist.getStartedAt(), whitelist.getEndAt());

        List<Whitelist> list = baseMapper.selectList(whitelistWrapper);

        if (list.size() > 0) {
            throw new RuntimeException("车牌号为" + whitelist.getPlateNumber() + "的有效期冲突，请检查后重试!");
        }
    }

    @Override
    public Page<Whitelist> customerSelect(Page<Whitelist> page, Wrapper<Whitelist> wrapper) {
        page.setRecords(baseMapper.customerSelect(page, wrapper));

        return page;
    }

    @Override
    public List<FreeEntity> getAllFreeTime(List<Whitelist> whitelists, Date startTime, Date endTime) {
        List<FreeEntity> result = new ArrayList<>();

        for (Whitelist whitelist : whitelists) {
            FreeEntity freeEntity = new FreeEntity();

            if (startTime.compareTo(whitelist.getStartedAt()) >= 0) {
                freeEntity.setStartedAt(startTime);

                if (endTime.compareTo(whitelist.getEndAt()) >= 0) {
                    freeEntity.setEndAt(whitelist.getEndAt());
                } else {
                    freeEntity.setEndAt(endTime);
                }
            } else {
                freeEntity.setStartedAt(whitelist.getStartedAt());

                if (endTime.compareTo(whitelist.getEndAt()) >= 0) {
                    freeEntity.setEndAt(whitelist.getEndAt());
                } else {
                    freeEntity.setEndAt(endTime);
                }
            }

            result.add(freeEntity);
        }

        return result;
    }
}
