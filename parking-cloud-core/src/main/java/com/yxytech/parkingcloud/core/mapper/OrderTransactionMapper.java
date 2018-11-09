package com.yxytech.parkingcloud.core.mapper;

import com.yxytech.parkingcloud.core.entity.OrderTransaction;
import com.yxytech.parkingcloud.core.utils.SuperMapper;

import java.util.Date;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author liwd
 * @since 2017-11-03
 */
public interface OrderTransactionMapper extends SuperMapper<OrderTransaction> {
    Date getLastTransactionDate(Long orderId);
}