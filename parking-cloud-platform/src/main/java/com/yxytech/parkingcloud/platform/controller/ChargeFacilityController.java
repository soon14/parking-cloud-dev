package com.yxytech.parkingcloud.platform.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.ChargeFacility;
import com.yxytech.parkingcloud.core.entity.ChargeFacilityBatch;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.service.IChargeFacilityService;
import com.yxytech.parkingcloud.core.service.IOrganizationService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.ChargeFacilityForm;
import com.yxytech.parkingcloud.platform.form.ChargeFacilityQuery;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cj
 * @since 2017-10-24
 */
@RestController
@RequestMapping("/chargeFacility")
public class ChargeFacilityController extends BaseController {

    @Autowired
    private IChargeFacilityService chargeFacilityService;

    @Autowired
    private IOrganizationService organizationService;

    /**
     * 查询移动收费设备信息
     * @return
     */
    @GetMapping("/index")
    @Access(permissionName = "设备查询",permissionCode = "DEVICE_QUERY",moduleCode = "equipment_manage")
    public ApiResponse<Object> index(@Valid @ModelAttribute("") ChargeFacilityQuery query, BindingResult bindingResult)throws BindException {
        this.validate(bindingResult);

        Page<ChargeFacility> p = new Page<>(query.getPage(),query.getSize());
        EntityWrapper<ChargeFacility> ew = new EntityWrapper<>();
        try{
            ew = queryCondition(ew,query);
        }catch (Exception ex){
            return apiFail("日期格式错误:" + ex.getMessage());
        }

        p = chargeFacilityService.selectByPage(p, ew);

        return apiSuccess(p);
    }

    /**
     * 查询移动收费设备详情
     *
     * @param id
     * @return
     */
    @GetMapping("/findChargeFacilityById")
    public ApiResponse<Object> findChargeFacilityById(@RequestParam(value = "id", required = false) Long id)throws NotFoundException{
        ChargeFacility chargeFacility = chargeFacilityService.detail(id);
        notFound(chargeFacility,"收费终端设备信息不存在");

        return apiSuccess(chargeFacility);
    }

    /**
     * 修改移动收费设备信息
     *
     * @return
     */
    @PutMapping("/updateChargeFacility")
    @Transactional
    @Access(permissionName = "设备修改",permissionCode = "DEVICE_UPDATE",moduleCode = "equipment_manage")
    public ApiResponse<Object> updateChargeFacility(@Valid @RequestBody ChargeFacilityForm chargeFacilityForm,BindingResult bindingResult)throws BindException {
        validate(bindingResult);

        ChargeFacility chargeFacility = new ChargeFacility();
        BeanUtils.copyProperties(chargeFacilityForm,chargeFacility);

        String errorMsg = chargeFacilityService.updateValidate(chargeFacility);
          if(StringUtils.isNotBlank(errorMsg))
             return apiFail(errorMsg);

        chargeFacilityService.updateById(chargeFacility);

        return apiSuccess(null);
    }

    /**
     * 添加移动收费设备
     *
     * @return
     */
    @PostMapping("/addChargeFacility")
    @Transactional
    @Access(permissionName = "设备创建",permissionCode = "DEVICE_CREATE",moduleCode = "equipment_manage")
    public ApiResponse<Object> addChargeFacility(@Valid @RequestBody ChargeFacilityForm chargeFacilityForm,BindingResult bindingResult)throws BindException {
        validate(bindingResult);

        ChargeFacility chargeFacility = new ChargeFacility();
        BeanUtils.copyProperties(chargeFacilityForm,chargeFacility);

        String errorMsg = chargeFacilityService.validate(null,chargeFacility.getManageOrgId(), chargeFacility.getParkingId(),
                                                           chargeFacility.getFacOrgId(), chargeFacility.getSn(),ChargeFacility.class);
        if(StringUtils.isNotBlank(errorMsg))
            return apiFail(errorMsg);

        chargeFacility.setPutInAt(new Date());
        chargeFacilityService.insert(chargeFacility);

        EntityWrapper<Organization> ew = new EntityWrapper<>();
        ew.setSqlSelect("is_facility_org","id").eq("id",chargeFacility.getFacOrgId());
        Organization facOrg = organizationService.selectOne(ew);
        if(!facOrg.getFacilityOrg()){
            facOrg.setFacilityOrg(true);
            organizationService.updateById(facOrg);
        }

        return apiSuccess(null);
    }

