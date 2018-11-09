package com.yxytech.parkingcloud.core.mapper;


import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author cj
 * @since 2017-10-18
 */
public interface OrganzationMapper extends SuperMapper<Organization> {

    List<Organization> selectByPage(Pagination page, @Param("ew") Wrapper<Organization> wrapper);

    List<Organization> findAllOwnerOrg(String ownerOrg);

    List<Organization> findAllManageOrg(String manageOrg);

    List<Organization> findAllFacilityOrg(String facilityOrg);
}