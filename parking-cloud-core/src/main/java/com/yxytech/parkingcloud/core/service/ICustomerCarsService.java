package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.CustomerCars;
import com.yxytech.parkingcloud.core.utils.YXYIService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-10-26
 */
public interface ICustomerCarsService extends IService<CustomerCars>,YXYIService<CustomerCars> {
    CustomerCars insertIfNotExists(CustomerCars customerCars);

    List<CustomerCars> getAllCarsByUser(Long userId);
}
