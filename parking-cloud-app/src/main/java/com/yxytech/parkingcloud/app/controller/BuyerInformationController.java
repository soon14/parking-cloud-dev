package com.yxytech.parkingcloud.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.yxytech.parkingcloud.app.controller.form.BuyerInformationForm;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.BuyerInformation;
import com.yxytech.parkingcloud.core.enums.InUseEnum;
import com.yxytech.parkingcloud.core.service.IBuyerInformationService;
import com.yxytech.parkingcloud.core.service.ICustomerService;
import com.yxytech.parkingcloud.core.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/buyerInformation")
public class BuyerInformationController extends BaseController{

    @Autowired
    private IBuyerInformationService buyerInformationService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICustomerService iCustomerService;

    @PostMapping("/addBuyer")
    public ApiResponse<Object> addBuyer(@Valid @RequestBody BuyerInformationForm buyerInformationForm, BindingResult buyerInformationErrors)
            throws BindException {
        validate(buyerInformationErrors);
        BuyerInformation buyerInformation = new BuyerInformation();
        BeanUtils.copyProperties(buyerInformationForm, buyerInformation);
        buyerInformationService.insert(buyerInformation);
        return apiSuccess(buyerInformation.getId());
    }

    @GetMapping("/findBuyer")
    public ApiResponse<Object> findBuyer()
    {
        Long customer_id = getCurrentUser().getId();
        EntityWrapper<BuyerInformation> ew = new EntityWrapper<>();
        ew.eq("customer_id", customer_id);
        Object buyerInformation = buyerInformationService.selectMap(ew);
        return apiSuccess(buyerInformation);
    }



    /**
     * 启用
     * @param id
     * @return
     */

//    @PutMapping("/enable/{id}")
//    public ApiResponse<Object> enable(@PathVariable Integer id)
//    {
//        BuyerInformation buyerInformation = new BuyerInformation();
//        buyerInformation.setInUse(1);
//        buyerInformation.setUsedTime(new Date());
//        Wrapper<BuyerInformation> buyerInformationWrapper = new EntityWrapper<>();
//        buyerInformationWrapper.eq("id", id);
//        Boolean result = buyerInformationService.update(buyerInformation, buyerInformationWrapper);
//        if(!result){
//            return apiFail(500,"找不到购方信息");
//        }
//        return apiSuccess("");
//    }

    /**
     * 禁用
     * @param id
     * @return
    */
//    @PutMapping("/unable/{id}")
//    public ApiResponse<Object> unable(@PathVariable Integer id)
//    {
//        BuyerInformation buyerInformation = new BuyerInformation();
//        buyerInformation.setInUse(2);
//        buyerInformation.setUnUsedTime(new Date());
//        Wrapper<BuyerInformation> buyerInformationWrapper = new EntityWrapper<>();
//        buyerInformationWrapper.eq("id", id);
//        Boolean result = buyerInformationService.update(buyerInformation, buyerInformationWrapper);
//        if(!result){
//            return apiFail(500,"找不到购方信息");
//        }
//        return apiSuccess("");
//    }


    @PutMapping("/unable/{id}")
    public ApiResponse<Object> unable(@PathVariable Integer id)
    {
        BuyerInformation buyerInformation = new BuyerInformation();
        buyerInformation.setInUse(InUseEnum.NOT_USE);
        buyerInformation.setUnUsedTime(new Date());
        Wrapper<BuyerInformation> buyerInformationWrapper = new EntityWrapper<>();
        buyerInformationWrapper.eq("id", id);
        Boolean result = buyerInformationService.update(buyerInformation, buyerInformationWrapper);
        if(!result){
            return apiFail(500,"找不到购方信息");
        }
        return apiSuccess("");
    }

}
