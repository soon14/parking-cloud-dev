package com.yxytech.parkingcloud.app.controller;

import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.enums.ColorsEnum;
import com.yxytech.parkingcloud.core.utils.EnumToMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController extends BaseController {

    @GetMapping("/plateTypes")
    public ApiResponse getAllColor() {
        EnumToMap<ColorsEnum> enumToMap = new EnumToMap<>();

        return this.apiSuccess(enumToMap.translateEnum(ColorsEnum.class));
    }

    @GetMapping("/carTypes")
    public ApiResponse getAllCarType() {
        EnumToMap<CarTypeEnum> enumToMap = new EnumToMap<>();

        return this.apiSuccess(enumToMap.translateEnum(CarTypeEnum.class, true));
    }
}
