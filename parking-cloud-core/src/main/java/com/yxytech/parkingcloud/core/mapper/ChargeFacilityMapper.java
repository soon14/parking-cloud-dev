package com.yxytech.parkingcloud.core.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yxytech.parkingcloud.core.entity.ChargeFacility;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author cj
 * @since 2017-10-24
 */
public interface ChargeFacilityMapper extends SuperMapper<ChargeFacility> {

    List<ChargeFacility> selectByPage(Pagination page, @Param("ew") Wrapper<ChargeFacility> wrapper);
}