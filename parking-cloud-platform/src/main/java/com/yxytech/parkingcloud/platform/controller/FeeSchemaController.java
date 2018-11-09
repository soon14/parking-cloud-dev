package com.yxytech.parkingcloud.platform.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.service.IFeeSchemaParkingService;
import com.yxytech.parkingcloud.core.service.IFeeSchemaService;
import com.yxytech.parkingcloud.core.service.IUserAccountService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.FeeSchemaForm;
import com.yxytech.parkingcloud.platform.form.SchemaParkingForm;
import com.yxytech.parkingcloud.platform.form.SchemaRulesForm;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cj
 * @since 2017-10-24
 */
@RestController
@RequestMapping("/schema")
public class FeeSchemaController extends BaseController {

    @Autowired
    private IFeeSchemaService schemaService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IFeeSchemaParkingService feeSchemaParkingService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     * @return
     * @throws BindException;
     */
    @GetMapping("/list")
    public ApiResponse<Object> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = pageSize) Integer size,
            @RequestParam(value = "schema", required = false) String schema,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "start_time", required = false) String startTime,
            @RequestParam(value = "end_time", required = false) String endTime
            ) throws BindException {

        Page<FeeSchema> pagination = new Page<>(page, size);
        EntityWrapper<FeeSchema> ew = new EntityWrapper<>();
        ew.like(name!=null,"name", name)
        .orderBy("id", true);
        try{
            ew.between(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime),
                       "created_at", DateParserUtil.parseDate(startTime,true),
                        DateParserUtil.parseDate(endTime,false));
        }catch (Exception e){
            return apiFail("日期格式错误:" + e.getMessage());
        }

        return this.apiSuccess(schemaService.selectPage(pagination, ew));
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("/show")
    public ApiResponse<Object> findById(@RequestParam(value = "id", required = true) Integer id) {

        return this.apiSuccess(schemaService.selectById(id));
    }


    /**
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    @Access(permissionName = "费率计划创建",permissionCode = "FEE_SCHEMA_CREATE",moduleCode = "system_manage")
    public ApiResponse<Object> create(@RequestBody FeeSchemaForm entity
    ) throws BindException, ParseException, NotFoundException {
        User user = getCurrentUser();
        UserAccount userAccount = userAccountService.selectByUserId(user.getId());
        notFound(userAccount, "用户信息不存在");

        Long orgId = userAccount.getOrgId();

        FeeSchema schema = new FeeSchema();
        entity.setCreatedAt(new Date());
        entity.setVersion(schemaService.getVersion());
        BeanUtils.copyProperties(entity, schema);
        schema.setOrganizationId(orgId);

        return this.apiSuccess(schemaService.create(schema));
    }

    /**
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    @Access(permissionName = "费率计划修改",permissionCode = "FEE_SCHEMA_UPDATE",moduleCode = "system_manage")
    public ApiResponse<Object> update(@RequestBody FeeSchemaForm entity) throws BindException {

        FeeSchema schema = new FeeSchema();
        entity.setUpdatedAt(new Date());
        entity.setVersion(schemaService.getVersion());

        BeanUtils.copyProperties(entity, schema);

        return this.apiSuccess(schemaService.update(schema));
    }


    /**
     * @param
     * @return
     */
    @PostMapping("/bindRule")
    @ResponseBody
    @Access(permissionName = "费率计划绑定费率规则",permissionCode = "FEE_SCHEMA_RULE",moduleCode = "system_manage")
    public ApiResponse<Object> bindRule(

            @RequestBody SchemaRulesForm form
    ) {

        try {
            return this.apiSuccess(schemaService.bindRule(form.getSchemaId(), form.getRules()));
        } catch (Exception e) {
            return this.apiFail(e.getMessage());
        }
    }

    /**
     * @param
     * @return
     */
    @PostMapping("/bindParking")
    @ResponseBody
    @Access(permissionName = "费率计划绑定停车场",permissionCode = "FEE_SCHEMA_PARKING",moduleCode = "system_manage")
    public ApiResponse<Object> bindParking(

            @RequestBody SchemaParkingForm form
    ) {

        try {
            return this.apiSuccess(schemaService.bindParking(form.getSchemaId(), form.getParking()));
        } catch (Exception e) {
            return this.apiFail(e.getMessage());
        }
    }


    /**
     * @param
     * @return
     */
    @GetMapping("/bindParkingList")
    @ResponseBody
    public ApiResponse<Object> bindParkingList(@RequestParam(value = "schemaId", required = false) Long schemaId

    ) throws BindException {
        EntityWrapper<FeeSchemaParking> ew = new EntityWrapper<>();

        ew.setSqlSelect("parking_id").eq("fee_schema_id", schemaId);


        return this.apiSuccess( feeSchemaParkingService.selectObjs(ew));
    }

}
