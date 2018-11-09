package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.SalesInformation;
import com.yxytech.parkingcloud.core.enums.InUseEnum;
import com.yxytech.parkingcloud.core.service.ISalesInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/salesInformation")
public class SaleInformationController extends BaseController{

    @Autowired
    private ISalesInformationService iSalesInformationService;

    @GetMapping("/lists")
    public ApiResponse<Object> index(
            @RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
            @RequestParam(value = "size",required = false) Integer size
    )
    {
        Page<SalesInformation> p  = new Page<SalesInformation>(page, size);
        EntityWrapper<SalesInformation> salesInformationEntityWrapper = new EntityWrapper<>();
        salesInformationEntityWrapper.orderBy("id",true);

        p = iSalesInformationService.selectPage(p, salesInformationEntityWrapper);
        return apiSuccess(p);
    }

    @PutMapping("/enableOrUnable/{id}")
    public ApiResponse<Object> enableOrUnable(@PathVariable Integer id,
                                              @RequestBody Map<String, Integer> in_use)
    {
        SalesInformation salesInformation = new SalesInformation();
        if(InUseEnum.IN_USE.getValue().equals(in_use.get("in_use"))) {
            salesInformation.setInUse(InUseEnum.IN_USE);
        }else{
            salesInformation.setInUse(InUseEnum.NOT_USE);
        }

        if(in_use.get("in_use") == 0){//停用
            salesInformation.setUsedTime(new Date());
        }else{
            salesInformation.setUnUsedTime(new Date());
        }

        Wrapper<SalesInformation> salesInformationWrapper = new EntityWrapper<>();
        salesInformationWrapper.eq("id", id);
        Boolean result = iSalesInformationService.update(salesInformation, salesInformationWrapper);
        if(!result){
            return apiFail(500,"找不到购方信息");
        }
        return apiSuccess("");
    }

}
