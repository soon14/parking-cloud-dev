package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.mapper.SimMapper;
import com.yxytech.parkingcloud.core.service.IChargeFacilityService;
import com.yxytech.parkingcloud.core.service.IOrganizationService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.service.ISimService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-10-24
 */
@Service
public class SimServiceImpl extends ServiceImpl<SimMapper, Sim> implements ISimService {

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IChargeFacilityService chargeFacilityService;

    @Autowired
    private IParkingService parkingService;

    @Override
    public Page<Sim> selectByPage(Page<Sim> page, Wrapper<Sim> wrapper) {
        page.setRecords(baseMapper.selectByPage(page,wrapper));

        return page;
    }

    @Override
    public String validate(Long manageOrgId, Long facOrgId,String simNumber,Long parkingId) {
        EntityWrapper<Sim> ew3 = new EntityWrapper<>();
        ew3.eq("sim_number",simNumber);
        if(baseMapper.selectCount(ew3) > 0)
             return  "请勿重复创建";

        EntityWrapper<Organization> ew1 = new EntityWrapper<>();
        ew1.eq("id",manageOrgId);
          if(organizationService.selectCount(ew1) < 1)
             return "管理单位不存在";

        EntityWrapper<Organization> ew2 = new EntityWrapper<>();
        ew2.eq("id",facOrgId);
          if(organizationService.selectCount(ew2) < 1)
              return "设备厂商不存在";

        EntityWrapper<Parking> ew4 = new EntityWrapper<>();
        ew3.eq("id",parkingId);
          if(parkingService.selectCount(ew4) < 1)
              return "所属停车场不存在";

        return  null;
    }

    private Long getTerminalDeviceId(String terminalDeviceCode){
        EntityWrapper<ChargeFacility> ew = new EntityWrapper<>();
        ew.setSqlSelect("id").eq("system_number",terminalDeviceCode);
        Object terminalDeviceId = chargeFacilityService.selectObj(ew);

        return (Long) terminalDeviceId;
    }

    @Override
    public Map<String, Object> getBind(String manageOrgName, String facOrgName,String parkingName) {
        Map<String,Object> map = new HashMap<>();
        EntityWrapper<Organization> ew1 = new EntityWrapper<>();
        ew1.eq("full_name",manageOrgName);
        Organization manageOrg = organizationService.selectOne(ew1);
        map.put("manageOrg",manageOrg);

        EntityWrapper<Organization> ew2 = new EntityWrapper<>();
        ew2.eq("full_name",facOrgName);
        Organization facOrg = organizationService.selectOne(ew2);
        map.put("facOrg",facOrg);

        EntityWrapper<Parking> ew3 = new EntityWrapper<>();
        ew3.eq("full_name",parkingName);
        Parking parking = parkingService.selectOne(ew3);
        map.put("parking",parking);

        return map;
    }

    @Override
    public String createBatch(List<SimBatch> simBatches) {
        if(simBatches.isEmpty())
            return "非法参数";

        EntityWrapper<Sim> ew = new EntityWrapper<>();
        List<String> simNumbers = new ArrayList<>();
        simBatches.forEach((item) -> simNumbers.add(item.getSimNumber()));
        ew.in(simNumbers!=null,"sim_number",simNumbers);
        if(baseMapper.selectCount(ew) > 0)
            return "手机号不可重复";

        for(SimBatch simBatch : simBatches){
            Sim sim = new Sim();
            BeanUtils.copyProperties(simBatch,sim);
            Map<String,Object> map = getBind(simBatch.getManageOrgName(),simBatch.getFacOrgName(),simBatch.getParkingName());
              if(map.get("manageOrg") == null || map.get("facOrg") == null || map.get("parking") == null)
                  return "关联信息不存在";

            Long terminalDeviceId = getTerminalDeviceId(simBatch.getTeminalDeviceCode());
              if(terminalDeviceId == null)
                  return "终端设备不存在";

            sim.setTeminalDeviceId(terminalDeviceId);
            sim.setFacOrgId(((Organization) map.get("facOrg")).getId());
            sim.setManageOrgId(((Organization) map.get("manageOrg")).getId());
            sim.setParkingId(((Parking) map.get("parking")).getId());
            sim.setPutInAt(new Date());
            baseMapper.insert(sim);

              if(!((Organization) map.get("facOrg")).getFacilityOrg()){
                ((Organization) map.get("facOrg")).setFacilityOrg(true);
                 organizationService.updateById((Organization) map.get("facOrg"));
              }
        }
        return null;
    }

    @Override
    public Sim detail(Long id) {
        Sim sim = baseMapper.selectById(id);
        if(sim == null)
            return null;

        List<Long> orgIds = new ArrayList<>();
        orgIds.add(sim.getFacOrgId());
        orgIds.add(sim.getManageOrgId());
        Map<Long,String> orgMap = organizationService.selectIdNameMap(orgIds,"id","full_name");
        sim.setFacOrgName(orgMap.get(sim.getFacOrgId()));
        sim.setManageOrgName(orgMap.get(sim.getManageOrgId()));

        EntityWrapper<Parking> ewParking = new EntityWrapper<>();
        ewParking.eq("id",sim.getParkingId());
        Map<Object,Object>  parkingMap = parkingService.selectIdNameMap(ewParking,"id","full_name");
        sim.setParkingName((String) parkingMap.get(sim.getParkingId()));

        return sim;
    }

    @Override
    public String updateValidate(Sim sim) {
        EntityWrapper<Sim> ew3 = new EntityWrapper<>();
        ew3.eq("sim_number",sim.getSimNumber());
        List<Sim> exist = baseMapper.selectList(ew3);
        if(exist.size() > 0 && exist.get(0).getId() != sim.getId())
            return  "请勿重复创建";

        EntityWrapper<Organization> ew1 = new EntityWrapper<>();
        ew1.eq("id",sim.getManageOrgId());
        if(organizationService.selectCount(ew1) < 1)
            return "管理单位不存在";

        EntityWrapper<Organization> ew2 = new EntityWrapper<>();
        ew2.eq("id",sim.getFacOrgId());
        if(organizationService.selectCount(ew2) < 1)
            return "设备厂商不存在";

        EntityWrapper<Parking> ew4 = new EntityWrapper<>();
        ew4.eq("id",sim.getParkingId());
        if(parkingService.selectCount(ew4) < 1)
            return "所属停车场不存在";

        return  null;
    }
}
