package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import com.yxytech.parkingcloud.core.mapper.ParkingMapper;
import com.yxytech.parkingcloud.core.service.*;
import com.yxytech.parkingcloud.core.utils.YXYServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
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
public class ParkingServiceImpl extends YXYServiceImpl<ParkingMapper,Parking> implements IParkingService {

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IParkingOwnerService parkingOwnerService;

    @Autowired
    private IParkingOperatorService parkingOperatorService;

    @Autowired
    private IAreaService areaService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String keyNamePrefix = "usedParkingCells:";

    @Override
    public String validate(String fullName) {
        String existMsg = null;

        EntityWrapper<Parking> ew = new EntityWrapper<>();
        ew.eq("full_name",fullName);

        if(baseMapper.selectCount(ew) > 0) existMsg = "停车场名称已存在";

        return existMsg;
    }

    @Override
    public void createParking(Parking parking) {
         baseMapper.createParking(parking);
    }

    @Override
    public List<ParkingInfoForApp> getNearParking(Double longitude, Double latitude, Double radius) {
        return baseMapper.getAllNear(longitude, latitude, radius);
    }

    @Override
    public void updateParking(Parking parking) {
        baseMapper.update(parking);
    }

    @Override
    public Parking getParkingInfo(Long id) {
        return baseMapper.getParkingInfo(id);
    }

    @Override
    public Page<Parking> selectByPage(Page<Parking> page, Wrapper<Parking> wrapper) {
        page.setRecords(baseMapper.selectByPage(page,wrapper));

        return page;
    }


    @Override
    public List<Parking> customSelectOrderList(Wrapper<OrderInfo> wrapper) {
        return baseMapper.customSelectOrderList(wrapper);
    }

    @Override
    public List<Parking> customSelectTransactionList(Wrapper<OrderTransaction> wrapper) {
        return baseMapper.customSelectTransactionList(wrapper);
    }

    @Override
    public String validateParkingIds(List<Long> parkingIds) {
        String errorMsg = null;
        EntityWrapper<Parking> ew = new EntityWrapper<>();
        ew.setSqlSelect("id").in("id", parkingIds);
        List<Object> existIds = baseMapper.selectObjs(ew);

        if (parkingIds.size() > existIds.size()) {
            existIds.forEach(item -> parkingIds.remove(item));
            errorMsg = "停车场id不存在：" + parkingIds.toString();
        }
        return errorMsg;
    }

    @Override
    public String examine(Parking parking) {
        EntityWrapper<Organization> ew1 = new EntityWrapper<>();
        ew1.setSqlSelect("is_property_org","id").eq("id",parking.getOwnerOrgId());
        Organization ownerOrg = organizationService.selectOne(ew1);
        // 增加单位产权单位性质
        ownerOrg.setPropertyOrg(true);

        organizationService.updateById(ownerOrg);

        //获取停车场的经营单位
        Long operatorOrgId = parking.getOperatorOrgId();

        EntityWrapper<Organization> ew2 = new EntityWrapper<>();
        ew2.setSqlSelect("is_manage_org","id").eq("id",operatorOrgId);
        Organization operatorOrg = organizationService.selectOne(ew2);
        operatorOrg.setManageOrg(true);

        // 增加单位为经营单位的性质
        organizationService.updateById(operatorOrg);

        if(parking!=null && parking.getApproveStatus().equals(ApproveEnum.APPROVE_ING)){
            // 如果停车场认证状态为认证中
            parking.setApproveStatus(ApproveEnum.APPROVE_SUCCESS);
            parking.setGps(null);

            updateParking(parking);
        }else{
            return  "非法操作";
        }

        return null;
    }

    @Override
    public Double getRadius(Double longitude, Double latitude, Double leftTopLongitude, Double leftTopLatitude) {
        Double width = leftTopLongitude - longitude;
        Double height = leftTopLatitude - latitude;

        return Math.sqrt((width * width) + (height * height));
    }

    public List<Parking> findByArea(Long areaId) {
        if(areaId == 0){
            EntityWrapper<Parking> ew1 = new EntityWrapper<>();
            List<Parking> parkings = baseMapper.selectList(ew1);
            
            return parkings;
        }
        EntityWrapper<Parking> ew2 = new EntityWrapper<>();
        ew2.eq("street_area_id",areaId);
        List<Parking> parkings = baseMapper.selectList(ew2);

        return parkings;
    }

