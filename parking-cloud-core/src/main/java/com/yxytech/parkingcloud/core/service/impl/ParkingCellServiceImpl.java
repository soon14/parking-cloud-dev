package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingCell;
import com.yxytech.parkingcloud.core.mapper.ParkingCellMapper;
import com.yxytech.parkingcloud.core.service.IParkingCellService;
import com.yxytech.parkingcloud.core.service.IParkingService;
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
 * @since 2017-10-19
 */
@Service
public class ParkingCellServiceImpl extends ServiceImpl<ParkingCellMapper, ParkingCell> implements IParkingCellService {

    @Autowired
    private IParkingService parkingService;

    @Override
    public String validate(Long parkingId, String roadBedCode, String code) {
        String existMsg = null;

        EntityWrapper<ParkingCell> ew = new EntityWrapper<>();
        ew.eq("parking_id",parkingId);
        ew.eq("roadbed_code",roadBedCode);
        ew.eq("code",code);

        if(baseMapper.selectCount(ew) > 0) existMsg = "请勿重复创建";

        return existMsg;
    }

    @Override
    public void createParkingCell(ParkingCell parkingCell) {
        Integer result = baseMapper.createParkingCell(parkingCell);
    }

    @Override
    public void updateParkingCell(ParkingCell parkingCell) {
        Integer ret = baseMapper.update(parkingCell);
    }

    @Override
    public void updateBatch(List<ParkingCell> list) {
        baseMapper.updateBatch(list);
    }

    @Override
    public ParkingCell detail(Long id) {
        ParkingCell parkingCell = baseMapper.selectById(id);
        if(parkingCell == null)
            return null;

        EntityWrapper<Parking> ewParking = new EntityWrapper<>();
        ewParking.eq("id",parkingCell.getParkingId());
        Map<Object,Object> parkingMap = parkingService.selectIdNameMap(ewParking,"id","full_name");
        parkingCell.setParkingName((String) parkingMap.get(parkingCell.getParkingId()));

        return parkingCell;
    }

    @Override
    public String updateValidate(ParkingCell parkingCell) {
        EntityWrapper<ParkingCell> ew = new EntityWrapper<>();
        ew.eq("parking_id",parkingCell.getParkingId());
        ew.eq("roadbed_code",parkingCell.getRoadbedCode());
        ew.eq("code",parkingCell.getCode());
        List<ParkingCell> exist = baseMapper.selectList(ew);

        if(exist.size() > 0 && exist.get(0).getId() != parkingCell.getId())
             return "停车位不可重复";

        return null;
    }

}
