package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.OrderParkingInfo;
import com.yxytech.parkingcloud.core.mapper.OrderParkingInfoMapper;
import com.yxytech.parkingcloud.core.service.IOrderParkingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwd
 * @since 2017-10-26
 */
@Service
public class OrderParkingInfoServiceImpl extends ServiceImpl<OrderParkingInfoMapper, OrderParkingInfo> implements IOrderParkingInfoService {

    @Override
    public Long getValidOrder(Long parkingId, Long parkingCellId) {
        return baseMapper.getValidOrder(parkingId, parkingCellId);
    }
}
