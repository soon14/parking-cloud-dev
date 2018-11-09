package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.core.entity.Area;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.mapper.OrganzationMapper;
import com.yxytech.parkingcloud.core.service.IAreaService;
import com.yxytech.parkingcloud.core.service.IOrganizationService;
import com.yxytech.parkingcloud.core.utils.YXYServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class OrganizationServiceImpl extends  YXYServiceImpl<OrganzationMapper, Organization> implements IOrganizationService {

    @Autowired
    private IAreaService areaService;

    @Override
    public String validate(String fullName){
        String existMsg = null;
        EntityWrapper<Organization> ew = new EntityWrapper<>();
        ew.eq("full_name",fullName);

        if(baseMapper.selectCount(ew) > 0) existMsg = "单位名称已存在";

        return existMsg;
    }

    @Override
    public Page<Organization> selectByPage(Page<Organization> page, Wrapper<Organization> wrapper) {
        page.setRecords(baseMapper.selectByPage(page,wrapper));

        return page;
    }

    @Override
    public List detail(Organization organization) {
        List<Object> data = new ArrayList<>();
        data.add(organization);

        Area street = areaService.selectById(organization.getAreaId());
        Long districtId = street.getParentId();
        Map<String,Object> hashMap = new HashMap<>();
        if(districtId == 0){
            Long[] areaIds = {organization.getAreaId()};
            hashMap.put("areaIds",areaIds);
            data.add(hashMap);
            return data;
        }
        Area district = areaService.selectById(districtId);
        Long cityId = district.getParentId();
        if(cityId == 0){
            Long[] areaIds = {districtId,organization.getAreaId()};
            hashMap.put("areaIds",areaIds);
            data.add(hashMap);
            return data;
        }
        Long[] areaIds = {cityId,districtId,organization.getAreaId()};
        Area city = areaService.selectById(cityId);
        String areas = city.getName() + "/" + district.getName() + "/" + street.getName();

        hashMap.put("areaIds",areaIds);
        hashMap.put("areas",areas);
        data.add(hashMap);

        return data;
    }

    @Override
    public String updateValidate(Organization organization) {
        EntityWrapper<Organization> ew = new EntityWrapper<>();
        ew.setSqlSelect("id").eq("full_name",organization.getFullName());
        List<Object> exist = baseMapper.selectObjs(ew);
        if(exist.size() > 0 && Long.parseLong(exist.get(0).toString()) != organization.getId())
             return "单位不可重复";

        return null;
    }

    @Override
    public List<Organization> findAllOwnerOrg(String ownerOrg) {
        List<Organization> organizationList = baseMapper.findAllOwnerOrg(ownerOrg);

        return organizationList;
    }

    @Override
    public List<Organization> findAllManageOrg(String manageOrg) {

        return baseMapper.findAllManageOrg(manageOrg);
    }

    @Override
    public List<Organization> findAllFacilityOrg(String facilityOrg) {

        return baseMapper.findAllFacilityOrg(facilityOrg);
    }

}
