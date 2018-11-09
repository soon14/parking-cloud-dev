package com.yxytech.parkingcloud.core.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yxytech.parkingcloud.core.entity.MagneticDevice;
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
public interface MagneticDeviceMapper extends SuperMapper<MagneticDevice> {

    public Integer create(MagneticDevice magneticDevice);

    public Integer update(MagneticDevice magneticDevice);

    List<MagneticDevice> selectByPage(Pagination page, @Param("ew") Wrapper<MagneticDevice> wrapper);

    Integer updateBatch(List<MagneticDevice> list);
}