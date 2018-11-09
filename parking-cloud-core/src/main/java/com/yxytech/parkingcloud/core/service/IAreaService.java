package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.Area;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-18
 */
public interface IAreaService extends IService<Area> {

    public String validateArea(String name);

    public Map<String,Object> objectToMap(Object object);

    String validateAreaIds(List<Long> areaIds);

    Map<String,Object> find(Long id);

    List findByLevel(Integer level);

    List findAll();

    String updateValidate(Area area);
}
