package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingOwner;
import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import com.yxytech.parkingcloud.core.mapper.ParkingOwnerMapper;
import com.yxytech.parkingcloud.core.service.IOrganizationService;
import com.yxytech.parkingcloud.core.service.IParkingOwnerService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-10-19
 */
@Service
public class ParkingOwnerServiceImpl extends ServiceImpl<ParkingOwnerMapper, ParkingOwner> implements IParkingOwnerService {

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IOrganizationService organizationService;

    @Override
    public String examine(ParkingOwner parkingOwner) {
        Parking parking = parkingService.selectById(parkingOwner.getParkingId());
          if(parking == null)  return  "尚未关联停车场";

        Wrapper<Organization> wrapper = new EntityWrapper<Organization>();
        wrapper.setSqlSelect("approve_status","id").eq("id",parkingOwner.getOwnerOrgId());
        Organization organization = organizationService.selectOne(wrapper);

        ApproveEnum approveEnum = organization.getApproveStatus();
        if(approveEnum.equals(ApproveEnum.NOT_APPROVE)
                || approveEnum.equals(ApproveEnum.APPROVE_ING)
                     ||approveEnum.equals(ApproveEnum.APPROVE_FAIL))
                           return  "产权单位未认证";

        parkingOwner.setApproveStatus(ApproveEnum.APPROVE_SUCCESS);

        baseMapper.updateById(parkingOwner);

        return null;
    }
}
