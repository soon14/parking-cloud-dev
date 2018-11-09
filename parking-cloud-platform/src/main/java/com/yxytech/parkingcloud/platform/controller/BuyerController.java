package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.BuyerInformation;
import com.yxytech.parkingcloud.core.enums.InUseEnum;
import com.yxytech.parkingcloud.core.service.IBuyerInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/buyerInformation")
public class BuyerController extends BaseController{

    @Autowired
    private IBuyerInformationService iBuyerInformationService;

    @GetMapping("/lists")
    public ApiResponse<Object> index(@RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                                     @RequestParam(value = "size",required = false) Integer size
                                     )
    {
        // 创建分页对象
        Page<BuyerInformation> p  = new Page<BuyerInformation>(page, size);
        EntityWrapper<BuyerInformation> buyerInformationEntityWrapper = new EntityWrapper<BuyerInformation>();

        p = iBuyerInformationService.selectPage(p, buyerInformationEntityWrapper);
        return apiSuccess(p);
    }

    @PutMapping("/enableOrUnable/{id}")
    public ApiResponse<Object> enableOrUnable(@PathVariable Integer id,
                                              @RequestBody Map<String, Integer> in_use
                                              )
    {

        BuyerInformation buyerInformation = new BuyerInformation();
        if(InUseEnum.IN_USE.getValue().equals(in_use.get("in_use"))) {
            buyerInformation.setInUse(InUseEnum.IN_USE);
        }else{
            buyerInformation.setInUse(InUseEnum.NOT_USE);
        }

        if(in_use.get("in_use") == 0){//停用
            buyerInformation.setUsedTime(new Date());
        }else{
            buyerInformation.setUnUsedTime(new Date());
        }
        Wrapper<BuyerInformation> buyerInformationWrapper = new EntityWrapper<>();
        buyerInformationWrapper.eq("id", id);
        Boolean result = iBuyerInformationService.update(buyerInformation, buyerInformationWrapper);
        if(!result){
            return apiFail(500,"找不到购方信息");
        }
        return apiSuccess("");
    }
}
