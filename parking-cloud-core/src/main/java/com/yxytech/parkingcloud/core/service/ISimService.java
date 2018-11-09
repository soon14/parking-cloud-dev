package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.Sim;
import com.yxytech.parkingcloud.core.entity.SimBatch;
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
public interface ISimService extends IService<Sim> {


    public Page<Sim> selectByPage(Page<Sim> page, @Param("ew") Wrapper<Sim> wrapper);

    public String validate(Long manageOrgId,Long facOrgId,String simNumber,Long parkingId);

    public Map<String,Object> getBind(String manageOrgName,String facOrgName,String parkingName);

    public String createBatch(List<SimBatch> simBatches);

    Sim detail(Long id);

    String updateValidate(Sim sim);

}