    @Override
    public Page<ParkingInfoForApp> getNearParkingPagination(Page<ParkingInfoForApp> page, Double longitude,
                                                            Double latitude, Double radius) {
        page.setRecords(baseMapper.getAllNear(page, longitude, latitude, radius));

        return page;
    }

    @Override
    public void approve(Parking parking,ParkingOwner parkingOwner,ParkingOperator parkingOperator) {
        parkingOwner.setApproveStatus(ApproveEnum.APPROVE_ING);
        parkingOwnerService.insert(parkingOwner);

        parkingOperator.setApproveStatus(ApproveEnum.APPROVE_ING);
        parkingOperatorService.insert(parkingOperator);

        // 将经营备案证保存到停车场信息
        parking.setApproveStatus(ApproveEnum.APPROVE_ING);
        parking.setGps(null);

        updateParking(parking);
    }

    @Override
    public void updateBatch(List<Parking> list) {
        baseMapper.updateBatch(list);
    }

    @Override
    public Parking detail(Long id) {
        Parking parking = baseMapper.selectById(id);
        if(parking == null)
            return null;

        if(parking.getOwnerOrgId() != null){
            EntityWrapper<Organization> ewOwner = new EntityWrapper<>();
            ewOwner.eq(parking.getOwnerOrgId()!=null,"id",parking.getOwnerOrgId());
            Map<Object,Object> ownerOrgMap = organizationService.selectIdNameMap(ewOwner,"id","full_name");
            parking.setOwnerOrgName((String) ownerOrgMap.get(parking.getOwnerOrgId()));
        }else{
            parking.setOwnerOrgName(null);
        }

        if(parking.getOperatorOrgId() != null){
            EntityWrapper<Organization> ewOperator = new EntityWrapper<>();
            ewOperator.eq(parking.getOperatorOrgId()!=null,"id",parking.getOperatorOrgId());
            Map<Object,Object> operatorOrgMap = organizationService.selectIdNameMap(ewOperator,"id","full_name");
            parking.setOperatorOrgName((String) operatorOrgMap.get(parking.getOperatorOrgId()));
        }else{
            parking.setOperatorOrgName(null);
        }

        // 在停车辆个数
        String keyName = keyNamePrefix + id;
        String v = redisTemplate.opsForValue().get(keyName);

        if (v == null) {
            parking.setUsedCells(0);
        } else {
            parking.setUsedCells(Integer.valueOf(v));
        }

        return parking;
    }

    @Override
    public String getAreas(Long streetAreaId) {
        Area street = areaService.selectById(streetAreaId);

        Long districtId = street.getParentId();
        Area district = areaService.selectById(districtId);

        Long cityId = district.getParentId();
        Area city = areaService.selectById(cityId);

        String areas = city.getName() + "/" + district.getName() + "/" + street.getName();

        return areas;
    }

    @Override
    public String updateValidate(Parking parking) {
        EntityWrapper<Parking> ew = new EntityWrapper<>();
        ew.eq("full_name",parking.getFullName());
        List<Parking> exist = baseMapper.selectList(ew);
        if(exist.size() > 0 && exist.get(0).getId() != parking.getId())
            return "停车场不可重复";

        return null;
    }

    @Override
    public List<Parking> findAll(String parking) {

        return baseMapper.findAll(parking);
    }

    @Override
    public synchronized void increaseParkingCellUsedCount(Long parkingId) {
        String keyName = keyNamePrefix + parkingId;

        redisTemplate.opsForValue().increment(keyName, 1);
    }

    @Override
    public synchronized void decreaseParkingCellUsedCount(Long parkingId) {
        String keyName = keyNamePrefix + parkingId;

        redisTemplate.opsForValue().increment(keyName, -1);
    }

    @Override
    public synchronized void syncParkingCellUsedCount(List<Map<String, Integer>> cellUsed) {
        redisTemplate.delete(redisTemplate.keys(keyNamePrefix + "*"));

        cellUsed.forEach(v -> {
            String keyName = keyNamePrefix + v.get("parkingId");
            String value = String.valueOf(v.get("usedCells"));

            redisTemplate.opsForValue().set(keyName, value);
        });
    }
}
