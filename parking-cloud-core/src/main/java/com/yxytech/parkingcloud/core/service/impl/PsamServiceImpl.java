package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.mapper.PsamMapper;
import com.yxytech.parkingcloud.core.service.IChargeFacilityService;
import com.yxytech.parkingcloud.core.service.IOrganizationService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.service.IPsamService;
import org.apache.commons.lang.StringUtils;
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
public class PsamServiceImpl extends ServiceImpl<PsamMapper, Psam> implements IPsamService {

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IChargeFacilityService chargeFacilityService;

    @Autowired
    private IParkingService parkingService;

    @Override
    public Page<Psam> selectByPage(Page<Psam> page, Wrapper<Psam> wrapper) {
        page.setRecords(baseMapper.selectByPage(page,wrapper));

        return page;
    }

    @Override
    public Map<String,Object> getBind(String manageOrgName,String parkingName) {
        Map<String,Object> map = new HashMap<>();
        EntityWrapper<Organization> ew1 = new EntityWrapper<>();
        ew1.eq("full_name",manageOrgName);
        map.put("manageOrg",organizationService.selectOne(ew1));

        EntityWrapper<Parking> ew2 = new EntityWrapper<>();
        ew2.eq("full_name",parkingName);
        map.put("parking",parkingService.selectOne(ew2));

        return map;
    }

    private Long getTerminalDeviceId(String terminalDeviceCode){
        EntityWrapper<ChargeFacility> ew = new EntityWrapper<>();
        ew.setSqlSelect("id").eq("system_number",terminalDeviceCode);
        Object terminalDeviceId = chargeFacilityService.selectObj(ew);

        return (Long) terminalDeviceId;
    }


    @Override
    public String createBatch(List<PsamBatch> psamBatches) {
        if(psamBatches.isEmpty())
            return "非法参数";

        for(PsamBatch psamBatch : psamBatches){
            Psam psam = new Psam();
            BeanUtils.copyProperties(psamBatch,psam);

            Map<String,Object> map  = getBind(psamBatch.getManageOrgName(),psamBatch.getParkingName());
            if(map.get("manageOrg") == null || map.get("parking") == null)
                return "关联信息不存在";

            psam.setManageOrgId(((Organization) map.get("manageOrg")).getId());
            psam.setParkingId(((Parking) map.get("parking")).getId());

            String errorMsg = validate(psam.getPsamCardNumber(),psam.getManageOrgId(),psam.getParkingId());
            if(StringUtils.isNotBlank(errorMsg))
                return errorMsg;

            Long terminalDeviceId = getTerminalDeviceId(psamBatch.getTerminalDeviceCode());
            if(terminalDeviceId == null)
                return "终端设备不存在";

            psam.setPutInAt(new Date());
            psam.setTerminalDeviceId(terminalDeviceId);

            baseMapper.insert(psam);
        }
        return null;
    }

    @Override
    public String validate(String psamCardNumber,Long manageOrgId,Long parkingId){
        EntityWrapper<Psam> ew1 = new EntityWrapper<>();
        ew1.eq("psam_card_number",psamCardNumber);
          if(baseMapper.selectCount(ew1) > 0)
             return  "请勿重复创建";

        EntityWrapper<Organization> ew2 = new EntityWrapper<>();
        ew2.eq("id",manageOrgId);
          if(organizationService.selectCount(ew2) < 1)
             return "管理单位不存在";

        EntityWrapper<Parking> ew3 = new EntityWrapper<>();
        ew3.eq("id",parkingId);
          if(parkingService.selectCount(ew3) < 1)
             return "所属停车场不存在";

        return null;
    }

    @Override
    public Psam detail(Long id) {
        Psam psam = baseMapper.selectById(id);
        if(psam == null)
            return null;

        EntityWrapper<Organization> ewOrg = new EntityWrapper<>();
        ewOrg.eq("id",psam.getManageOrgId());
        Map<Object,Object> orgMap = organizationService.selectIdNameMap(ewOrg,"id","full_name");
        psam.setManageOrgName((String) orgMap.get(psam.getManageOrgId()));

        EntityWrapper<Parking> ewParking = new EntityWrapper<>();
        ewParking.eq("id",psam.getParkingId());
        Map<Object,Object> parkingMap = parkingService.selectIdNameMap(ewParking,"id","full_name");
        psam.setParkingName((String) parkingMap.get(psam.getParkingId()));

        return psam;
    }

    @Override
    public String updateValidate(Psam psam) {
        EntityWrapper<Psam> ew1 = new EntityWrapper<>();
        ew1.eq("psam_card_number",psam.getPsamCardNumber());
        List<Psam> exist = baseMapper.selectList(ew1);
        if(exist.size() > 0 && exist.get(0).getId() != psam.getId())
            return  "请勿重复创建";

        EntityWrapper<Organization> ew2 = new EntityWrapper<>();
        ew2.eq("id",psam.getManageOrgId());
        if(organizationService.selectCount(ew2) < 1)
            return "管理单位不存在";

        EntityWrapper<Parking> ew3 = new EntityWrapper<>();
        ew3.eq("id",psam.getParkingId());
        if(parkingService.selectCount(ew3) < 1)
            return "所属停车场不存在";

        return null;
    }
}
