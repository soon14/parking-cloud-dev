package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.OrgArea;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.mapper.OrgAreaMapper;
import com.yxytech.parkingcloud.core.service.IAreaService;
import com.yxytech.parkingcloud.core.service.IOrgAreaService;
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
public class OrgAreaServiceImpl extends ServiceImpl<OrgAreaMapper, OrgArea> implements IOrgAreaService {

    @Autowired
    private IAreaService areaService;

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IOrganizationService organizationService;

    @Override
    public String validateBindData(Long orgId, List<Long> areaIds,List<Long> parkingIds) {
        String errorMsg = null;
        if(!areaIds.isEmpty() && parkingIds.isEmpty()) errorMsg = areaService.validateAreaIds(areaIds);
        if(!parkingIds.isEmpty() && areaIds.isEmpty()) errorMsg = parkingService.validateParkingIds(parkingIds);

        EntityWrapper<Organization> ewOrg = new EntityWrapper<>();
        ewOrg.eq("id",orgId);
        Organization org = organizationService.selectOne(ewOrg);
        if(org == null) errorMsg += "关联单位不存在";

        return errorMsg;
    }

    @Override
    public void syncAreas(Long orgId, List<Long> areaIds) {
        EntityWrapper<OrgArea> ew = new EntityWrapper<>();
        ew.setSqlSelect("area_id").eq("org_id",orgId);
        List<Object> existAreaIds = ListUtils.typedList(baseMapper.selectObjs(ew),Long.class);

        List<Long> intersection = ListUtils.intersection(areaIds,existAreaIds);
        List<Long> toRemoveIds = ListUtils.subtract(existAreaIds,intersection);
        List<Long> toAddIds = ListUtils.subtract(areaIds,intersection);

        if(!toRemoveIds.isEmpty()){
            EntityWrapper<OrgArea> ewToRemove = new EntityWrapper<>();
            ewToRemove.eq("org_id",orgId).in("area_id",toRemoveIds);
            delete(ewToRemove);
        }

        if(!toAddIds.isEmpty()){
            List<OrgArea> toAdd = new ArrayList<>();
            toAddIds.forEach((item) -> toAdd.add(new OrgArea(orgId,item)));

            insertBatch(toAdd);
        }
    }
    @Override
    public List<Object> findBindAreaIds(Long orgId) {
        EntityWrapper<OrgArea> ewArea = new EntityWrapper<>();
        ewArea.setSqlSelect("area_id").eq("org_id",orgId);
        List<Object> areaIds = ListUtils.typedList(baseMapper.selectObjs(ewArea),Long.class);

        return areaIds;
    }
}
