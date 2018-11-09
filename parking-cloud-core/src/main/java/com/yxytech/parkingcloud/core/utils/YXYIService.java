package com.yxytech.parkingcloud.core.utils;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface YXYIService<T>  {

    Map<Object, Object> selectIdNameMap(Wrapper<T> wrapper, String idColumn, String nameColumn);

    Map<Long, String> selectIdNameMap(Collection<Long> ids, String idColumn, String nameColumn);
}
