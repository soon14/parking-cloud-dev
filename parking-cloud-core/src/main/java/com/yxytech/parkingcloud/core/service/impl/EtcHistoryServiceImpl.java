package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.yxytech.parkingcloud.core.entity.EtcHistory;
import com.yxytech.parkingcloud.core.mapper.EtcHistoryMapper;
import com.yxytech.parkingcloud.core.service.IEtcHistoryService;
import org.apache.ibatis.session.SqlSession;
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
public class EtcHistoryServiceImpl extends ServiceImpl<EtcHistoryMapper, EtcHistory> implements IEtcHistoryService {

    @Override
    public boolean upsert(EtcHistory etcHistory) {
        EtcHistory history = new EtcHistory();
        Integer rows;

        history.setEtcNumber(etcHistory.getEtcNumber());
        history.setEtcNetId(etcHistory.getEtcNumber());

        if (baseMapper.getOne(etcHistory) == null) {
            // 插入
            rows = baseMapper.insert(etcHistory);
        } else {
            rows = baseMapper.update(etcHistory);
        }

        return rows > 0;
    }

    @Override
    public Page<EtcHistory> getAllByVersion(Page<EtcHistory> page, Integer version) {
        page.setRecords(baseMapper.getAllByVersion(page, version));

        return page;
    }

    @Override
    public String getAllVersion(String etcNumber) {
        return baseMapper.getAllVersion(etcNumber);
    }

    @Override
    public boolean insertOrUpdateBatch(List<EtcHistory> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }

        SqlSession batchSqlSession = sqlSessionBatch();
        try {
            if (entityList.size() > 0) {
                baseMapper.insertBatch(entityList);
            }
//            List<EtcHistory> existList;
//            existList = etcHistoryMapper.getAllExistId(entityList);
//
//            if (existList.size() == 0) {
//                etcHistoryMapper.insertBatch(entityList);
//            } else {
//                // 先更新
//                etcHistoryMapper.updateBatch(Long.valueOf(entityList.get(0).getVersion()), existList);
//
//                etcHistoryMapper.insertBatch(entityList);
//            }

            batchSqlSession.commit();
        } catch (Throwable e) {
            batchSqlSession.rollback();
            throw new MybatisPlusException("Error: Cannot execute insertOrUpdateBatch Method. Cause", e);
        } finally {
            batchSqlSession.close();
        }

        return true;
    }
}
