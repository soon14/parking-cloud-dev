package com.yxytech.parkingcloud.platform.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.ParkingOwner;
import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import com.yxytech.parkingcloud.core.service.IParkingOwnerService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.ParkingOwnerQuery;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
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
@RequestMapping("/parkingOwner")
public class ParkingOwnerController extends BaseController {

    @Autowired
    private IParkingOwnerService parkingOwnerService;

    @GetMapping
    public ApiResponse<Object> index(@Valid @ModelAttribute("") ParkingOwnerQuery query, BindingResult bindingResult)throws BindException{
        this.validate(bindingResult);

        Page<ParkingOwner> p = new Page<>(query.getPage(),query.getSize());
        EntityWrapper<ParkingOwner> ew = new EntityWrapper();
        try{
            ew = queryCondition(ew,query);
        }catch (Exception ex){
            return apiFail("日期格式错误:" + ex.getMessage());
        }

        p = parkingOwnerService.selectPage(p,ew);

        return  apiSuccess(p);
    }

    /**
     * 查询产权关系详情
     * @param id
     * @return
     */
    @GetMapping("/findParkingOwnerById")
    public ApiResponse<Object> findParkingOwnerById(@RequestParam(value = "id",required = false)Long id)throws NotFoundException{
        ParkingOwner parkingOwner = parkingOwnerService.selectById(id);
        notFound(parkingOwner,"产权关系信息不存在");

        return apiSuccess(parkingOwner);
    }

    /**
     * 产权关系审核通过
     * @param id
     * @return
     */
    @GetMapping("/examine/{id}")
    @Transactional
    public ApiResponse<Object> examine(@PathVariable Long id) throws NotFoundException{
        ParkingOwner parkingOwner = parkingOwnerService.selectById(id);
        notFound(parkingOwner,"产权关系信息不存在");

        ApproveEnum approveStatus = parkingOwner.getApproveStatus();
        if(!approveStatus.equals(ApproveEnum.APPROVE_ING))
              return apiFail("非法操作");

        String errorMsg = parkingOwnerService.examine(parkingOwner);
          if(StringUtils.isNotBlank(errorMsg))
               return apiFail(errorMsg);

        return apiSuccess(null);
    }

    /**
     * 产权关系审核未通过
     * @param id
     * @return
     */
    @GetMapping("/refuse/{id}")
    @Transactional
    public ApiResponse<Object> refuse(@PathVariable Long id)throws NotFoundException{
        ParkingOwner parkingOwner = parkingOwnerService.selectById(id);
        notFound(parkingOwner,"产权关系信息不存在");

        ApproveEnum approveStatus = parkingOwner.getApproveStatus();
        if(!approveStatus.equals(ApproveEnum.APPROVE_ING))
             return apiFail("非法操作");

        parkingOwner.setApproveStatus(ApproveEnum.APPROVE_FAIL);
        parkingOwnerService.updateById(parkingOwner);

        return apiSuccess(null);
    }


    private EntityWrapper<ParkingOwner> queryCondition(EntityWrapper<ParkingOwner> ew,ParkingOwnerQuery query)throws Exception{
        ew.eq(query.getOwnerOrgId()!=null,"org_id",query.getOwnerOrgId());
        ew.eq(query.getParkingId()!=null,"parking_id",query.getParkingId());
        ew.eq(query.getApproveStatus()!=null,"approve_status",query.getApproveStatus());
        ew.eq(query.getUsing()!=null,"isUsing",query.getUsing());
        ew.orderBy("created_at",false);
        ew.between(DateParserUtil.parseDate(query.getStart_time(),true)!=null&&
                            DateParserUtil.parseDate(query.getEnd_time(),false)!=null, "created_at",
                            DateParserUtil.parseDate(query.getStart_time(),true),
                            DateParserUtil.parseDate(query.getEnd_time(),false));

        return ew;
    }
}
