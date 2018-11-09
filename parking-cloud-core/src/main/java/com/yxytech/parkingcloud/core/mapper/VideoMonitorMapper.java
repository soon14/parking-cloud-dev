package com.yxytech.parkingcloud.core.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yxytech.parkingcloud.core.entity.VideoMonitor;
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
public interface VideoMonitorMapper extends SuperMapper<VideoMonitor> {

    public Integer create(VideoMonitor videoMonitor);

    public Integer update(VideoMonitor videoMonitor);

    List<VideoMonitor> selectByPage(Pagination page, @Param("ew") Wrapper<VideoMonitor> wrapper);

    Integer updateBatch(List<VideoMonitor> list);
}