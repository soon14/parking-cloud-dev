package com.yxytech.parkingcloud.core.service.impl;

import com.yxytech.parkingcloud.core.entity.CustomerCars;
import com.yxytech.parkingcloud.core.mapper.CustomerCarsMapper;
import com.yxytech.parkingcloud.core.service.ICustomerCarsService;
import com.yxytech.parkingcloud.core.utils.YXYServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwd
 * @since 2017-10-26
 */
@Service
public class CustomerCarsServiceImpl extends YXYServiceImpl<CustomerCarsMapper, CustomerCars> implements ICustomerCarsService {

    @Override
    public CustomerCars insertIfNotExists(CustomerCars customerCars) {
        CustomerCars customerCarsParam = customerCars;
        customerCarsParam.setCreatedBy(null);
        customerCarsParam.setGreenEnergy(null);

        CustomerCars customerCarsResult = baseMapper.selectOne(customerCarsParam);

        if (customerCarsResult == null) {
            Integer ret = baseMapper.insert(customerCars);

            if (ret <= 0) {
                return null;
            }

            return customerCars;
        }

        return customerCarsResult;
    }

    @Override
    public List<CustomerCars> getAllCarsByUser(Long userId) {
        return baseMapper.getAllCarsByUserId(userId);
    }
}
