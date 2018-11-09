package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.OrgParking;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.mapper.OrgParkingMapper;
import com.yxytech.parkingcloud.core.service.IOrgParkingService;
import com.yxytech.parkingcloud.core.service.IOrganizationService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-11-10
 */
@Service
public class OrgParkingServiceImpl extends ServiceImpl<OrgParkingMapper, OrgParking> implements IOrgParkingService {

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IParkingService parkingService;


    @Override
    public void syncParkings(List<Long> parkingIds, Long orgId) {
        EntityWrapper<OrgParking> ew = new EntityWrapper<>();
        ew.setSqlSelect("parking_id").eq("org_id",orgId);
        List<Object> existParkingIds = ListUtils.typedList(baseMapper.selectObjs(ew),Long.class);

        List<Long> intersection = ListUtils.intersection(parkingIds,existParkingIds);
        List<Long> toRemoveIds = ListUtils.subtract(existParkingIds,intersection);
        List<Long> toAddIds = ListUtils.subtract(parkingIds,intersection);

        EntityWrapper<Organization> ewOrg = new EntityWrapper<>();
        ewOrg.setSqlSelect("is_property_org","is_manage_org","is_regulatory").eq("id",orgId);
        Organization org = organizationService.selectOne(ewOrg);

        if(!toAddIds.isEmpty()){
            List<OrgParking> toAdd = new ArrayList<>();
            toAddIds.forEach((item) -> toAdd.add(new OrgParking(orgId,item,org.getManageOrg(),org.getPropertyOrg(),org.getRegulatory())));

            insertBatch(toAdd);
        }

        /*if(!toRemoveIds.isEmpty()){
            EntityWrapper<OrgParking> ewToRemove = new EntityWrapper<>();
            ewToRemove.eq("org_id",orgId).in("parking_id",toRemoveIds);
            delete(ewToRemove);
        }*/
    }

    @Override
    public List<Object> findBindParkingIds(Long orgId) {
        EntityWrapper<OrgParking> ew = new EntityWrapper<>();
        ew.setSqlSelect("parking_id").eq("org_id",orgId);
        List<Object> parkingIds = ListUtils.typedList(baseMapper.selectObjs(ew),Long.class);

        return parkingIds;
    }

    @Override
    public List<Parking> findBindParkings(List<Object> parkingIds) {
       List<Parking> parkings = new ArrayList<>();
       if(!parkingIds.isEmpty()){
           EntityWrapper<Parking> ewParking = new EntityWrapper<>();
           ewParking.setSqlSelect("id", "code","full_name", "approve_status").in("id",parkingIds);
           parkings = parkingService.selectList(ewParking);
       }

       return parkings;
    }
}
