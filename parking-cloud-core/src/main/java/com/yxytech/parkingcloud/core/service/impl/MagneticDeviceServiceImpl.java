package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.MagneticDevice;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingCell;
import com.yxytech.parkingcloud.core.mapper.MagneticDeviceMapper;
import com.yxytech.parkingcloud.core.service.IMagneticDeviceService;
import com.yxytech.parkingcloud.core.service.IOrganizationService;
import com.yxytech.parkingcloud.core.service.IParkingCellService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-10-24
 */
@Service
public class MagneticDeviceServiceImpl extends ServiceImpl<MagneticDeviceMapper, MagneticDevice> implements IMagneticDeviceService {

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IParkingCellService parkingCellService;

    @Override
    public void create(MagneticDevice magneticDevice) {
        baseMapper.create(magneticDevice);
    }

    @Override
    public void updateMagneticDevice(MagneticDevice magneticDevice) {
        baseMapper.update(magneticDevice);
    }

    @Override
    public String validateGps(String gps) {
        if(!gps.contains(" ") || gps.startsWith(" ") || gps.endsWith(" ")) return "请输入正确的经纬度格式";

        String[] newGps = gps.split(" ");
          if(( Double.parseDouble(newGps[0]) < -180 || Double.parseDouble(newGps[0]) > 180 ) ||
                  (Double.parseDouble(newGps[1]) < -90 || Double.parseDouble(newGps[1]) > 90 ))
                     return "请输入正确的经纬度值";

        return null;
    }

    @Override
    public String formattingGps(String gps) {
        String[] newGps = gps.split(" ");
        String finalGps = newGps[0] + "," + newGps[1];

        return finalGps;
    }

    @Override
    public String validate(String sn, Long facOrgId) {
        String existMsg = null;

        EntityWrapper<MagneticDevice> ew = new EntityWrapper<>();
        ew.eq("sn",sn);
        ew.eq("fac_org_id",facOrgId);

        if(baseMapper.selectCount(ew) > 0) existMsg = "请勿重复创建";

        return existMsg;
    }

    @Override
    public Page<MagneticDevice> selectByPage(Page<MagneticDevice> page, Wrapper<MagneticDevice> wrapper) {
        page.setRecords(baseMapper.selectByPage(page,wrapper));

        return page;
    }

    @Override
    public String bindOrg(Long facOrgId) {
        EntityWrapper<Organization> ew = new EntityWrapper<>();
        ew.setSqlSelect("is_facility_org","id").eq("id",facOrgId);
        Organization facOrg = organizationService.selectOne(ew);
        if(facOrg == null)
            return "设备厂商不存在";

        if(!facOrg.getFacilityOrg()){
            facOrg.setFacilityOrg(true);
            organizationService.updateById(facOrg);
        }

        return null;
    }

    @Override
    public void updateBatch(List<MagneticDevice> list) {
        baseMapper.updateBatch(list);
    }

    @Override
    public MagneticDevice detail(Long id) {
        MagneticDevice magneticDevice = baseMapper.selectById(id);
        if(magneticDevice == null)
            return null;

        List<Long> orgIds = new ArrayList<>();
        orgIds.add(magneticDevice.getFacOrgId());
        orgIds.add(magneticDevice.getManageOrgId());

        Map<Long,String> orgMap = organizationService.selectIdNameMap(orgIds,"id","full_name");
        magneticDevice.setManageOrgName(orgMap.get(magneticDevice.getManageOrgId()));
        magneticDevice.setFacOrgName(orgMap.get(magneticDevice.getFacOrgId()));

        EntityWrapper<Parking> ewParking = new EntityWrapper<>();
        ewParking.eq("id",magneticDevice.getParkingId());
        Map<Object,Object> parkingMap = parkingService.selectIdNameMap(ewParking,"id","full_name");
        magneticDevice.setParkingName((String) parkingMap.get(magneticDevice.getParkingId()));

        return magneticDevice;
    }

    @Override
    public String updateValidate(MagneticDevice magneticDevice) {
        EntityWrapper<Organization> ew1 = new EntityWrapper<>();
        ew1.eq("id",magneticDevice.getManageOrgId());

        EntityWrapper<Parking> ew2 = new EntityWrapper<>();
        ew2.eq("id",magneticDevice.getParkingId());

        EntityWrapper<Organization> ew3 = new EntityWrapper<>();
        ew3.eq("id",magneticDevice.getFacOrgId());

        EntityWrapper<ParkingCell> ew4 = new EntityWrapper<>();
        ew4.eq("code",magneticDevice.getParkingCellCode());

        if(organizationService.selectCount(ew1) < 1)
            return "管理单位不存在";
        if(parkingService.selectCount(ew2) < 1)
            return "停车场不存在";
        if(organizationService.selectCount(ew3) < 1)
            return "设备厂商不存在";
        if(parkingCellService.selectCount(ew4) < 1)
            return "所属车位不存在";

        EntityWrapper<MagneticDevice> ew = new EntityWrapper<>();
        ew.eq("sn",magneticDevice.getSn());
        ew.eq("fac_org_id",magneticDevice.getFacOrgId());
        ew.eq("ip",magneticDevice.getIp());
        List<MagneticDevice> exist = baseMapper.selectList(ew);

        if(exist.size() > 0 && exist.get(0).getId() != magneticDevice.getId()  )
            return "设备信息不可重复";

        return null;
    }

}
