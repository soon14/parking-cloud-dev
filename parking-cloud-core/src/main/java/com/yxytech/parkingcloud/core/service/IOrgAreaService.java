package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.OrgArea;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-11-10
 */
public interface IOrgAreaService extends IService<OrgArea> {

    public String validateBindData(Long orgId,List<Long> areaIds,List<Long> parkingIds);

    public void syncAreas(Long orgId, List<Long> areaIds);

    public List<Object> findBindAreaIds(Long orgId);
    
}
