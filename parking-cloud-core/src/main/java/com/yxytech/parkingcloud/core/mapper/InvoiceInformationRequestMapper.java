package com.yxytech.parkingcloud.core.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yxytech.parkingcloud.core.entity.InvoiceInformationRequest;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zhangyiqing
 * @since 2017-11-02
 */
public interface InvoiceInformationRequestMapper extends SuperMapper<InvoiceInformationRequest> {

    List<InvoiceInformationRequest> selectPage(Pagination page, @Param("ew") Wrapper<InvoiceInformationRequest> wrapper);
    List<InvoiceInformationRequest> selectList(Pagination page, @Param("ew") Wrapper<InvoiceInformationRequest> wrapper);
    List<InvoiceInformationRequest> selectRequestPage(Pagination page, @Param("ew") Wrapper<InvoiceInformationRequest> wrapper);
    List<InvoiceInformationRequest> selectRequestList(@Param("ew") Wrapper<InvoiceInformationRequest> wrapper);
    List<InvoiceInformationRequest> selectLastOne(@Param("ew") Wrapper<InvoiceInformationRequest> wrapper);
    String getLastUsedInformation(@Param("userId") Long userId);
}