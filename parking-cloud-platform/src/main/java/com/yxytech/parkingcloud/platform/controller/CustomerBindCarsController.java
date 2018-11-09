package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.CustomerBindCars;
import com.yxytech.parkingcloud.core.entity.CustomerCars;
import com.yxytech.parkingcloud.core.enums.CarBindStatus;
import com.yxytech.parkingcloud.core.service.ICustomerBindCarsService;
import com.yxytech.parkingcloud.core.service.ICustomerCarsService;
import com.yxytech.parkingcloud.core.service.ICustomerService;
import com.yxytech.parkingcloud.platform.config.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customerBindCar")
public class CustomerBindCarsController extends BaseController{

    @Autowired
    protected ICustomerBindCarsService customerBindCarsService;


    @Autowired
    protected ICustomerCarsService customerCarsService;

    @Autowired
    protected ICustomerService customerService;

    @GetMapping("")
    @Access(permissionName = "平台客户查询",permissionCode = "CUSTOMER_QUERY",moduleCode = "customer_manage")
    public ApiResponse<Object> index(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                     @RequestParam(value = "size", defaultValue = pageSize, required = false) Integer size,
                                     @RequestParam(value = "userId",  required = false) Long userId,
                                     @RequestParam(value = "status",  required = false) Integer status)
    {
        Page<CustomerBindCars> bindPage = new Page<>(page, size);

        EntityWrapper<CustomerBindCars> ew = new EntityWrapper<>();

        ew.eq(userId != null, "user_id", userId);
        ew.eq(status != null, "status", status);
        ew.orderBy("id",true);

        bindPage = customerBindCarsService.selectPage(bindPage, ew);

        List<Long> userIds = new ArrayList<>();
        List<Long> carIds = new ArrayList<>();

        bindPage.getRecords().forEach(item -> {
            userIds.add(item.getUserId());
            carIds.add(item.getCarId());
        });

        Map<Long, String> users = customerService.selectIdNameMap(userIds, "id", "name");

        List<CustomerCars> cars = new ArrayList<>();
        if(!carIds.isEmpty()) {
            cars = customerCarsService.selectBatchIds(carIds);
        }


        Map<String, Object> datas = new HashMap<>();

        datas.put("customerBindCars", bindPage);
        datas.put("users", users);
        datas.put("CustomerCars", cars);

        return this.apiSuccess(datas);
    }

    @PutMapping("/examine")
    @Access(permissionName = "平台客户绑定户车辆审核",permissionCode = "CUSTOMER_CARS_CHECK",moduleCode = "customer_manage")
    public ApiResponse<Object> valid(@RequestBody List<Long> ids) {

        List<CustomerBindCars> customerBindCars = customerBindCarsService.selectBatchIds(ids);

        if(ids.size() > customerBindCars.size()){
            customerBindCars.forEach((item) -> ids.remove(item.getId()));
            return apiFail("绑定信息不存在" + ids.toString());
        }

        if (customerBindCars.size() == 0 || ids.size() <= 0) {
            return this.apiSuccess("");
        }

        for (CustomerBindCars item : customerBindCars) {
            if (! item.getValid()) {
                return this.apiFail("无效的绑定信息!");
            }

            if (! item.getStatus().equals(CarBindStatus.CERT_ING)) {
                return this.apiFail("存在未提交认证的车辆！");
            }
        }

        Wrapper<CustomerBindCars> customerBindCarsWrapper = new EntityWrapper<>();
        customerBindCarsWrapper.in("id", ids);

        CustomerBindCars customerBindCarsParam = new CustomerBindCars();
        customerBindCarsParam.setCertification(true);
        customerBindCarsParam.setStatus(CarBindStatus.CERT_SUCCESS);

        customerBindCarsService.update(customerBindCarsParam, customerBindCarsWrapper);

        return this.apiSuccess(null);
    }

    @PutMapping("/refuse/{id}")
    public ApiResponse<Object> refuse(@PathVariable Long id, @RequestBody Map<String, String>  refuse) {

        CustomerBindCars customerBindCar = customerBindCarsService.selectById(id);

        if(customerBindCar == null) {
            return this.apiFail("非法的认证!");
        }

        if (! customerBindCar.getStatus().equals(CarBindStatus.CERT_ING)) {
            return this.apiFail("非法的认证!");
        }

        customerBindCar.setRemark(refuse.get("reason"));
        customerBindCar.setStatus(CarBindStatus.CERT_FAILED);
        customerBindCarsService.updateById(customerBindCar);

        return this.apiSuccess(null);
    }

    @GetMapping("/carBindStatus")
    public ApiResponse<Object> carBindStatus() {
        List data = new ArrayList();
        for(CarBindStatus CarBindStatu : CarBindStatus.values()){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id",CarBindStatu.getValue());
            map.put("name",CarBindStatu.getDesc());
            data.add(map);
        }

        return apiSuccess(data);
    }
}
