package com.yxytech.parkingcloud.platform.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import com.yxytech.parkingcloud.core.service.IAreaService;
import com.yxytech.parkingcloud.core.service.IFeeSchemaParkingService;
import com.yxytech.parkingcloud.core.service.IMagneticDeviceService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.ParkingApproveForm;
import com.yxytech.parkingcloud.platform.form.ParkingBindFeeschemaForm;
import com.yxytech.parkingcloud.platform.form.ParkingForm;
import com.yxytech.parkingcloud.platform.form.ParkingQuery;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author guowei
 * @since 2017-10-31
 */
@RestController
@RequestMapping("/parking")
public class ParkingController extends BaseController {

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IAreaService areaService;

    @Autowired
    private IMagneticDeviceService magneticDeviceService;

    @Autowired
    private IFeeSchemaParkingService feeSchemaParkingService;

    private static final Double radius = 0.01;

    /**
     *
     * @param query
     * @param bindingResult
     * @return
     * @throws BindException
     */
    @GetMapping("/index")
    @Access(permissionName = "停车场查询",permissionCode = "PARKING_QUERY",moduleCode = "basic_information")
    public ApiResponse<Object> index(@Valid @ModelAttribute("") ParkingQuery query, BindingResult bindingResult)throws BindException{
        this.validate(bindingResult);

        Page<Parking> p = new Page<Parking>(query.getPage(),query.getSize());
        EntityWrapper<Parking> ew = new EntityWrapper<Parking>();
        try{
            ew = queryCondition(ew,query);
        }catch (Exception ex){
            return apiFail("日期格式错误:" + ex.getMessage());
        }

        return this.apiSuccess(parkingService.selectByPage(p, ew));
    }

    /**
     * 新建停车场信息
     * @return
     */
    @PostMapping("/addParking")
    @Access(permissionName = "停车场创建",permissionCode = "PARKING_CREATE",moduleCode = "basic_information")
    public ApiResponse<Object> addParking(@Valid @RequestBody ParkingForm parkingForm,BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        Parking parking = new Parking();
        BeanUtils.copyProperties(parkingForm,parking);
        String fullName = parking.getFullName();

        String existMsg = parkingService.validate(fullName);
          if(StringUtils.isNotBlank(existMsg))
               return apiFail(existMsg);

        String gpsMsg = magneticDeviceService.validateGps(parking.getGps());
          if(StringUtils.isNotBlank(gpsMsg))
               return apiFail(gpsMsg);

        parking.setGps(magneticDeviceService.formattingGps(parking.getGps()));
        parkingService.createParking(parking);

        return apiSuccess(null);
    }

    /**
     * 修改停车场信息
     * @param parkingForm
     * @param bindingResult
     * @return
     * @throws BindException
     */
    @PutMapping("/updateParking")
    @Access(permissionName = "停车场修改",permissionCode = "PARKING_UPDATE",moduleCode = "basic_information")
    public ApiResponse<Object> updateParking(@Valid @RequestBody ParkingForm parkingForm,BindingResult bindingResult)throws BindException {
        validate(bindingResult);

        Parking parking = new Parking();
        BeanUtils.copyProperties(parkingForm,parking);

        String errorMsg = parkingService.updateValidate(parking);
          if(errorMsg!=null)
               return apiFail(errorMsg);

        String gpsMsg = magneticDeviceService.validateGps(parking.getGps());
          if(StringUtils.isNotBlank(gpsMsg))
               return apiFail(gpsMsg);

        parking.setGps(magneticDeviceService.formattingGps(parking.getGps()));
        parkingService.updateParking(parking);

        return apiSuccess(null);
    }

    /**
     * 根据停车场ID查询停车场详细信息
     * @param id
     * @return
     */
    @GetMapping("/findParkingById")
    public ApiResponse<Object> findParkingById(@RequestParam(value = "id",required = false)Long id)throws NotFoundException{
        Parking parking = parkingService.detail(id);
        notFound(parking,"停车场不存在");

        parking.setGps(String.format("%.6f", parking.getLongitude()) + " " + String.format("%.6f", parking.getLatitude()));

        Long streetId =  parking.getStreetAreaId();
        Area street = areaService.selectById(streetId);
        Long districtId = street.getParentId();
        Area district = areaService.selectById(districtId);

        Long cityId = district.getParentId();
        Long[] areaIds = {cityId,districtId,streetId};
        HashMap hashMap = new HashMap();
        hashMap.put("parking",parking);
        hashMap.put("areaIds",areaIds);
        hashMap.put("areas",parkingService.getAreas(parking.getStreetAreaId()));

        return apiSuccess(hashMap);
    }

