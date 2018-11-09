package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.ChargeFacility;
import com.yxytech.parkingcloud.core.utils.YXYIService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-24
 */
public interface IChargeFacilityService extends IService<ChargeFacility>,YXYIService<ChargeFacility> {


    public Page<ChargeFacility> selectByPage(Page<ChargeFacility> page, @Param("ew") Wrapper<ChargeFacility> wrapper);

    public String validate(String parkingCellCode,Long manageOrgId,Long parkingId,Long facOrgId,String sn,Class type);

    public Map<String,Object> getBind(String facOrgName,String parkingName,String manageOrgName);

    public String createBatch(List devices,Class Type);

    ChargeFacility detail(Long id);

    String updateValidate(ChargeFacility chargeFacility);

}
