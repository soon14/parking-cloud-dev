package com.yxytech.parkingcloud.core.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yxytech.parkingcloud.core.entity.OrderInfo;
import com.yxytech.parkingcloud.core.utils.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author liwd
 * @since 2017-10-27
 */
public interface OrderInfoMapper extends SuperMapper<OrderInfo> {
    OrderInfo getDetail(Long id);

    Integer getInParkingByCell(@Param("parkingId") Long parkingId, @Param("cellId") Long cellId);

    List<OrderInfo> invoiceSelectPage(Pagination page, @Param("ew") Wrapper<OrderInfo> wrapper);

    List<OrderInfo> invoiceSelectList(@Param("ew") Wrapper<OrderInfo> wrapper);

    List<OrderInfo> organzationSelectList(@Param("ew") Wrapper<OrderInfo> wrapper);

    List<Map<String,Object>> selectOrganizationInvoiceInfo(Long id);

    List<Map<String, Integer>> selectInUseParkingCells();
}