    /**
     * 停用停车场
     * @param ids
     * @return
     */
    @PutMapping("/nonUse")
    @Transactional
    @Access(permissionName = "停车场停用",permissionCode = "PARKING_NONUSE",moduleCode = "basic_information")
    public ApiResponse<Object> nonUse(@RequestBody String ids){
        if(StringUtils.isBlank(ids))
             return apiFail("非法参数");

        Map<String,Object> map = JSON.parseObject(ids);
        List<Long> newIds = (List<Long>) map.get("ids");

        List<Parking> parkings = parkingService.selectBatchIds(newIds);
            if(newIds.size() > parkings.size()){
                parkings.forEach((item) -> newIds.remove(item.getId()));
                return apiFail("设备信息id不存在：" + newIds.toString());
            }
        List<Parking> toUpdateParkingList = new ArrayList<>();
        for(Parking parking : parkings){
            parking.setUsing(false);
            parking.setGps(null);
            toUpdateParkingList.add(parking);
        }
        parkingService.updateBatch(toUpdateParkingList);

        return apiSuccess(null);
    }

    /**
     * 启用停车场
     * @param ids
     * @return
     */
    @PutMapping("/startUsing")
    @Transactional
    @Access(permissionName = "停车场启用",permissionCode = "PARKING_USING",moduleCode = "basic_information")
    public ApiResponse<Object> StartUsing(@RequestBody String ids){
        if(StringUtils.isBlank(ids))
             return apiFail("非法参数");

        Map<String,Object> map = JSON.parseObject(ids);
        List<Long> newIds = (List<Long>) map.get("ids");

        List<Parking> parkings = parkingService.selectBatchIds(newIds);
        if(newIds.size() > parkings.size()){
            parkings.forEach((item) -> newIds.remove(item.getId()));
            return apiFail("设备信息id不存在：" + newIds.toString());
        }

        List<Parking> toUpdateParkingList = new ArrayList<>();
        for(Parking parking : parkings){
            parking.setUsing(true);
            parking.setGps(null);
            toUpdateParkingList.add(parking);
        }
        parkingService.updateBatch(toUpdateParkingList);

        return apiSuccess(null);
    }

    /**
     * 查询所有的停车场
     * @return
     */
    @GetMapping("/findAllParking")
    public ApiResponse<Object> findAllParking(@RequestParam(value = "parking",required = false)String parking){
        if(StringUtils.isBlank(parking))
             return apiSuccess("");

        List<Parking> parkingList = parkingService.findAll(parking);

        return apiSuccess(parkingList);
    }

    /**
     * 停车场认证审核通过
     * @param id
     * @return
     */
    @GetMapping("/examine/{id}")
    @Transactional
    @Access(permissionName = "停车场认证审核",permissionCode = "PARKING_CHECK",moduleCode = "basic_information")
    public ApiResponse<Object> examine(@PathVariable Long id)throws NotFoundException{
        Parking parking = parkingService.selectById(id);
        notFound(parking,"停车场不存在");

        ApproveEnum approveStatus = parking.getApproveStatus();
        if(!approveStatus.equals(ApproveEnum.APPROVE_ING))
             return apiFail("非法操作");

        String errorMsg = parkingService.examine(parking);
          if(StringUtils.isNotBlank(errorMsg))
               return apiFail(errorMsg);

        return apiSuccess(null);
    }

    /**
     * 停车场认证审核未通过
     * @param id
     * @return
     */
    @GetMapping("/refuse/{id}")
    @Transactional
    public ApiResponse<Object> refuse(@PathVariable Long id)throws NotFoundException{
        Parking parking = parkingService.selectById(id);
        notFound(parking,"停车场不存在");

        ApproveEnum approveStatus = parking.getApproveStatus();
        if(!approveStatus.equals(ApproveEnum.APPROVE_ING))
             return apiFail("非法操作");

        parking.setApproveStatus(ApproveEnum.APPROVE_FAIL);
        parking.setGps(null);
        parkingService.updateParking(parking);

        return apiSuccess(null);
    }


    /**
     * 停车场认证
     * @param id
     * @param approveForm
     * @param bindingResult
     * @return
     * @throws BindException
     */
    @PutMapping("/approve/{id}")
    @Transactional
    @Access(permissionName = "停车场认证",permissionCode = "PARKING_APPROVE",moduleCode = "basic_information")
    public ApiResponse<Object> approveParking(@PathVariable Long id, @Valid @RequestBody ParkingApproveForm approveForm, BindingResult bindingResult)
                                              throws BindException,NotFoundException{
        validate(bindingResult);

        Parking parking = parkingService.selectById(id);
        notFound(parking,"停车场不存在");

        ParkingOwner parkingOwner = new ParkingOwner(parking.getId());
        ParkingOperator parkingOperator = new ParkingOperator(parking.getId());

        BeanUtils.copyProperties(approveForm,parkingOwner);
        BeanUtils.copyProperties(approveForm,parkingOperator);
        BeanUtils.copyProperties(approveForm,parking);

        parkingService.approve(parking,parkingOwner,parkingOperator);

        return apiSuccess(null);
    }

