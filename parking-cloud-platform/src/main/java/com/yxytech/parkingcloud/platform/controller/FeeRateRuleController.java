package com.yxytech.parkingcloud.platform.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.FeeRateRule;
import com.yxytech.parkingcloud.core.entity.FeeSchema;
import com.yxytech.parkingcloud.core.entity.User;
import com.yxytech.parkingcloud.core.entity.UserAccount;
import com.yxytech.parkingcloud.core.enums.CarTypeEnum;
import com.yxytech.parkingcloud.core.enums.CycleFeeTypeEnum;
import com.yxytech.parkingcloud.core.enums.CycleTypeEnum;
import com.yxytech.parkingcloud.core.enums.FeeRateEnum;
import com.yxytech.parkingcloud.core.service.IFeeRateRuleService;
import com.yxytech.parkingcloud.core.service.IUserAccountService;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.FeeRuleRateForm;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cj
 * @since 2017-10-24
 */
@RestController
@RequestMapping("/feeRateRule")
public class FeeRateRuleController extends BaseController {

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IFeeRateRuleService ruleService;

    String[] week = new String[]{"onMon", "onTue", "onWed", "onThu", "onFri", "onSat", "onSun"};
    String[] method = new String[]{"setOnMon", "setOnTue", "setOnWed", "setOnThu", "setOnFri", "setOnSat", "setOnSun"};

    /**
     * @return
     * @throws BindException;
     */
    @GetMapping("/list")
    public ApiResponse<Object> list(
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = pageSize, required = false) Integer size,
            @RequestParam(value = "desc", defaultValue = "", required = false) String desc

    ) throws BindException {

        Page<Object[]> pagination = new Page(page, size);

        EntityWrapper<FeeRateRule> ew = new EntityWrapper<>();
        ew.like(desc!=null,"yxy_fee_rate_rule.desc", desc);

        return this.apiSuccess(ruleService.getFeeRuleRatePage(pagination, ew));
    }


    @PostMapping("/bindList")
    @ResponseBody
    public ApiResponse<Object> bindlist(@RequestBody FeeSchema schema
                                        ) throws BindException {

        return this.apiSuccess(ruleService.bindList(schema));
    }

    /**
     * @param
     * @return
     */
    @GetMapping("/show")
    @Access(permissionName = "费率规则详情",permissionCode = "FEE_RULE_DETAIL",moduleCode = "system_manage")
    public ApiResponse<Object> findById(@RequestParam(value = "id",required = true) Long id) {


        List<Object> feeRule = ruleService.getDetail(id);
        return this.apiSuccess(feeRule);
    }

    /**
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    @Access(permissionName = "费率规则修改",permissionCode = "FEE_RETE_RULE_UPDATE",moduleCode = "system_manage")
    public ApiResponse<Object> update(
            @RequestBody FeeRuleRateForm form
    ) throws BindException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return this.apiSuccess(ruleService.update(form.getRule(), form.getSteps()));
    }

    /**
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    @Access(permissionName = "费率规则新建",permissionCode = "FEE_RETE_RULE_CREATE",moduleCode = "system_manage")
    public ApiResponse<Object> create(
           @Valid @RequestBody FeeRuleRateForm form
    ) throws BindException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NotFoundException {
        User user = getCurrentUser();
        UserAccount userAccount = userAccountService.selectByUserId(user.getId());
        notFound(userAccount, "用户信息不存在");

        Long orgId = userAccount.getOrgId();

        FeeRateRule rule = form.getRule();
        if(form.getDesc() != null){
          rule.setDesc(form.getDesc());
        }

        rule.setOrganizationId(orgId);
        return this.apiSuccess(ruleService.create(rule, form.getSteps()));
    }

    /**
     * @return
     */
    @PostMapping("/enum")
    @ResponseBody
    public ApiResponse<Object> getFeeEnum(
    ) throws BindException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        for (FeeRateEnum f : FeeRateEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", f.getValue());
            map.put("name", f.getDesc());
            array.add(map);
        }
        json.put("feeRate",array);

        JSONArray type = new JSONArray();
        for (CycleTypeEnum c : CycleTypeEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", c.getValue());
            map.put("name", c.getDesc());
            type.add(map);
        }
        json.put("cycleType",type);

        JSONArray feeType = new JSONArray();
        for (CycleFeeTypeEnum f : CycleFeeTypeEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", f.getValue());
            map.put("name", f.getDesc());
            feeType.add(map);
        }

        json.put("cycleFeeType",feeType);

        JSONArray vehicle = new JSONArray();

        for (CarTypeEnum f : CarTypeEnum.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", f.getValue());
            map.put("name", f.getDesc());
            vehicle.add( map);
        }

        json.put("vehicle",vehicle);


        return this.apiSuccess(json);
    }


}
