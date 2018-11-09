package com.yxytech.parkingcloud.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Advertisement;
import com.yxytech.parkingcloud.core.service.IAdvertisementService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/advertisement")
public class AdvertisementController extends BaseController {

    @Autowired
    protected IAdvertisementService advertisementService;

    @GetMapping("/index")
    public ApiResponse<Object> index(@RequestParam(value = "position", required = false) Integer position,
                                     @RequestParam(value = "title", required = false) String title) {
        EntityWrapper<Advertisement> ew = new EntityWrapper<>();

        ew.like(StringUtils.isNotBlank(title), "title", title).
                where("status",true).
                orderBy("sort",true);
        ew.eq(position != null, "position", position);

        List data = advertisementService.selectList(ew);

        return this.apiSuccess(data);
    }
}