    /**
     * 查询所有的认证状态
     * @return
     */
    @GetMapping("/findAllApproves")
    public ApiResponse<Object> findAllApproveEnum(){
        List data = new ArrayList();
        for(ApproveEnum approveEnum : ApproveEnum.values()){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id",approveEnum.getValue());
            map.put("name",approveEnum.getDesc());
            data.add(map);
        }

        return apiSuccess(data);
    }

    @PostMapping("/nearParking")
    @Access(permissionName = "停车场地图可视化",permissionCode = "NEAR_PARKING",moduleCode = "statictical_analysis")
    public ApiResponse getNearParking(@RequestBody ParkingInfoForApp parkingInfo) {
        if (parkingInfo.getLongitude() == null
                || parkingInfo.getLatitude() == null)
                       return apiFail("参数不能为空!");

        List<ParkingInfoForApp> nearParkingList = parkingService.getNearParking(parkingInfo.getLongitude(),
                parkingInfo.getLatitude(), radius);

        return apiSuccess(nearParkingList);
    }

    @GetMapping("/findParkingByArea/{id}")
    public ApiResponse<Object> findParkingByArea(@PathVariable Long id)throws NotFoundException{
        Area area = areaService.selectById(id);
        notFound(area,"区域不存在");

        List<Parking> parkings = parkingService.findByArea(id);

        return apiSuccess(parkings);
    }

    /**
     * 根据停车场名称模糊查询
     * @param name
     * @return
     */
    @GetMapping("/search")
    public ApiResponse<Object> search(@RequestParam(value = "name",required = false)String name){
        EntityWrapper<Parking> ew = new EntityWrapper<>();
        ew.like("full_name",name);

        List<Parking> parkings = parkingService.selectList(ew);

        return apiSuccess(parkings);
    }


    /**
     * 停车场绑定费率计划
     * @param
     * @return
     * @throws NotFoundException
     */
    @PostMapping("/bindFeeSchema")
    @Transactional
    @Access(permissionName = "停车场绑定费率",permissionCode = "PARKING_BIND_SCHEMA",moduleCode = "basic_information")
    public ApiResponse<Object> bindFeeSchema(@Valid @RequestBody ParkingBindFeeschemaForm parkingBindFeeschemaForm,BindingResult bindingResult)throws NotFoundException,BindException{
        validate(bindingResult);

        Parking parking = parkingService.selectById(parkingBindFeeschemaForm.getParkingId());
        notFound(parking,"停车场不存在");

        try {
            String errorMsg = feeSchemaParkingService.bindSchema(parkingBindFeeschemaForm.getParkingId(), parkingBindFeeschemaForm.getSchemaIds());

            if(errorMsg != null)
                return apiFail(errorMsg);

            return apiSuccess(null);
        } catch (Exception e) {
            return this.apiFail(e.getMessage());
        }
    }

    /**
     * 获取停车场绑定的所有费率计划的ID
     * @param parkingId
     * @return
     */
    @GetMapping("/allBindSchema/{parkingId}")
    public ApiResponse<Object> allBindSchema(@PathVariable Long parkingId){
        EntityWrapper<FeeSchemaParking> ew = new EntityWrapper<>();
        ew.setSqlSelect("fee_schema_id").eq("parking_id",parkingId);
        List<Long> feeSchemaIds = ListUtils.typedList(feeSchemaParkingService.selectObjs(ew),Long.class);

        return apiSuccess(feeSchemaIds);
    }


    private EntityWrapper<Parking> queryCondition(EntityWrapper<Parking> ew,ParkingQuery query)throws Exception{
        ew.eq(StringUtils.isNotBlank(query.getCode()),"p.code",query.getCode());
        ew.like(StringUtils.isNotBlank(query.getFullName()),"p.full_name",query.getFullName());
        ew.like(StringUtils.isNotBlank(query.getShortName()),"p.short_name",query.getShortName());
        // 0 路内 1路外
        ew.eq(query.getRoadType()!=null,"road_type",query.getRoadType());
        // 0 封闭 1 开放
        ew.eq(query.getOpenType()!=null,"open_type",query.getOpenType());
        // 0 收费 1 免费
        ew.eq(query.getFeeType()!=null,"fee_type",query.getFeeType());
        ew.eq(query.getOwnerOrgId()!=null,"owner_org_id",query.getOwnerOrgId());
        ew.eq(query.getOperatorOrgId()!=null,"operator_org_id",query.getOperatorOrgId());
        ew.eq(query.getStreetAreaId()!=null,"street_area_id",query.getStreetAreaId());
        ew.eq(query.getUsing()!=null,"p.is_using",query.getUsing());
        ew.eq(query.getApproveStatus()!=null,"p.approve_status",query.getApproveStatus());
        ew.eq(query.getAllDay()!=null,"all_day",query.getAllDay());
        ew.between(DateParserUtil.parseDate(query.getStart_time(),true)!=null&&
                            DateParserUtil.parseDate(query.getEnd_time(),false)!=null,
                   "p.created_at",
                            DateParserUtil.parseDate(query.getStart_time(),true),
                            DateParserUtil.parseDate(query.getEnd_time(),false));
        return ew;
    }
}
