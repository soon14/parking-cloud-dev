package com.yxytech.parkingcloud.platform.controller;

import com.alibaba.fastjson.JSON;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.CustomerBindCars;
import com.yxytech.parkingcloud.core.entity.CustomerCars;
import com.yxytech.parkingcloud.core.enums.CarBindStatus;
import com.yxytech.parkingcloud.core.service.ICustomerBindCarsService;
import com.yxytech.parkingcloud.core.service.ICustomerCarsService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cars")
public class CarsController extends BaseController {

    @Autowired
    private ICustomerCarsService customerCarsService;

    @Autowired
    private ICustomerBindCarsService customerBindCarsService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody CustomerCars customerCars) {

        customerCarsService.insert(customerCars);

        return this.apiSuccess("ok");
    }

    /**
     * 车辆认证
     * @param id
     * @return
     */
    @PutMapping("/certificationCar")
    public ApiResponse<Object> certificationCar(@RequestParam(value = "id",required = false)Long id)throws NotFoundException{
        CustomerBindCars customerBindCars = customerBindCarsService.selectById(id);
        notFound(customerBindCars,"用户车辆绑定关系不存在");

        if(customerBindCars.getStatus().equals(CarBindStatus.CERT_ING)){
            customerBindCars.setStatus(CarBindStatus.CERT_SUCCESS);
            customerBindCars.setCertification(true);
            customerBindCarsService.updateById(customerBindCars);

            return apiSuccess(null);
        }

        return apiFail("非法操作");
    }

    /**
     * 车辆批量认证
     * @param ids
     * @return
     */
    @PutMapping("/certificationCarBath")
    public ApiResponse<Object> certificationCarBath(@RequestBody String ids){
        if(StringUtils.isBlank(ids))
             return apiFail("非法参数");

       Map<String, Object> map = JSON.parseObject(ids);
       List<Long> newIds = (List<Long>) map.get("ids");

       List<CustomerBindCars> list = new ArrayList<>();
           for (Long id : newIds) {
               CustomerBindCars customerBindCars = customerBindCarsService.selectById(id);
               if(customerBindCars.getStatus().equals(CarBindStatus.NOT_CERT) || customerBindCars.getStatus().equals(CarBindStatus.CERT_SUCCESS)){
                   Long carId = customerBindCars.getCarId();
                   CustomerCars customerCars = customerCarsService.selectById(carId);

                   return apiFail("车牌号为:"+customerCars.getPlateNumber()+"的车辆状态不为认证中");
               }
               if(customerBindCars.getStatus().equals(CarBindStatus.CERT_ING)){
                   customerBindCars.setStatus(CarBindStatus.CERT_SUCCESS);
                   customerBindCars.setCertification(true);
                   list.add(customerBindCars);
               }
           }
           customerBindCarsService.updateBatchById(list);

           return apiSuccess(null);
   }
}
