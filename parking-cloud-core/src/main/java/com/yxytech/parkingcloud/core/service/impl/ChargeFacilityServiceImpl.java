package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.mapper.ChargeFacilityMapper;
import com.yxytech.parkingcloud.core.service.*;
import com.yxytech.parkingcloud.core.utils.YXYServiceImpl;
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
public class ChargeFacilityServiceImpl extends YXYServiceImpl<ChargeFacilityMapper, ChargeFacility> implements IChargeFacilityService {

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IMagneticDeviceService magneticDeviceService;

    @Autowired
    private IVideoMonitorService videoMonitorService;

    @Autowired
    private IVideoPileService videoPileService;

    @Autowired
    private IParkingCellService parkingCellService;

    @Override
    public Page<ChargeFacility> selectByPage(Page<ChargeFacility> page, Wrapper<ChargeFacility> wrapper) {
       page.setRecords(baseMapper.selectByPage(page,wrapper));

        return page;
    }

    @Override
    public String validate(String parkingCellCode,Long manageOrgId, Long parkingId, Long facOrgId,String sn,Class type) {

        EntityWrapper<Organization> ew1 = new EntityWrapper<>();
        ew1.eq("id",manageOrgId);

        EntityWrapper<Parking> ew2 = new EntityWrapper<>();
        ew2.eq("id",parkingId);

        EntityWrapper<Organization> ew3 = new EntityWrapper<>();
        ew3.eq("id",facOrgId);

        if(organizationService.selectCount(ew1) < 1)
             return "管理单位不存在";
        if(parkingService.selectCount(ew2) < 1)
             return "停车场不存在";
        if(organizationService.selectCount(ew3) < 1)
             return "设备厂商不存在";
        Object obj = null;
        try{
             obj = type.newInstance();
        }catch (Exception e){
            return "类型异常";
        }

        if(StringUtils.isNotBlank(parkingCellCode)){
            EntityWrapper<ParkingCell> ewCell = new EntityWrapper<>();
            ewCell.eq("code",parkingCellCode);
            if(parkingCellService.selectCount(ewCell) < 1)
                return "所属车位不存在";
        }

        if(obj instanceof ChargeFacility){
            EntityWrapper<ChargeFacility> ew = new EntityWrapper<>();
            ew.eq("sn",sn);
            ew.eq("fac_org_id",facOrgId);

            if(baseMapper.selectCount(ew) > 0)
                return "请勿重复创建设备信息";
        }
        if(obj instanceof MagneticDevice){
            EntityWrapper<MagneticDevice> ew = new EntityWrapper<>();
            ew.eq("sn",sn);
            ew.eq("fac_org_id",facOrgId);

            if(magneticDeviceService.selectCount(ew) > 0)
                return "请勿重复创建设备信息";
        }
        if(obj instanceof VideoPile){
            EntityWrapper<VideoPile> ew = new EntityWrapper<>();
            ew.eq("sn",sn);
            ew.eq("fac_org_id",facOrgId);

            if(videoPileService.selectCount(ew) > 0)
                return "请勿重复创建设备信息";
        }
        if(obj instanceof VideoMonitor){
            EntityWrapper<VideoMonitor> ew = new EntityWrapper<>();
            ew.eq("sn",sn);
            ew.eq("fac_org_id",facOrgId);

            if(videoMonitorService.selectCount(ew) > 0)
                return "请勿重复创建设备信息";
        }

        return null;
    }

    @Override
    public Map<String, Object> getBind(String facOrgName, String parkingName, String manageOrgName) {
        Map<String,Object> map = new HashMap<>();
        EntityWrapper<Parking> ew1 = new EntityWrapper<>();
        ew1.eq("full_name",parkingName);
        Parking parking = parkingService.selectOne(ew1);
        map.put("parking",parking);

        EntityWrapper<Organization> ew2 = new EntityWrapper<>();
        ew2.eq("full_name",manageOrgName);
        Organization manageOrg = organizationService.selectOne(ew2);
        map.put("manageOrg",manageOrg);

        EntityWrapper<Organization> ew3 = new EntityWrapper<>();
        ew3.eq("full_name",facOrgName);
        Organization facOrg = organizationService.selectOne(ew3);
        map.put("facOrg",facOrg);

        return map;
    }

