package com.yxytech.parkingcloud.platform.controller;

import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.FeeFestival;
import com.yxytech.parkingcloud.core.entity.FeeFestivalItem;
import com.yxytech.parkingcloud.core.service.IFeeFestivalItemService;
import com.yxytech.parkingcloud.core.service.IFeeFestivalService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.FeeFestivalForm;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/feeFestival")
public class FeeFestivalController extends BaseController{

    @Autowired
    private IFeeFestivalService feeFestivalService;

    @Autowired
    private IFeeFestivalItemService feeFestivalItemService;

    /**
     * 批量导入节假日
     * @param
     * @return
     */
    @PostMapping("/batch/{year}")
    @Transactional
    @Access(permissionName = "假日新增",permissionCode = "FEE_FESTIVAL_CREATE",moduleCode = "system_manage")
    public ApiResponse<Object> createBatch(@Valid @PathVariable Integer year, @RequestBody List<FeeFestivalForm> feeFestivalForms, BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        if(feeFestivalForms.isEmpty() || year == null)
             return apiFail("非法参数");

        feeFestivalService.deleteThisYear(year);
        List<FeeFestival> feeFestivals = new ArrayList<>();
        for(FeeFestivalForm feeFestivalForm : feeFestivalForms){
            try{
                Date startTime = DateParserUtil.parseDate(feeFestivalForm.getStartTime(),true);
                Date endTime = DateParserUtil.parseDate(feeFestivalForm.getEndTime(),false);
                FeeFestival feeFestival = new FeeFestival(feeFestivalForm.getName(),startTime,endTime,feeFestivalForm.getNote());
                feeFestivals.add(feeFestival);
            }catch (Exception e){
                return apiFail("日期格式错误:" + e.getMessage());
            }
        }
        feeFestivalService.insertBatch(feeFestivals);

        List<FeeFestival> feeFestivalList = feeFestivalService.findByYear(year);
        List<FeeFestivalItem> feeFestivalItemList = new ArrayList<>();
        feeFestivalList.forEach(item -> feeFestivalItemList.add(new FeeFestivalItem(item.getId(),item.getStartTime())));
        feeFestivalItemService.insertBatch(feeFestivalItemList);

        return apiSuccess(null);
    }

    /**
     * 根据年份查询节假日
     * @param year
     * @return
     */
    @GetMapping("/findFeeFestivalByYear")
    public ApiResponse<Object> find(@RequestParam(value = "year",required = false)String year)throws NotFoundException{
        List<FeeFestival> feeFestivals = feeFestivalService.findByYear(Integer.parseInt(year));
        notFound(feeFestivals,"假日信息不存在");

        return apiSuccess(feeFestivals);
    }
}
