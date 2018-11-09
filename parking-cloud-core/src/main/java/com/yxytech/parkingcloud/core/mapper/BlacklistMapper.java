package com.yxytech.parkingcloud.core.mapper;


import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yxytech.parkingcloud.core.entity.Blacklist;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author liwd
 * @since 2017-10-20
 */
public interface BlacklistMapper extends SuperMapper<Blacklist> {
    List<Blacklist> customerSelect(Pagination page, @Param("ew") Wrapper<Blacklist> wrapper);
}