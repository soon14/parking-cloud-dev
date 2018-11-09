package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.entity.Psam;
import com.yxytech.parkingcloud.core.entity.PsamBatch;
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
public interface IPsamService extends IService<Psam> {

    public Page<Psam> selectByPage(Page<Psam> page, @Param("ew") Wrapper<Psam> wrapper);

    public Map<String,Object> getBind(String manageOrgName, String parkingName);

    public String createBatch(List<PsamBatch> psamBatches);

    public String validate(String pasamNumber,Long manageOrgId,Long parkingId);

    Psam detail(Long id);

    String updateValidate(Psam psam);
}
