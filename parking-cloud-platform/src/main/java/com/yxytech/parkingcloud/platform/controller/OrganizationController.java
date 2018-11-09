package com.yxytech.parkingcloud.platform.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.enums.ApproveEnum;
import com.yxytech.parkingcloud.core.enums.OrgNatureEnum;
import com.yxytech.parkingcloud.core.service.IOrgAreaService;
import com.yxytech.parkingcloud.core.service.IOrgParkingService;
import com.yxytech.parkingcloud.core.service.IOrganizationService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.OrgApproveForm;
import com.yxytech.parkingcloud.platform.form.OrgBindForm;
import com.yxytech.parkingcloud.platform.form.OrganizationForm;
import com.yxytech.parkingcloud.platform.form.OrganizationQuery;
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
 * 单位信息控制层
 * @author cj
 * @since 2017-10-18
 */
@RestController
@RequestMapping("/organzation")
public class OrganizationController extends BaseController {

    /** 注入单位信息业务层对象 */
    @Autowired
    private IOrganizationService organzationService;

    @Autowired
    private IOrgAreaService orgAreaService;

    @Autowired
    private IOrgParkingService orgParkingService;


    /**
     * 分页查询区域内所有单位信息
     * @return
     */
    @GetMapping("/index")
    @Access(permissionName = "单位查询",permissionCode = "ORG_QUERY",moduleCode = "basic_information")
    public ApiResponse<Object> index(@Valid @ModelAttribute("") OrganizationQuery query, BindingResult bindingResult)throws BindException{
        this.validate(bindingResult);

        Page<Organization> p = new Page<>(query.getPage(),query.getSize());
        EntityWrapper<Organization> ew = new EntityWrapper<>();
        try{
            ew = queryCondition(ew,query);
        }catch (Exception ex){
            return apiFail("日期格式错误:" + ex.getMessage());
        }

        p = organzationService.selectByPage(p,ew);

        return apiSuccess(p);
    }

    /**
     * 根据单位ID查询单位详细信息
     * @param id
     * @return
     */
    @GetMapping("/findOrgById")
    public ApiResponse<Object> findById(@RequestParam(value = "id",required = false) Long id)throws NotFoundException{
        Organization organization = organzationService.selectById(id);
        notFound(organization,"单位信息不存在");

        List data = organzationService.detail(organization);

        return apiSuccess(data);
    }


    /**
     * 创建单位信息
     * @return
     */
    @PostMapping("/addOrg")
    @Transactional
    @Access(permissionName = "单位新建",permissionCode = "ORG_CREATE",moduleCode = "basic_information")
    public ApiResponse<Object> addOrganzation(@Valid @RequestBody OrganizationForm organizationForm, BindingResult bindingResult)throws  BindException{
        validate(bindingResult);

        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationForm, organization);

        String existMsg = organzationService.validate(organization.getFullName());
          if(StringUtils.isNotBlank(existMsg))
               return apiFail(existMsg);

        organzationService.insert(organization);