    /**
     * 批量启用设备
     *
     * @param ids
     * @return
     */
    @PutMapping("/startUsing")
    @Transactional
    @Access(permissionName = "设备启用",permissionCode = "DEVICE_USING",moduleCode = "equipment_manage")
    public ApiResponse<Object> startUsing(@RequestBody String ids)throws NotFoundException {
        if(StringUtils.isBlank(ids))
             return apiFail("非法参数");

        Map<String, Object> map = JSON.parseObject(ids);
        List<Long> newIds = (List<Long>) map.get("ids");

        List<ChargeFacility> chargeFacilities = chargeFacilityService.selectBatchIds(newIds);
        if(newIds.size() > chargeFacilities.size()){
            chargeFacilities.forEach((item) -> newIds.remove(item.getId()));
            return apiFail("设备信息id不存在：" + newIds.toString());
        }
        chargeFacilities.forEach((item) -> item.setStatus(true));
        chargeFacilityService.updateBatchById(chargeFacilities);

        return apiSuccess(null);
    }

    /**
     * 批量停用设备
     * @param ids
     * @return
     */
    @PutMapping("/nonUse")
    @Transactional
    @Access(permissionName = "设备停用",permissionCode = "DEVICE_NONUSE",moduleCode = "equipment_manage")
    public ApiResponse<Object> nonUse(@RequestBody String ids)throws NotFoundException{
        if(StringUtils.isEmpty(ids))
             return apiFail("非法参数");

        Map<String, Object> map = JSON.parseObject(ids);
        List<Long> newIds = (List<Long>) map.get("ids");

        List<ChargeFacility> chargeFacilities = chargeFacilityService.selectBatchIds(newIds);
        if(newIds.size() > chargeFacilities.size()){
           chargeFacilities.forEach((item) -> newIds.remove(item.getId()));
           return apiFail("设备信息id不存在：" + newIds.toString());
        }
        chargeFacilities.forEach((item) -> item.setStatus(false));
        chargeFacilityService.updateBatchById(chargeFacilities);

        return this.apiSuccess(null);
    }

    /**
     * 批量导入
     * @param list
     * @return
     */
    @PostMapping("/batch")
    @Transactional
    public ApiResponse<Object> createBatch(@RequestBody List<ChargeFacilityBatch> list){
        String errorMsg = chargeFacilityService.createBatch(list,ChargeFacility.class);
          if(StringUtils.isNotBlank(errorMsg))
               return apiFail(errorMsg);

        return apiSuccess(null);
    }

    /**
     * 查询所有的移动收费设备终端信息
     * @return
     */
    @GetMapping("/findAllChargeFacility/{id}")
    public ApiResponse<Object> findAll(@PathVariable Long id){
        EntityWrapper<ChargeFacility> ew = new EntityWrapper<>();
        ew.eq("parking_id",id);

        return this.apiSuccess(chargeFacilityService.selectList(ew));
    }


    private EntityWrapper<ChargeFacility> queryCondition(EntityWrapper<ChargeFacility> ew,ChargeFacilityQuery query)throws Exception{
        ew.eq(StringUtils.isNotBlank(query.getSystemNumber()),"system_number", query.getSystemNumber());
        ew.eq(StringUtils.isNotBlank(query.getSn()),"sn",query.getSn());
        ew.eq(query.getStatus()!=null,"status",query.getStatus());
        ew.eq(query.getPutInAt()!=null,"put_in_at", query.getPutInAt());
        ew.eq(query.getPutOutAt()!=null,"put_out_at",query.getPutOutAt());
        ew.eq(query.getStartusingAt()!=null,"startusing_at",query.getStartusingAt());
        ew.eq(query.getFacOrgId()!=null,"fac_org_id", query.getFacOrgId());
        ew.eq(StringUtils.isNotBlank(query.getIp()),"ip",query.getIp());
        ew.eq(StringUtils.isNotBlank(query.getImei()),"imei",query.getImei());
        ew.eq(query.getManageOrgId()!=null,"manage_org_id",query.getManageOrgId());
        ew.eq(StringUtils.isNotBlank(query.getManager()),"manager", query.getManager());
        ew.eq(query.getParkingId()!=null,"parking_id", query.getParkingId());
        ew.between(StringUtils.isNotBlank(query.getStart_time()) &&
                            StringUtils.isNotBlank(query.getEnd_time()),
                   "put_in_at",
                            DateParserUtil.parseDate(query.getStart_time(),true),
                            DateParserUtil.parseDate(query.getEnd_time(),false));
        return ew;
    }

}
