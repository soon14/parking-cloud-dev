package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.ParkingCellUse;
import com.yxytech.parkingcloud.core.mapper.ParkingCellUseMapper;
import com.yxytech.parkingcloud.core.service.IParkingCellUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-10-19
 */
@Service
public class ParkingCellUseServiceImpl extends ServiceImpl<ParkingCellUseMapper, ParkingCellUse> implements IParkingCellUseService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String keyNamePrefix = "usedParkingCells:";

    @Override
    public List<ParkingCellUse> getInUseCells(List<Long> parkingIds) {
        List<ParkingCellUse> ret = new ArrayList<>();

        parkingIds.forEach(v -> {
            String count = redisTemplate.opsForValue().get(keyNamePrefix + v);

            if (count != null) {
                ParkingCellUse use = new ParkingCellUse();
                use.setParkingId(v);
                use.setUsingNumber(Integer.valueOf(count));

                ret.add(use);
            }
        });

        return ret;
    }
}
