package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingCell;
import com.yxytech.parkingcloud.core.entity.VideoPile;
import com.yxytech.parkingcloud.core.mapper.VideoPileMapper;
import com.yxytech.parkingcloud.core.service.IOrganizationService;
import com.yxytech.parkingcloud.core.service.IParkingCellService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.service.IVideoPileService;
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
public class VideoPileServiceImpl extends ServiceImpl<VideoPileMapper, VideoPile> implements IVideoPileService {

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IParkingCellService parkingCellService;

    @Override
    public void create(VideoPile videoPile) {
        baseMapper.create(videoPile);
    }

    @Override
    public void updateVideoPile(VideoPile videoPile) {
        baseMapper.update(videoPile);
    }


    @Override
    public String validate(String sn, Long facOrgId,String ip) {
        String existMsg = null;

        EntityWrapper<VideoPile> ew = new EntityWrapper<>();
        ew.eq("sn",sn);
        ew.eq("fac_org_id",facOrgId);
        ew.eq("ip",ip);

        if(baseMapper.selectCount(ew) > 0)  existMsg = "请勿重复创建";

        return existMsg;
    }

    @Override
    public Page<VideoPile> selectByPage(Page<VideoPile> page, Wrapper<VideoPile> wrapper) {
        page.setRecords(baseMapper.selectByPage(page,wrapper));

        return page;
    }

    @Override
    public void updateBatch(List<VideoPile> list) {
        baseMapper.updateBatch(list);
    }

    @Override
    public VideoPile detail(Long id) {
        VideoPile videoPile = baseMapper.selectById(id);
        if(videoPile == null)
            return null;

        List<Long> orgIds = new ArrayList<>();
        orgIds.add(videoPile.getFacOrgId());
        orgIds.add(videoPile.getManageOrgId());
        Map<Long,String> orgMap = organizationService.selectIdNameMap(orgIds,"id","full_name");
        videoPile.setFacOrgName(orgMap.get(videoPile.getFacOrgId()));
        videoPile.setManageOrgName(orgMap.get(videoPile.getManageOrgId()));

        EntityWrapper<Parking> ew = new EntityWrapper<>();
        ew.eq("id",videoPile.getParkingId());
        Map<Object,Object> parkingMap = parkingService.selectIdNameMap(ew,"id","full_name");
        videoPile.setParkingName((String) parkingMap.get(videoPile.getParkingId()));

        return videoPile;
    }

    @Override
    public String updateValidate(VideoPile videoPile) {
        EntityWrapper<Organization> ew1 = new EntityWrapper<>();
        ew1.eq("id",videoPile.getManageOrgId());

        EntityWrapper<Parking> ew2 = new EntityWrapper<>();
        ew2.eq("id",videoPile.getParkingId());

        EntityWrapper<Organization> ew3 = new EntityWrapper<>();
        ew3.eq("id",videoPile.getFacOrgId());

        EntityWrapper<ParkingCell> ew4 = new EntityWrapper<>();
        ew4.eq("code",videoPile.getParkingCellCode());

        if(organizationService.selectCount(ew1) < 1)
            return "管理单位不存在";
        if(parkingService.selectCount(ew2) < 1)
            return "停车场不存在";
        if(organizationService.selectCount(ew3) < 1)
            return "设备厂商不存在";
        if(parkingCellService.selectCount(ew4) < 1)
            return "所属车位不存在";

        EntityWrapper<VideoPile> ew = new EntityWrapper<>();
        ew.eq("sn",videoPile.getSn());
        ew.eq("fac_org_id",videoPile.getFacOrgId());
        ew.eq("ip",videoPile.getIp());
        List<VideoPile> exist = baseMapper.selectList(ew);

        if(exist.size() > 0 && exist.get(0).getId() != videoPile.getId())
            return "设备信息不可重复";

        return null;
    }

}
