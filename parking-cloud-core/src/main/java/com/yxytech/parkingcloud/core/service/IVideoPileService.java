package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.VideoPile;
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
public interface IVideoPileService extends IService<VideoPile> {

    public void create(VideoPile videoPile);

    public void updateVideoPile(VideoPile videoPile);

    public String validate(String sn,Long facOrgId,String ip);

    public Page<VideoPile> selectByPage(Page<VideoPile> page, @Param("ew") Wrapper<VideoPile> wrapper);

    void updateBatch(List<VideoPile> list);

    VideoPile detail(Long id);

    String updateValidate(VideoPile videoPile);
}
