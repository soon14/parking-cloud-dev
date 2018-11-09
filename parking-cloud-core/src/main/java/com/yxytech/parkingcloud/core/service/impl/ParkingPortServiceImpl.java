package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingPort;
import com.yxytech.parkingcloud.core.mapper.ParkingPortMapper;
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
public class ParkingPortServiceImpl extends YXYServiceImpl<ParkingPortMapper,ParkingPort> implements IParkingPortService {

    @Autowired
    private IParkingService parkingService;

    @Override
    public String validate(Long parkingId, String code) {
        String existMsg = null;

        EntityWrapper<ParkingPort> ew = new EntityWrapper<>();
        ew.eq("parking_id",parkingId);
        ew.eq("code",code);

        if(baseMapper.selectCount(ew) > 0) existMsg = "请勿重复创建";

        return existMsg;
    }

    @Override
    public void createParkingPort(ParkingPort parkingPort) {
        baseMapper.createParkingPort(parkingPort);
    }

    @Override
    public void updateParkingPort(ParkingPort parkingPort) {
        baseMapper.update(parkingPort);
    }

    @Override
    public void updateBatch(List<ParkingPort> list) {
        baseMapper.updateBatch(list);
    }

    @Override
    public ParkingPort detail(Long id) {
        ParkingPort parkingPort = baseMapper.selectById(id);
        if(parkingPort == null)
            return null;

        EntityWrapper<Parking> ewParking = new EntityWrapper<>();
        ewParking.eq("id",parkingPort.getParkingId());
        Map<Object,Object> parkingMap = parkingService.selectIdNameMap(ewParking,"id","full_name");

        parkingPort.setParkingName((String) parkingMap.get(parkingPort.getParkingId()));

        return parkingPort;
    }

    @Override
    public String updateValidate(ParkingPort parkingPort) {
        EntityWrapper<ParkingPort> ew = new EntityWrapper<>();
        ew.eq("parking_id",parkingPort.getParkingId());
        ew.eq("code",parkingPort.getCode());
        List<ParkingPort> exist = baseMapper.selectList(ew);

        if(exist.size() > 0 && exist.get(0).getId() != parkingPort.getId())
            return "出入口不可重复";

        return null;
    }


}
