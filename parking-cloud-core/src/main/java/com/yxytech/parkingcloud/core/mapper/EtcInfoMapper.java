package com.yxytech.parkingcloud.core.mapper;

import com.yxytech.parkingcloud.core.entity.EtcInfo;
import com.yxytech.parkingcloud.core.utils.SuperMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author liwd
 * @since 2017-10-18
 */
public interface EtcInfoMapper extends SuperMapper<EtcInfo> {
    Integer repairTableByVersion(Integer version);
}