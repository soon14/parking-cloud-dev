package com.yxytech.parkingcloud.core.mapper;

import com.yxytech.parkingcloud.core.entity.EtcVersion;
import com.yxytech.parkingcloud.core.utils.SuperMapper;

import java.util.List;


/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author liwd
 * @since 2017-10-18
 */
public interface EtcVersionMapper extends SuperMapper<EtcVersion> {
    Integer getMaxVersion();

    String getValidTableName();

    List<EtcVersion> getAllByVersion(String etcNumber);
}