package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingOperator;
import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import com.yxytech.parkingcloud.core.mapper.ParkingOperatorMapper;
import com.yxytech.parkingcloud.core.service.IOrganizationService;
import com.yxytech.parkingcloud.core.service.IParkingOperatorService;
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
public class ParkingOperatorServiceImpl extends ServiceImpl<ParkingOperatorMapper, ParkingOperator> implements IParkingOperatorService {

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IOrganizationService organizationService;

    @Override
    public String examine(ParkingOperator parkingOperator) {
        Long parkingId = parkingOperator.getParkingId();
        Parking parking = parkingService.selectById(parkingId);
          if(parking == null)  return  "尚未关联停车场";

        Wrapper<Organization> wrapper = new EntityWrapper<Organization>();
        wrapper.setSqlSelect("approve_status","id").eq("id",parkingOperator.getOperatorOrgId());

        Organization organization = organizationService.selectOne(wrapper);
        ApproveEnum approveEnum = organization.getApproveStatus();
        if(approveEnum.equals(ApproveEnum.NOT_APPROVE)
                || approveEnum.equals(ApproveEnum.APPROVE_ING)
                     ||approveEnum.equals(ApproveEnum.APPROVE_FAIL))
                                           return  "经营单位未认证";

        parkingOperator.setApproveStatus(ApproveEnum.APPROVE_SUCCESS);

        baseMapper.updateById(parkingOperator);

        return null;
    }
}
