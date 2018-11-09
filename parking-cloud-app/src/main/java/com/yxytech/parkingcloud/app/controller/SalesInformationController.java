package com.yxytech.parkingcloud.app.controller;


import com.yxytech.parkingcloud.app.controller.form.SalesInformationForm;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.SalesInformation;
import com.yxytech.parkingcloud.core.service.ISalesInformationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/sales")
public class SalesInformationController extends BaseController{

    @Autowired
    private ISalesInformationService salesInformationService;

    /**
     * 新增
     * @param salesInformationForm
     * @param SalesInformationErrors
     * @return
     * @throws BindException
     */
    @PostMapping("/addSale")
    public ApiResponse<Object> addSale(@Valid @RequestBody SalesInformationForm salesInformationForm,
                                       BindingResult SalesInformationErrors) throws BindException
    {
        validate(SalesInformationErrors);
        SalesInformation salesInformation = new SalesInformation();
        BeanUtils.copyProperties(salesInformationForm, salesInformation);
        salesInformationService.insert(salesInformation);
        return apiSuccess("");
    }

}
