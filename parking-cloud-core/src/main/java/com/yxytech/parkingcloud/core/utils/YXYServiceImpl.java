package com.yxytech.parkingcloud.core.utils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.commons.entity.UserContext;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.mapper.UserAccountMapper;
import com.yxytech.parkingcloud.core.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YXYServiceImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M , T> implements YXYIService<T> {

    @Override
    public Map<Object, Object> selectIdNameMap(Wrapper<T> wrapper, String idColumn, String nameColumn) {
        wrapper.setSqlSelect(idColumn, nameColumn);
        List<Map<String, Object>> items;

        try {
            items = baseMapper.selectIdNameMap(wrapper);
        } catch (Exception e) {
            items = baseMapper.selectMaps(wrapper);
        }

        Map<Object, Object> map = new HashMap<>();
        items.forEach(item -> map.put(item.get(idColumn), item.get(nameColumn)));

        return map;
    }

    @Override
    public Map<Long, String> selectIdNameMap(Collection<Long> ids, String idColumn, String nameColumn) {
        Map<Long, String> map = new HashMap<>();

        if (ids.size() > 0) {
            EntityWrapper<T> ew = new EntityWrapper<>();
            ew.setSqlSelect(idColumn, nameColumn).in(idColumn, ids);
            List<Map<String, Object>> items;

            try {
                items = baseMapper.selectIdNameMap(ew);
            } catch (Exception e) {
                items = baseMapper.selectMaps(ew);
            }

            items.forEach(item -> map.put((Long)item.get(idColumn), (String)item.get(nameColumn)));
        }
        return map;
    }
}
