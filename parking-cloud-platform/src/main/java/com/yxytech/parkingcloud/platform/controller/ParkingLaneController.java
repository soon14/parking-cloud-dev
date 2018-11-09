package com.yxytech.parkingcloud.platform.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.ParkingLane;
import com.yxytech.parkingcloud.core.service.IParkingLaneService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.ParkingLaneForm;
import com.yxytech.parkingcloud.platform.form.ParkingLaneQuery;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cj
 * @since 2017-10-18
 */
@RestController
@RequestMapping("/parkingLane")
public class ParkingLaneController extends BaseController {

    @Autowired
    private IParkingLaneService parkingLaneService;

    @GetMapping("/index")
    public ApiResponse<Object> index(@Valid @ModelAttribute("") ParkingLaneQuery query, BindingResult bindingResult)throws BindException{
        this.validate(bindingResult);

        Page<ParkingLane> p = new Page<>(query.getPage(),query.getSize());
        EntityWrapper ew = new EntityWrapper();
        try{
            ew = queryCondition(ew,query);
        }catch (Exception ex){
            return apiFail("日期格式错误:" + ex.getMessage());
        }

        p = parkingLaneService.selectPage(p,ew);

        return this.apiSuccess(p);
    }

    /**
     * 创建车道
     * @param parkingLaneForm
     * @param bindingResult
     * @return
     * @throws BindException
     */
    @PostMapping("/addLane")
    @Transactional
    public ApiResponse<Object> create(@Valid @RequestBody ParkingLaneForm parkingLaneForm,BindingResult bindingResult)throws BindException {
        validate(bindingResult);

        ParkingLane parkingLane = new ParkingLane();
        BeanUtils.copyProperties(parkingLaneForm,parkingLane);

        String existMsg = parkingLaneService.validate(parkingLane.getParkingId(),parkingLane.getParkingPortId(),parkingLane.getCode());
          if(StringUtils.isNotBlank(existMsg))
               return apiFail(existMsg);

        parkingLaneService.insert(parkingLane);

        return apiSuccess(null);
    }

    /**
     * 车道修改
     * @param parkingLaneForm
     * @param bindingResult
     * @return
     * @throws BindException
     */
    @PutMapping("/updateLane")
    @Transactional
    public ApiResponse<Object> updateParking(@Valid @RequestBody ParkingLaneForm parkingLaneForm,BindingResult bindingResult)throws BindException {
        validate(bindingResult);

        ParkingLane parkingLane = new ParkingLane();
        BeanUtils.copyProperties(parkingLaneForm,parkingLane);

        String errorMsg = parkingLaneService.updateValidate(parkingLane);
          if(errorMsg!=null)
               return apiFail(errorMsg);

        parkingLaneService.updateById(parkingLane);

        return apiSuccess(null);
    }
    /**
     * 查询车道详细信息
     * @param id
     * @return
     */
    @GetMapping("/findLaneById")
    public ApiResponse<Object> findLaneById(@RequestParam(value = "id",required = false)Long id)throws NotFoundException{
        ParkingLane parkingLane = parkingLaneService.detail(id);
        notFound(parkingLane,"车道信息不存在");

        return apiSuccess(parkingLane);
    }

    /**
     * 批量停用车道
     * @param ids
     * @return
     */
    @PutMapping("/nonUse")
    @Transactional
    public ApiResponse<Object> nonUseLane(@RequestBody String ids){
        if(StringUtils.isBlank(ids))
             return apiFail("非法参数");

        Map<String,Object> map = JSON.parseObject(ids);
        List<Long> newIds = (List<Long>) map.get("ids");

        List<ParkingLane> parkingLanes = parkingLaneService.selectBatchIds(newIds);
        if(newIds.size() > parkingLanes.size()){
            parkingLanes.forEach((item) -> newIds.remove(item.getId()));
            return apiFail("设备信息id不存在：" + newIds.toString());
        }
        parkingLanes.forEach((item) -> item.setUsing(false));
        parkingLaneService.updateBatchById(parkingLanes);

        return apiSuccess(null);
    }

    /**
     * 批量启用车道
     * @param ids
     * @return
     */
    @PutMapping("/startUsing")
    @Transactional
    public ApiResponse<Object> startUsingLane(@RequestBody String ids){
        if(StringUtils.isBlank(ids))
             return apiFail("非法参数");

        Map<String,Object> map = JSON.parseObject(ids);
        List<Long> newIds = (List<Long>) map.get("ids");

        List<ParkingLane> parkingLanes = parkingLaneService.selectBatchIds(newIds);
        if(newIds.size() > parkingLanes.size()){
            parkingLanes.forEach((item) -> newIds.remove(item.getId()));
            return apiFail("设备信息id不存在：" + newIds.toString());
        }
        parkingLanes.forEach((item) -> item.setUsing(true));
        parkingLaneService.updateBatchById(parkingLanes);

        return apiSuccess(null);
    }


    private EntityWrapper<ParkingLane> queryCondition(EntityWrapper<ParkingLane> ew,ParkingLaneQuery query)throws Exception{
        ew.eq(query.getParkingId()!=null,"yxy_parking_lane.parking_id",query.getParkingId());
        ew.eq(StringUtils.isNotBlank(query.getCode()),"yxy_parking_lane.code",query.getCode());
        ew.eq(query.getUsing()!=null,"yxy_parking_lane.is_using",query.getUsing());
        ew.eq(query.getLaneType()!=null,"lane_type",query.getLaneType());
        ew.between(DateParserUtil.parseDate(query.getStart_time(),true)!=null&&
                            DateParserUtil.parseDate(query.getEnd_time(),false)!=null,
                   "created_at",
                            DateParserUtil.parseDate(query.getStart_time(),true),
                            DateParserUtil.parseDate(query.getEnd_time(),false));
        return ew;
    }
}
