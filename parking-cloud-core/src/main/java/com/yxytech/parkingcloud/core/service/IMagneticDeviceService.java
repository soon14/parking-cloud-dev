package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.MagneticDevice;
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
public interface IMagneticDeviceService extends IService<MagneticDevice> {

    public void create(MagneticDevice magneticDevice);

    public void updateMagneticDevice(MagneticDevice magneticDevice);

    public String validateGps(String gps);

    public String formattingGps(String gps);

    public String validate(String sn,Long facOrgId);

    public Page<MagneticDevice> selectByPage(Page<MagneticDevice> page, @Param("ew") Wrapper<MagneticDevice> wrapper);

    String bindOrg(Long orgId);

    void updateBatch(List<MagneticDevice> list);

    MagneticDevice detail(Long id);

    String updateValidate(MagneticDevice magneticDevice);
}