    @Override
    public String createBatch(List devices,Class type) {
        if(devices.isEmpty())
            return "非法参数";

        Object obj = null;
         try{
             obj = type.newInstance();
         }catch (Exception ex){
             return ex.getMessage();
         }
        if(obj instanceof ChargeFacility){
            for(Object object : devices){
                ChargeFacilityBatch chargeFacilityBatch = (ChargeFacilityBatch) object;

                ChargeFacility chargeFacility = new ChargeFacility();
                BeanUtils.copyProperties(chargeFacilityBatch,chargeFacility);

                Map<String,Object> map = getBind(chargeFacilityBatch.getFacOrgName(),chargeFacilityBatch.getParkingName(),chargeFacilityBatch.getManageOrgName());
                  if(map.get("facOrg") == null || map.get("parking") == null || map.get("manageOrg") == null)
                     return "关联信息不存在";

                chargeFacility.setFacOrgId(((Organization) map.get("facOrg")).getId());
                chargeFacility.setParkingId(((Parking) map.get("parking")).getId());
                chargeFacility.setManageOrgId(((Organization) map.get("manageOrg")).getId());

                String existMsg = validate(null,chargeFacility.getManageOrgId(),chargeFacility.getParkingId(),
                                             chargeFacility.getFacOrgId(),chargeFacility.getSn(),ChargeFacility.class);
                if(StringUtils.isNotBlank(existMsg))
                      return existMsg;

                chargeFacility.setPutInAt(new Date());
                if(!((Organization) map.get("facOrg")).getFacilityOrg()){
                    ((Organization) map.get("facOrg")).setFacilityOrg(true);
                    organizationService.updateById((Organization) map.get("facOrg"));
                }
                baseMapper.insert(chargeFacility);
            }

            return null;
        }
            for(Object object : devices){
                if(obj instanceof MagneticDevice){
                    MagneticDeviceBatch magneticDeviceBatch = (MagneticDeviceBatch) object;
                    MagneticDevice magneticDevice = new MagneticDevice();
                    BeanUtils.copyProperties(magneticDeviceBatch,magneticDevice);

                    String gpsMsg = magneticDeviceService.validateGps(magneticDevice.getGps());
                    if(StringUtils.isNotBlank(gpsMsg)) return gpsMsg;

                    String gps = magneticDeviceService.formattingGps(magneticDevice.getGps());
                    magneticDevice.setGps(gps);

                    Map<String,Object> map = getBind(magneticDeviceBatch.getFacOrgName(),magneticDeviceBatch.getParkingName(),magneticDeviceBatch.getManageOrgName());
                      if(map.get("facOrg") == null ||  map.get("parking") == null || map.get("manageOrg") == null)
                          return "关联信息不存在";

                    magneticDevice.setFacOrgId(((Organization) map.get("facOrg")).getId());
                    magneticDevice.setParkingId(((Parking) map.get("parking")).getId());
                    magneticDevice.setManageOrgId(((Organization) map.get("manageOrg")).getId());

                    String errorMsg = validate(magneticDevice.getParkingCellCode(),magneticDevice.getManageOrgId(),magneticDevice.getParkingId(),
                                                 magneticDevice.getFacOrgId(),magneticDevice.getSn(),MagneticDevice.class);
                    if(StringUtils.isNotBlank(errorMsg))
                         return errorMsg;

                    magneticDevice.setPutInAt(new Date());
                    if(!((Organization) map.get("facOrg")).getFacilityOrg()){
                        ((Organization) map.get("facOrg")).setFacilityOrg(true);
                        organizationService.updateById((Organization) map.get("facOrg"));
                    }

                    magneticDeviceService.create(magneticDevice);
                }
                if(obj instanceof VideoMonitor){
                    VideoMonitorBatch videoMonitorBatch = (VideoMonitorBatch) object;
                    VideoMonitor videoMonitor = new VideoMonitor();
                    BeanUtils.copyProperties(videoMonitorBatch,videoMonitor);

                    String existMsg = videoMonitorService.validate(videoMonitor.getSn(),videoMonitor.getFacOrgId());
                    if(StringUtils.isNotBlank(existMsg))
                        return existMsg;

                    Map<String,Object> map = getBind(videoMonitorBatch.getFacOrgName(),videoMonitorBatch.getParkingName(),videoMonitorBatch.getManageOrgName());
                    if(map.get("facOrg") == null ||  map.get("parking") == null || map.get("manageOrg") == null)
                        return "关联信息不存在";

                    videoMonitor.setFacOrgId(((Organization) map.get("facOrg")).getId());
                    videoMonitor.setParkingId(((Parking) map.get("parking")).getId());
                    videoMonitor.setManageOrgId(((Organization) map.get("manageOrg")).getId());

                    String error = validate(videoMonitor.getParkingCellCode(),videoMonitor.getManageOrgId(),videoMonitor.getParkingId(),videoMonitor.getFacOrgId(),videoMonitor.getSn(),VideoMonitor.class);
                      if(StringUtils.isNotBlank(error))
                          return error;

                    String errorMsg = magneticDeviceService.validateGps(videoMonitor.getGps());
                      if(StringUtils.isNotBlank(errorMsg))
                        return errorMsg;
                    videoMonitor.setGps(magneticDeviceService.formattingGps(videoMonitor.getGps()));
                    videoMonitor.setPutInAt(new Date());

                    videoMonitorService.create(videoMonitor);

                    if(!((Organization) map.get("facOrg")).getFacilityOrg()){
                        ((Organization) map.get("facOrg")).setFacilityOrg(true);
                        organizationService.updateById(((Organization) map.get("facOrg")));
                    }
                }
                if(obj instanceof VideoPile){
                    VideoPileBatch videoPileBatch = (VideoPileBatch) object;
                    VideoPile videoPile = new VideoPile();
                    BeanUtils.copyProperties(videoPileBatch,videoPile);

                    String existMsg = videoPileService.validate(videoPile.getSn(),videoPile.getFacOrgId(),videoPile.getIp());
                    if(StringUtils.isNotBlank(existMsg))
                        return existMsg;

                    Map<String,Object> map = getBind(videoPileBatch.getFacOrgName(),videoPileBatch.getParkingName(),videoPileBatch.getManageOrgName());
                      if(map.get("facOrg") == null ||  map.get("parking") == null || map.get("manageOrg") == null)
                        return "关联信息不存在";

                    videoPile.setFacOrgId(((Organization) map.get("facOrg")).getId());
                    videoPile.setParkingId(((Parking) map.get("parking")).getId());
                    videoPile.setManageOrgId(((Organization) map.get("manageOrg")).getId());

                    validate(videoPile.getParkingCellCode(),videoPile.getManageOrgId(),videoPile.getParkingId(),videoPile.getFacOrgId(),videoPile.getSn(),VideoPile.class);

                    String errorMsg = magneticDeviceService.validateGps(videoPile.getGps());
                    if(StringUtils.isNotBlank(errorMsg))
                        return errorMsg;
                    videoPile.setGps(magneticDeviceService.formattingGps(videoPile.getGps()));
                    videoPile.setPutInAt(new Date());

                    videoPileService.create(videoPile);

                    if(!((Organization) map.get("facOrg")).getFacilityOrg()){
                        ((Organization) map.get("facOrg")).setFacilityOrg(true);
                        organizationService.updateById(((Organization) map.get("facOrg")));
                    }
                }
            }

         return null;
    }

