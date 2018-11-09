package com.yxytech.parkingcloud.core.service.impl;

import com.yxytech.parkingcloud.core.entity.Customer;
import com.yxytech.parkingcloud.core.mapper.CustomerMapper;
import com.yxytech.parkingcloud.core.service.ICustomerService;
import com.yxytech.parkingcloud.core.utils.YXYServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cj
 * @since 2017-10-27
 */
@Service
public class CustomerServiceImpl extends YXYServiceImpl<CustomerMapper, Customer> implements ICustomerService {
    @Override
    public Customer findCustomerInfo(Long id) {

        return baseMapper.findCustomerInfo(id);
    }
}
