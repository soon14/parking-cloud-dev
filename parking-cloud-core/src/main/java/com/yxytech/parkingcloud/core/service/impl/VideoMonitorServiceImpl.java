package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingCell;
import com.yxytech.parkingcloud.core.entity.VideoMonitor;
import com.yxytech.parkingcloud.core.mapper.VideoMonitorMapper;
import com.yxytech.parkingcloud.core.service.IOrganizationService;
import com.yxytech.parkingcloud.core.service.IParkingCellService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.service.IVideoMonitorService;
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
public class VideoMonitorServiceImpl extends ServiceImpl<VideoMonitorMapper, VideoMonitor> implements IVideoMonitorService {

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IParkingCellService parkingCellService;

    @Override
    public void create(VideoMonitor videoMonitor) {
        baseMapper.create(videoMonitor);
    }

    @Override
    public void updateVideoMonitor(VideoMonitor videoMonitor) {
        baseMapper.update(videoMonitor);
    }

    @Override
    public String validate(String sn, Long facOrgId) {
        String existMsg = null;

        EntityWrapper<VideoMonitor> ew = new EntityWrapper<>();
        ew.eq("sn",sn);
        ew.eq("fac_org_id",facOrgId);

        if( baseMapper.selectCount(ew) > 0)
              existMsg = "请勿重复创建";

        return existMsg;
    }

    @Override
    public Page<VideoMonitor> selectByPage(Page<VideoMonitor> page, Wrapper<VideoMonitor> wrapper) {
        page.setRecords(baseMapper.selectByPage(page,wrapper));

        return page;
    }

    @Override
    public void updateBatch(List<VideoMonitor> list) {
        baseMapper.updateBatch(list);
    }

    @Override
    public VideoMonitor detail(Long id) {
        VideoMonitor videoMonitor = baseMapper.selectById(id);
        if(videoMonitor == null)
            return null;

        List<Long> orgIds = new ArrayList<>();
        orgIds.add(videoMonitor.getFacOrgId());
        orgIds.add(videoMonitor.getManageOrgId());
        Map<Long,String> orgMap = organizationService.selectIdNameMap(orgIds,"id","full_name");
        videoMonitor.setFacOrgName(orgMap.get(videoMonitor.getFacOrgId()));
        videoMonitor.setManageOrgName(orgMap.get(videoMonitor.getManageOrgId()));

        EntityWrapper<Parking> ew = new EntityWrapper<>();
        ew.eq("id",videoMonitor.getParkingId());
        Map<Object,Object> parkingMap = parkingService.selectIdNameMap(ew,"id","full_name");
        videoMonitor.setParkingName((String) parkingMap.get(videoMonitor.getParkingId()));

        return videoMonitor;
    }

    @Override
    public String updateValidate(VideoMonitor videoMonitor) {
        EntityWrapper<Organization> ew1 = new EntityWrapper<>();
        ew1.eq("id",videoMonitor.getManageOrgId());

        EntityWrapper<Parking> ew2 = new EntityWrapper<>();
        ew2.eq("id",videoMonitor.getParkingId());

        EntityWrapper<Organization> ew3 = new EntityWrapper<>();
        ew3.eq("id",videoMonitor.getFacOrgId());

        EntityWrapper<ParkingCell> ew4 = new EntityWrapper<>();
        ew4.eq("code",videoMonitor.getParkingCellCode());

        if(organizationService.selectCount(ew1) < 1)
            return "管理单位不存在";
        if(parkingService.selectCount(ew2) < 1)
            return "停车场不存在";
        if(organizationService.selectCount(ew3) < 1)
            return "设备厂商不存在";
        if(parkingCellService.selectCount(ew4) < 1)
            return "所属车位不存在";

        EntityWrapper<VideoMonitor> ew = new EntityWrapper<>();
        ew.eq("sn",videoMonitor.getSn());
        ew.eq("fac_org_id",videoMonitor.getFacOrgId());
        ew.eq("ip",videoMonitor.getIp());
        List<VideoMonitor> exist = baseMapper.selectList(ew);

        if(exist.size() > 0 && exist.get(0).getId() != videoMonitor.getId()  )
            return "设备信息不可重复";

        return null;
    }
}
