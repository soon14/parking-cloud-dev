package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.VideoMonitor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-24
 */
public interface IVideoMonitorService extends IService<VideoMonitor> {

    public void create(VideoMonitor videoMonitor);

    public void updateVideoMonitor(VideoMonitor videoMonitor);

    public String validate(String sn,Long facOrgId);

    public Page<VideoMonitor> selectByPage(Page<VideoMonitor> page, @Param("ew") Wrapper<VideoMonitor> wrapper);

    void updateBatch(List<VideoMonitor> list);

    VideoMonitor detail(Long id);

    String updateValidate(VideoMonitor videoMonitor);
}
