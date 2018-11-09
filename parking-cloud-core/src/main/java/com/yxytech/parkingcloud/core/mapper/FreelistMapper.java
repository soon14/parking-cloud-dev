package com.yxytech.parkingcloud.core.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yxytech.parkingcloud.core.entity.Freelist;
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
public interface FreelistMapper extends SuperMapper<Freelist> {
    List<Freelist> customerSelect(Pagination page, @Param("ew") Wrapper<Freelist> wrapper);

    Integer updateForUsedTimes(@Param("id") Long id, @Param("times") Integer times);
}