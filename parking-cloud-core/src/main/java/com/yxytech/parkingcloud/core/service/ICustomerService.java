package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.Customer;
import com.yxytech.parkingcloud.core.utils.YXYIService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cj
 * @since 2017-10-27
 */
public interface ICustomerService extends IService<Customer>,YXYIService<Customer> {

    public Customer findCustomerInfo(Long id);
}
