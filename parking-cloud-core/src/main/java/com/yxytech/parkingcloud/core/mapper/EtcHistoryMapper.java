package com.yxytech.parkingcloud.core.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yxytech.parkingcloud.core.entity.EtcHistory;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author liwd
 * @since 2017-10-18
 */
public interface EtcHistoryMapper extends SuperMapper<EtcHistory> {
    Integer insert(EtcHistory etcHistory);

    List<EtcHistory> getAllByVersion(Pagination page, Integer version);

    EtcHistory getOne(EtcHistory etcHistory);

    Integer update(EtcHistory etcHistory);

    List<EtcHistory> getAllExistId(List<EtcHistory> etcHistoryList);

    Integer insertBatch(List<EtcHistory> etcHistoryList);

    Integer updateBatch(@Param("version") Long version, @Param("etcHistoryList") List<EtcHistory> etcHistoryList);

    String getAllVersion(String etcNumber);
}