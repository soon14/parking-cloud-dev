package com.yxytech.parkingcloud.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Area;
import com.yxytech.parkingcloud.core.mapper.AreaMapper;
import com.yxytech.parkingcloud.core.service.IAreaService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-10-18
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

    @Override
    public String validateArea(String name) {
        Wrapper<Area> wrapper = new EntityWrapper<Area>();
        wrapper.eq("name",name);

        String msg = null;
        if(baseMapper.selectCount(wrapper) > 0)
             msg = "区域名称不可重复";

        return msg;
    }

    @Override
    public Map<String, Object> objectToMap(Object object) {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(object);
        Set<Map.Entry<String,Object>> entrySet = jsonObject.entrySet();
        Map<String, Object> map=new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : entrySet) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    @Override
    public String validateAreaIds(List<Long> areaIds) {
        String errorMsg = null;
        EntityWrapper<Area> ew = new EntityWrapper<>();
        ew.setSqlSelect("id").in("id", areaIds);
        List<Object> existIds = baseMapper.selectObjs(ew);

        if (areaIds.size() > existIds.size()) {
            existIds.forEach(item -> areaIds.remove(item));
            errorMsg = "区域id不存在：" + areaIds.toString();
        }
        return errorMsg;
    }

    @Override
    public Map<String, Object> find(Long id) {
        Area area = baseMapper.selectById(id);
        HashMap hashMap = new HashMap();
        if(area.getParentId() == 0 && area.getLevel() == 1){
            Integer [] parentIds = {0};
            hashMap.put("data",area);
            hashMap.put("parentIds",parentIds);
        }else if(area.getParentId() != 0){
            if(area.getLevel() == 2){
                hashMap.put("data",area);
                Long [] parentIds = {area.getParentId()};
                hashMap.put("parentIds",parentIds);
            }else if(area.getLevel() == 3){
                Long p1 = area.getParentId();
                // 查到区县信息
                Area district = baseMapper.selectById(p1);
                // 得到对应一级区域ID
                Long cityId = district.getParentId();
                Long [] parentIds = {cityId,p1};
                hashMap.put("data",area);
                hashMap.put("parentIds",parentIds);
            }
        }

        return hashMap;
    }

    @Override
    public List findByLevel(Integer level) {
        if(level != null && level == 2){
            EntityWrapper<Area> ew1 = new EntityWrapper<>();
            ew1.eq("level",1);
            List<Area> list = baseMapper.selectList(ew1);

            return list;
        }else if(level != null && level == 3){
            EntityWrapper<Area> ew2 = new EntityWrapper<>();
            ew2.eq("level",1);
            List<Area> list = baseMapper.selectList(ew2);

            List<Object> data = new ArrayList<>();
            for(Area area : list){
                HashMap map = new HashMap();
                map.put("id",area.getId());
                map.put("name",area.getName());
                EntityWrapper<Area> ew = new EntityWrapper<>();
                Long parentId = area.getId();
                ew.eq("parent_id",parentId);
                List<Area> list1 = baseMapper.selectList(ew);
                if(list1 != null && list1.size() > 0)
                     map.put("child",list1);

                data.add(map);
            }

            return data;
        }
        return null;
    }

    @Override
    public List findAll() {
        EntityWrapper<Area> ew1 = new EntityWrapper<>();
        ew1.eq("level",1);
        List<Area> allArea1 = baseMapper.selectList(ew1);

        EntityWrapper<Area> ew2 = new EntityWrapper<>();
        ew1.eq("level",2);
        List<Area> allArea2 = baseMapper.selectList(ew2);

        EntityWrapper<Area> ew3 = new EntityWrapper<>();
        ew1.eq("level",3);
        List<Area> allArea3 = baseMapper.selectList(ew3);

        List data = new ArrayList();
        for(Area area1 : allArea1) {
            Map<String,Object> city = objectToMap(area1);
            List districts = new ArrayList();
             for (Area area2 : allArea2) {
                Map<String, Object> discrict = objectToMap(area2);
                List street = new ArrayList();
                 for (Area area3 : allArea3) {
                    if (area3.getParentId() == area2.getId())
                          street.add(area3);
                 }
                discrict.put("child",street);
                if(area2.getParentId() == area1.getId())
                     districts.add(discrict);
             }
            city.put("child",districts);
            data.add(city);
        }
        return data;
    }

    @Override
    public String updateValidate(Area area) {
        EntityWrapper<Area> ew = new EntityWrapper<>();
        ew.eq("name",area.getName());
        List<Area> exist = baseMapper.selectList(ew);
        if(exist.size() > 0 && exist.get(0).getId() != area.getId())
            return "区域信息不可重复";

        return null;
    }
}