        return apiSuccess(null);
    }

    /**
     * 修改单位信息
     * @return
     */
    @PutMapping("/updateOrg")
    @Transactional
    @Access(permissionName = "单位修改",permissionCode = "ORG_UPDATE",moduleCode = "basic_information")
    public ApiResponse<Object> updateOrg(@Valid @RequestBody OrganizationForm organizationForm, BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationForm, organization);

        String errorMsg = organzationService.updateValidate(organization);
          if(errorMsg!=null)
               return apiFail(errorMsg);

        organzationService.updateById(organization);

        return apiSuccess(null);
    }

    /**
     * 查询所有单位性质
     * @return
     */
    @GetMapping("/findAllOrgNature")
    public ApiResponse<Object> findAllOrgNature(){
        List data = new ArrayList();
        for(OrgNatureEnum value : OrgNatureEnum.values()){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id",value.getValue());
            map.put("name",value.getDesc());
            data.add(map);
        }

        return apiSuccess(data);
    }

    /**
     * 查询所有的产权单位
     * @return
     */
    @GetMapping("/findAllOwnerOrg")
    public ApiResponse<Object> findAllOwnerOrg(@RequestParam(value = "ownerOrg",required = false)String ownerOrg){
        if(StringUtils.isBlank(ownerOrg))
             return apiSuccess("");

        return apiSuccess(organzationService.findAllOwnerOrg(ownerOrg));
    }

    /**
     * 查询所有的经营单位
     * @return
     */
    @GetMapping("/findAllManageOrg")
    public ApiResponse<Object> findAllManageOrg(@RequestParam(value = "manageOrg",required = false)String manageOrg){
        if(StringUtils.isBlank(manageOrg))
             return apiSuccess("");

        return apiSuccess(organzationService.findAllManageOrg(manageOrg));
    }

    /**
     * 查询所有的设备厂商
     * @return
     */
    @GetMapping("/findAllFacilityOrg")
    public ApiResponse<Object> findAllFacilityOrg(@RequestParam(value = "facilityOrg",required = false)String facilityOrg){
        if(StringUtils.isBlank(facilityOrg))
             return apiSuccess("");

        return apiSuccess(organzationService.findAllFacilityOrg(facilityOrg));
    }

    /**
     * 单位认证
     * @param id
     * @param orgApproveForm
     * @param bindingResult
     * @return
     * @throws BindException
     */
    @PutMapping("/approve/{id}")
    @Transactional
    @Access(permissionName = "单位认证",permissionCode = "ORG_APPROVE",moduleCode = "basic_information")
    public ApiResponse<Object> approve(@PathVariable Long id,@Valid @RequestBody OrgApproveForm orgApproveForm,BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        Organization organization = organzationService.selectById(id);
            if(organization.getApproveStatus().equals(ApproveEnum.APPROVE_ING) &&
                    organization.getApproveStatus().equals(ApproveEnum.APPROVE_SUCCESS)){
                return this.apiFail("请勿重复认证");
            }

        BeanUtils.copyProperties(orgApproveForm, organization);
        organization.setApproveStatus(ApproveEnum.APPROVE_ING);

        organzationService.updateById(organization);

        return apiSuccess(null);
    }

    /**
     * 单位认证审核通过
     * @param id
     * @return
     */
    @GetMapping("/examine/{id}")
    @Transactional
    public ApiResponse<Object> examine(@PathVariable Long id)throws NotFoundException{
        Organization organization = organzationService.selectById(id);
        notFound(organization,"单位信息不存在");

        organization.setApproveStatus(ApproveEnum.APPROVE_SUCCESS);

        organzationService.updateById(organization);

        return apiSuccess(null);
    }

    /**
     * 单位认证审核未通过
     * @param id
     * @return
     */
    @GetMapping("/refuse/{id}")
    @Transactional
    @Access(permissionName = "单位认证审核",permissionCode = "ORG_CHECK",moduleCode = "basic_information")
    public ApiResponse<Object> refuse(@PathVariable Long id) throws NotFoundException{
        Organization organization = organzationService.selectById(id);
        notFound(organization,"单位不存在");

        organization.setApproveStatus(ApproveEnum.APPROVE_FAIL);

        organzationService.updateById(organization);

        return apiSuccess(null);
    }


    /**
     * 查看单位,区域,停车场关系详情
     * @param id
     * @return
     */
    @GetMapping("/findBindId/{id}")
    public ApiResponse<Object> findBind(@PathVariable Long id) throws NotFoundException{
        Organization organization = organzationService.selectById(id);
        notFound(organization,"单位不存在");

        List<Object> areaIds = orgAreaService.findBindAreaIds(id);
        List<Object> parkingIds = orgParkingService.findBindParkingIds(id);
        List<Parking> parkings = orgParkingService.findBindParkings(parkingIds);

        Map<String,List> data = new HashMap<>();
        data.put("areaIds",areaIds);
        data.put("parkingIds",parkingIds);
        data.put("parkings",parkings);

        return apiSuccess(data);
    }

    /**
     * 单位关联区域，停车场
     * @param orgBindForm
     * @param bindingResult
     * @return
     * @throws BindException
     * @throws NotFoundException
     */
    @PutMapping("/orgBind")
    @Transactional
    public ApiResponse<Object> sync(@Valid @RequestBody OrgBindForm orgBindForm, BindingResult bindingResult)throws BindException,NotFoundException{
        validate(bindingResult);
        Organization organization = organzationService.selectById(orgBindForm.getOrgId());
        notFound(organization,"单位不存在");

        Long orgId = orgBindForm.getOrgId();
        List<Long> areaIds = orgBindForm.getAreaIds();
        List<Long> parkingIds = orgBindForm.getParkingIds();
        if(areaIds == null)
            areaIds = new ArrayList<Long>();
        if(parkingIds == null)
            parkingIds = new ArrayList<Long>();

        String errorMsg = orgAreaService.validateBindData(orgId,areaIds,parkingIds);
          if(StringUtils.isNotBlank(errorMsg))
               return apiFail(errorMsg);

        orgAreaService.syncAreas(orgId,areaIds);
        orgParkingService.syncParkings(parkingIds,orgId);

        return apiSuccess(null);
    }

    private EntityWrapper<Organization> queryCondition(EntityWrapper<Organization> ew,OrganizationQuery query)throws Exception{
        ew.eq(query.getAreaId()!=null,"area_id",query.getAreaId());
        ew.like(StringUtils.isNotBlank(query.getFullName()),"full_name",query.getFullName());
        ew.like(StringUtils.isNotBlank(query.getShortName()),"short_name",query.getShortName());
        ew.eq(query.getOrgNature()!=null,"org_nature",query.getOrgNature());
        ew.eq(query.getPropertyOrg()!=null,"is_property_org",query.getPropertyOrg());
        ew.eq(query.getManageOrg()!=null,"is_manage_org",query.getManageOrg());
        ew.eq(query.getFacilityOrg()!=null,"is_facility_org",query.getFacilityOrg());
        ew.eq(query.getApproveStatus()!=null,"approve_status",query.getApproveStatus());
        ew.between(DateParserUtil.parseDate(query.getStart_time(),true)!=null&&
                            DateParserUtil.parseDate(query.getEnd_time(),false)!=null,
                   "created_at",
                            DateParserUtil.parseDate(query.getStart_time(),true),
                            DateParserUtil.parseDate(query.getEnd_time(),false));
        return ew;
    }
}
