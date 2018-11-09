package com.yxytech.parkingcloud.core.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yxytech.parkingcloud.core.entity.InvoiceInformationRequest;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.entity.SalesInformation;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zhangyiqing
 * @since 2017-10-31
 */
public interface SalesInformationMapper extends SuperMapper<SalesInformation> {
    List<InvoiceInformationRequest> selectPage(Pagination page, @Param("ew") Wrapper<Organization> wrapper);
    List<InvoiceInformationRequest> selectList(Pagination page, @Param("ew") Wrapper<Organization> wrapper);
}