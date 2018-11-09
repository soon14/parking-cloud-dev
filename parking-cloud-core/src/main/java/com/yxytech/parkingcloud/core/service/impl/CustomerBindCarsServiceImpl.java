package com.yxytech.parkingcloud.core.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yxytech.parkingcloud.core.entity.Customer;
import com.yxytech.parkingcloud.core.entity.CustomerBindCars;
import com.yxytech.parkingcloud.core.entity.CustomerCars;
import com.yxytech.parkingcloud.core.enums.CarBindStatus;
import com.yxytech.parkingcloud.core.enums.ColorsEnum;
import com.yxytech.parkingcloud.core.mapper.CustomerBindCarsMapper;
import com.yxytech.parkingcloud.core.service.ICustomerBindCarsService;
import com.yxytech.parkingcloud.core.service.ICustomerCarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class CustomerBindCarsServiceImpl extends ServiceImpl<CustomerBindCarsMapper, CustomerBindCars> implements ICustomerBindCarsService {
    @Autowired
    private ICustomerCarsService customerCarsService;

    @Override
    public void bind(CustomerCars customerCars, Customer user) throws RuntimeException {
        Date date = new Date();

        CustomerBindCars customerBindCarsCondition = new CustomerBindCars();

        customerBindCarsCondition.setUserId(user.getId());
        customerBindCarsCondition.setCarId(customerCars.getId());

        CustomerBindCars customerBindCars = baseMapper.selectOne(customerBindCarsCondition);

        if (customerBindCars != null) {
            if (customerBindCars.getValid()) {
                throw new RuntimeException("您已经绑定过该辆车，请不要重复绑定");
            } else {
                this.validateIsExistsBindRelation(customerBindCarsCondition);

                // 更新绑定
                customerBindCars.setId(customerBindCars.getId());
                customerBindCars.setValid(true);
                customerBindCars.setStatus(CarBindStatus.NOT_CERT);
                customerBindCars.setUpdatedAt(date);

                baseMapper.updateOtherBindRelation(customerCars.getId());
                Integer ret = baseMapper.updateById(customerBindCars);

                if (ret <= 0) {
                    throw new RuntimeException("绑定失败!");
                }
            }
        } else {
            this.validateIsExistsBindRelation(customerBindCarsCondition);
            CustomerBindCars customerBindCarsParam = new CustomerBindCars();

            customerBindCarsParam.setCarId(customerCars.getId());
            customerBindCarsParam.setUserId(user.getId());
            customerBindCarsParam.setValid(true);
            customerBindCarsParam.setBindAt(date);

            baseMapper.updateOtherBindRelation(customerCars.getId());
            Integer ret = baseMapper.insert(customerBindCarsParam);

            if (ret <= 0) {
                throw new RuntimeException("绑定车辆失败!");
            }
        }
    }

    private void validateIsExistsBindRelation(CustomerBindCars customerBindCars) {
        customerBindCars.setUserId(null);
        customerBindCars.setValid(true);

        customerBindCars = baseMapper.selectOne(customerBindCars);

        if (customerBindCars != null) {
            if (customerBindCars.getCertification()) {
                throw new RuntimeException("车辆已经绑定过，并且已经认证，不能再次进行绑定!");
            }

            if (customerBindCars.getStatus().equals(CarBindStatus.CERT_ING)) {
                throw new RuntimeException("该车辆正在认证中，不能进行绑定!");
            }
        }
    }

    @Override
    public List<CustomerBindCars> getAllBindCars(Long userId) {
        return baseMapper.getAllBindCars(userId);
    }

    @Override
    public CustomerBindCars getByPlateInfo(String plateNumber, ColorsEnum plateColor) {
        Wrapper<CustomerCars> customerCarsWrapper = new EntityWrapper<>();
        customerCarsWrapper.eq("plate_number", plateNumber)
                .eq("plate_color", plateColor);

        CustomerCars customerCars = customerCarsService.selectOne(customerCarsWrapper);

        if (customerCars == null) {
            return null;
        }

        CustomerBindCars customerBindCarsParam = new CustomerBindCars();
        customerBindCarsParam.setCarId(customerCars.getId());
        customerBindCarsParam.setValid(true);
        CustomerBindCars customerBindCars = baseMapper.selectOne(customerBindCarsParam);

        if (customerBindCars == null) {
            customerBindCars = new CustomerBindCars();

            customerBindCars.setCarId(customerCars.getId());
            customerBindCars.setUserId(0L);
        }

        return customerBindCars;
    }
}
