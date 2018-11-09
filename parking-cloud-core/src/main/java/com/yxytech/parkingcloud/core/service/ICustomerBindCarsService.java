package com.yxytech.parkingcloud.core.service;

import com.baomidou.mybatisplus.service.IService;
import com.yxytech.parkingcloud.core.entity.Customer;
import com.yxytech.parkingcloud.core.entity.CustomerBindCars;
import com.yxytech.parkingcloud.core.entity.CustomerCars;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.enums.ColorsEnum;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwd
 * @since 2017-10-26
 */
public interface ICustomerBindCarsService extends IService<CustomerBindCars> {
    void bind(CustomerCars customerCars, Customer user);

    List<CustomerBindCars> getAllBindCars(Long userId);

    CustomerBindCars getByPlateInfo(String plateNumber, ColorsEnum plateColor);
}
