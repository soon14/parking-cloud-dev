package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.utils.YXYIService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-18
 */
public interface IOrganizationService extends IService<Organization>, YXYIService<Organization> {

     public String validate(String fullName);


     public Page<Organization> selectByPage(Page<Organization> page, @Param("ew") Wrapper<Organization> wrapper);

     public List detail(Organization organization);

     String updateValidate(Organization organization);

     List<Organization> findAllOwnerOrg(String ownerOrg);

     List<Organization> findAllManageOrg(String manageOrg);

     List<Organization> findAllFacilityOrg(String facilityOrg);

}
