package com.yxytech.parkingcloud.platform.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.ParkingOperator;
import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import com.yxytech.parkingcloud.core.service.IParkingOperatorService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.ParkingOperatorForm;
import com.yxytech.parkingcloud.platform.form.ParkingOperatorQuery;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cj
 * @since 2017-10-19
 */
@RestController
@RequestMapping("/parkingOperator")
public class ParkingOperatorController extends BaseController {

    @Autowired
    private IParkingOperatorService parkingOperatorService;

    /**
     * 查询经营关系信息
     *
     * @return
     */
    @GetMapping("/index")
    public ApiResponse<Object> index(@Valid @ModelAttribute("") ParkingOperatorQuery query, BindingResult bindingResult)throws BindException{
        this.validate(bindingResult);

        Page<ParkingOperator> p = new Page<>(query.getPage(),query.getSize());
        EntityWrapper<ParkingOperator> ew = new EntityWrapper<>();
        try{
            ew = queryCondition(ew,query);
        }catch (Exception ex){
            return apiFail("日期格式错误:" + ex.getMessage());
        }

        p = parkingOperatorService.selectPage(p,ew);

        return apiSuccess(p);
    }



    /**
     * 查询经营关系详情
     * @param id
     * @return
     */
    @GetMapping("/findParkingOperatorById")
    public ApiResponse<Object> findById(@RequestParam(value = "id",required = false)Long id)throws NotFoundException{
        ParkingOperator parkingOperator = parkingOperatorService.selectById(id);
        notFound(parkingOperator,"经营关系信息不存在");

        return apiSuccess(parkingOperator);
    }

    /**
     * 修改经营关系信息
     * @param parkingOperatorForm
     * @param bindingResult
     * @return
     * @throws BindException
     */
    @PutMapping("/updateParkingOperator")
    @Transactional
    public ApiResponse<Object> updateParkingOperator(@Valid @RequestBody ParkingOperatorForm parkingOperatorForm,BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        ParkingOperator parkingOperator = new ParkingOperator();
        BeanUtils.copyProperties(parkingOperatorForm,parkingOperator);

        parkingOperatorService.updateById(parkingOperator);

        return apiSuccess(null);
    }

    /**
     * 经营关系审核通过
     * @param id
     * @return
     */
    @GetMapping("/examine/{id}")
    @Transactional
    public ApiResponse<Object> examine(@PathVariable Long id)throws NotFoundException{
        ParkingOperator parkingOperator = parkingOperatorService.selectById(id);
        notFound(parkingOperator,"经营关系信息不存在");

        ApproveEnum approveStatus = parkingOperator.getApproveStatus();
          if(!approveStatus.equals(ApproveEnum.APPROVE_ING))
               return apiFail("非法操作");

        String errorMsg = parkingOperatorService.examine(parkingOperator);
          if(StringUtils.isNotBlank(errorMsg))
               return apiFail(errorMsg);

        return apiSuccess(null);
    }

    /**
     * 经营关系审核未通过
     * @param id
     * @return
     */
    @GetMapping("/refuse/{id}")
    @Transactional
    public ApiResponse<Object> refuse(@PathVariable Long id)throws NotFoundException{
        ParkingOperator parkingOperator = parkingOperatorService.selectById(id);
        notFound(parkingOperator,"经营关系信息不存在");

        ApproveEnum approveStatus = parkingOperator.getApproveStatus();
        if(!approveStatus.equals(ApproveEnum.APPROVE_ING))
             return apiFail("非法操作");

        parkingOperator.setApproveStatus(ApproveEnum.APPROVE_FAIL);
        parkingOperatorService.updateById(parkingOperator);

        return apiSuccess(null);
    }


    private EntityWrapper<ParkingOperator> queryCondition(EntityWrapper<ParkingOperator> ew,ParkingOperatorQuery query)throws Exception{
        ew.eq(query.getOperatorOrgId()!=null,"org_id",query.getOperatorOrgId());
        ew.eq(query.getParkingId()!=null,"parking_id",query.getParkingId());
        ew.eq(query.getUsing()!=null,"is_using",query.getUsing());
        ew.eq(query.getApproveStatus()!=null,"approve_status",query.getApproveStatus());
        ew.orderBy("created_at",false);
        ew.between(DateParserUtil.parseDate(query.getStart_time(),true)!=null&&
                            DateParserUtil.parseDate(query.getEnd_time(),false)!=null,
                   "created_at",
                            DateParserUtil.parseDate(query.getStart_time(),true),
                            DateParserUtil.parseDate(query.getEnd_time(),false));
        return ew;
    }
}