    @Override
    public ChargeFacility detail(Long id) {
        ChargeFacility chargeFacility = baseMapper.selectById(id);
        if(chargeFacility == null)
            return null;

        List<Long> orgIds = new ArrayList<>();
        orgIds.add(chargeFacility.getFacOrgId());
        orgIds.add(chargeFacility.getManageOrgId());

        Map<Long,String> orgMap = organizationService.selectIdNameMap(orgIds,"id","full_name");
        chargeFacility.setManageOrgName(orgMap.get(chargeFacility.getManageOrgId()));
        chargeFacility.setFacOrgName(orgMap.get(chargeFacility.getFacOrgId()));

        EntityWrapper<Parking> ewParking = new EntityWrapper<>();
        ewParking.eq("id",chargeFacility.getParkingId());
        Map<Object,Object> parkingMap = parkingService.selectIdNameMap(ewParking,"id","full_name");
        chargeFacility.setParkingName((String) parkingMap.get(chargeFacility.getParkingId()));

        return chargeFacility;
    }

    @Override
    public String updateValidate(ChargeFacility chargeFacility) {
        EntityWrapper<Organization> ew1 = new EntityWrapper<>();
        ew1.eq("id",chargeFacility.getManageOrgId());

        EntityWrapper<Parking> ew2 = new EntityWrapper<>();
        ew2.eq("id",chargeFacility.getParkingId());

        EntityWrapper<Organization> ew3 = new EntityWrapper<>();
        ew3.eq("id",chargeFacility.getFacOrgId());

        if(organizationService.selectCount(ew1) < 1)
            return "管理单位不存在";
        if(parkingService.selectCount(ew2) < 1)
            return "停车场不存在";
        if(organizationService.selectCount(ew3) < 1)
            return "设备厂商不存在";

        EntityWrapper<ChargeFacility> ew = new EntityWrapper<>();
        ew.eq("sn",chargeFacility.getSn());
        ew.eq("fac_org_id",chargeFacility.getFacOrgId());
        ew.eq("ip",chargeFacility.getIp());
        List<ChargeFacility> exist = baseMapper.selectList(ew);

        if(exist.size() > 0 && exist.get(0).getId() != chargeFacility.getId()  )
            return "设备信息不可重复";

        return null;
    }
}
