package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.EtcVersion;
import com.yxytech.parkingcloud.core.mapper.EtcVersionMapper;
import com.yxytech.parkingcloud.core.service.IEtcVersionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwd
 * @since 2017-10-18
 */
@Service
public class EtcVersionServiceImpl extends ServiceImpl<EtcVersionMapper, EtcVersion> implements IEtcVersionService {

    @Override
    public Integer getMaxVersion() {
        return baseMapper.getMaxVersion();
    }

    @Override
    public String getValidTableName() {
        return baseMapper.getValidTableName();
    }

    @Override
    public List<EtcVersion> getAllHistoryByVersion(String etcNumber) {
        return baseMapper.getAllByVersion(etcNumber);
    }
}
