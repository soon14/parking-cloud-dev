package com.yxytech.parkingcloud.app.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxytech.parkingcloud.app.entity.CustomBindCarsEntity;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Customer;
import com.yxytech.parkingcloud.core.entity.CustomerBindCars;
import com.yxytech.parkingcloud.core.entity.CustomerCars;
import com.yxytech.parkingcloud.core.enums.CarBindStatus;
import com.yxytech.parkingcloud.core.service.ICustomerBindCarsService;
import com.yxytech.parkingcloud.core.service.ICustomerCarsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liwd
 * @since 2017-10-24
 */
@RestController
@RequestMapping("/customCars")
public class CustomerCarsController extends BaseController {

	@Autowired
	private ICustomerCarsService customerCarsService;

	@Autowired
    private ICustomerBindCarsService customerBindCarsService;

	@PostMapping("/bindCar")
	public ApiResponse bindCar(@RequestBody CustomerCars customerCars) {
        if (customerCars.getPlateNumber() == null || customerCars.getPlateColor() == null
                || customerCars.getCarType() == null) {
            return this.apiFail(500, "参数不能为空!");
        }

        customerCars.setGreenEnergy(customerCars.getCarType().getValue().equals(1));
        customerCars.setCarType(null);

        Customer user = getCurrentUser();

        customerCars = customerCarsService.insertIfNotExists(customerCars);

        if (customerCars == null) {
            return this.apiFail(500, "绑定车辆信息失败!");
        }

        try {
            customerBindCarsService.bind(customerCars, user);
        } catch (RuntimeException e) {
            return this.apiFail(e.getMessage());
        }

		return this.apiSuccess("ok");
    }

    @PostMapping("/unbindCar/{id}")
    public ApiResponse unbindCar(@PathVariable Long id) {
        Date date = new Date();
        Wrapper<CustomerBindCars> customerBindCarsWrapper = new EntityWrapper<>();

        customerBindCarsWrapper.eq("car_id", id);
        customerBindCarsWrapper.eq("is_valid", true);

	    CustomerBindCars customerCarsResult = customerBindCarsService.selectOne(customerBindCarsWrapper);

	    if (customerCarsResult == null || (! customerCarsResult.getUserId().equals(getCurrentUser().getId()))) {
            return this.apiFail("非法访问!");
        }

        if (! customerCarsResult.getValid()) {
	        return this.apiFail("已经解绑!");
        }

        customerCarsResult.setUnbindAt(date);
        customerCarsResult.setStatus(CarBindStatus.UNBIND);
        customerCarsResult.setCertification(false);
        customerCarsResult.setValid(false);

	    Boolean ret = customerBindCarsService.updateById(customerCarsResult);

        if (! ret) {
            return this.apiFail(500, "解绑车辆失败!");
        }

        return this.apiSuccess("ok");
    }

    @PostMapping("/updateBindCar/{id}")
    public ApiResponse updateBindCar(@PathVariable Integer id,
                                     @RequestBody CustomerCars customerCars) {
	    Wrapper<CustomerBindCars> customerBindCarsWrapper = new EntityWrapper<>();
        CustomerCars customerCarsResult = customerCarsService.selectById(id);
        Date date = new Date();

        customerBindCarsWrapper.eq("car_id", id)
                .eq("user_id", getCurrentUser().getId())
                .where("is_valid is TRUE");

        CustomerBindCars customerBindCars = customerBindCarsService.selectOne(customerBindCarsWrapper);

        if (customerBindCars == null) {
            return this.apiFail(500, "非法操作!");
        }

        if (customerCars.getPlateNumber() != null) {
            customerCarsResult.setPlateNumber(customerCars.getPlateNumber());
        }

        if (customerCars.getPlateColor() != null) {
            customerCarsResult.setPlateColor(customerCars.getPlateColor());
        }

        if (customerCars.getCarType() != null) {
            customerCarsResult.setCarType(customerCars.getCarType());
        }

        Boolean ret = customerCarsService.updateById(customerCarsResult);

        if (! ret) {
            return this.apiFail(500, "更新车辆信息失败!");
        }

        return this.apiSuccess("ok");
    }

    @GetMapping("/getBindCars")
    public ApiResponse getBindCars() {
        List<CustomerBindCars> ret = customerBindCarsService.getAllBindCars(getCurrentUser().getId());
        List<CustomBindCarsEntity> result = new ArrayList<>();

        for (CustomerBindCars customerBindCars : ret) {
            CustomBindCarsEntity entity = new CustomBindCarsEntity();

            BeanUtils.copyProperties(customerBindCars, entity);

            CustomerCars carInfo = customerBindCars.getCarInfo();

            entity.setPlateNumber(carInfo.getPlateNumber());
            entity.setPlateColor(carInfo.getPlateColor());
            entity.setCarType(carInfo.getCarType());
            entity.setAuthRequestTime(customerBindCars.getAuthTime());

            if (customerBindCars.getCertification()) {
                entity.setAuthFinishedTime(customerBindCars.getUpdatedAt());
            }

            result.add(entity);
        }

        return this.apiSuccess(result);
    }

    @PostMapping("/validateCar/{id}")
    public ApiResponse validateCar(@PathVariable Long id, @RequestBody CustomerCars customerCars) {
	    Wrapper<CustomerBindCars> customerBindCarsWrapper = new EntityWrapper<>();
        Customer user = getCurrentUser();

	    customerBindCarsWrapper.eq("car_id", id)
            .eq("user_id", user.getId())
            .where("is_valid is TRUE");

        CustomerBindCars customerBindCars = customerBindCarsService.selectOne(customerBindCarsWrapper);

        if (customerBindCars == null || customerBindCars.getStatus().equals(CarBindStatus.CERT_SUCCESS)) {
            return this.apiFail("非法操作!");
        }

        if (customerBindCars.getStatus().equals(CarBindStatus.CERT_ING)) {
            return this.apiFail("认证审核中，请不要提交认证信息!");
        }

        if (customerBindCars.getCertification()) {
            return this.apiFail("请不要重复认证!");
        }

        Date date = new Date();

        customerBindCars.setAuthTime(date);
        customerBindCars.setAuthImageUrl(customerCars.getAuthImage());
        customerBindCars.setStatus(CarBindStatus.CERT_ING);

        Boolean ret = customerBindCarsService.updateById(customerBindCars);

        if (! ret) {
            return this.apiFail(500, "认证失败!");
        }

        return this.apiSuccess("资料提交成功，请等待审核!");
    }
}
