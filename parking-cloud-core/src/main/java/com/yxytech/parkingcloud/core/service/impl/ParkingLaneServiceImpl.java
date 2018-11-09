package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingLane;
import com.yxytech.parkingcloud.core.entity.ParkingPort;
import com.yxytech.parkingcloud.core.mapper.ParkingLaneMapper;
import com.yxytech.parkingcloud.core.service.IParkingLaneService;
import com.yxytech.parkingcloud.core.service.IParkingPortService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.utils.YXYServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-10-18
 */
@Service
public class ParkingLaneServiceImpl extends YXYServiceImpl<ParkingLaneMapper,ParkingLane> implements IParkingLaneService {

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IParkingPortService parkingPortService;

    @Override
    public String validate(Long parkingId, Long parkingPortId, String code) {
        String existMsg = null;

        EntityWrapper<ParkingLane> ew = new EntityWrapper<>();
        ew.eq("parking_id",parkingId);
        ew.eq("parking_port_id",parkingPortId);
        ew.eq("code",code);

        if(baseMapper.selectCount(ew) > 0) existMsg = "请勿重复创建";

        return existMsg;
    }

    @Override
    public ParkingLane detail(Long id) {
        ParkingLane parkingLane = baseMapper.selectById(id);
        if(parkingLane == null)
            return null;

        EntityWrapper<Parking> ewParking = new EntityWrapper<>();
        ewParking.eq("id",parkingLane.getParkingId());
        Map<Object,Object> parkingMap = parkingService.selectIdNameMap(ewParking,"id","full_name");
        parkingLane.setParkingName((String) parkingMap.get(parkingLane.getParkingId()));

        EntityWrapper<ParkingPort> ewPort = new EntityWrapper<>();
        ewPort.eq("id",parkingLane.getParkingPortId());
        Map<Object,Object> portMap = parkingPortService.selectIdNameMap(ewPort,"id","code");
        parkingLane.setParkingPortCode((String) portMap.get(parkingLane.getParkingPortId()));

        return parkingLane;
    }

    @Override
    public String updateValidate(ParkingLane parkingLane) {
        EntityWrapper<ParkingLane> ew = new EntityWrapper<>();
        ew.eq("parking_id",parkingLane.getParkingId());
        ew.eq("parking_port_id",parkingLane.getParkingPortId());
        ew.eq("code",parkingLane.getCode());
        List<ParkingLane> exist = baseMapper.selectList(ew);

        if(exist.size() > 0 && exist.get(0).getId() != parkingLane.getId())
             return "车道不可重复";

        return null;
    }
}
