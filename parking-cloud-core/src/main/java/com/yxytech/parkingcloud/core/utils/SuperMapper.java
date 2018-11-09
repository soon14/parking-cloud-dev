package com.yxytech.parkingcloud.core.utils;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SuperMapper<T> extends BaseMapper<T> {
    List<Map<String, Object>> selectIdNameMap(@Param("ew") Wrapper<T> wrapper);
}
