package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.EtcVersion;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-10-18
 */
public interface IEtcVersionService extends IService<EtcVersion> {
    Integer getMaxVersion();

    String getValidTableName();

    List<EtcVersion> getAllHistoryByVersion(String etcNumber);
}
