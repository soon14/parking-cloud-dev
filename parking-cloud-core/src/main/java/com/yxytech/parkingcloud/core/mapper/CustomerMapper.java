package com.yxytech.parkingcloud.core.mapper;

import com.yxytech.parkingcloud.core.entity.Customer;
import com.yxytech.parkingcloud.core.utils.SuperMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author cj
 * @since 2017-10-27
 */
public interface CustomerMapper extends SuperMapper<Customer> {

    public Customer findCustomerInfo(Long id);
}