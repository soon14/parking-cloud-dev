package com.yxytech.parkingcloud.core.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yxytech.parkingcloud.core.entity.VideoPile;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author cj
 * @since 2017-10-24
 */
public interface VideoPileMapper extends SuperMapper<VideoPile> {

    public Integer create(VideoPile videoPile);

    public Integer update(VideoPile videoPile);

    List<VideoPile> selectByPage(Pagination page, @Param("ew") Wrapper<VideoPile> wrapper);

    Integer updateBatch(List<VideoPile> list);
}