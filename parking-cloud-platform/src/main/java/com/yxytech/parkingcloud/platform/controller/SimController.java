package com.yxytech.parkingcloud.platform.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.ChargeFacility;
import com.yxytech.parkingcloud.core.entity.Sim;
import com.yxytech.parkingcloud.core.entity.SimBatch;
import com.yxytech.parkingcloud.core.service.IChargeFacilityService;
import com.yxytech.parkingcloud.core.service.IMagneticDeviceService;
import com.yxytech.parkingcloud.core.service.ISimService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.SimForm;
import com.yxytech.parkingcloud.platform.form.SimQuery;
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
@RequestMapping("/sim")
public class SimController extends BaseController {

    @Autowired
    private ISimService simService;

    @Autowired
    private IMagneticDeviceService magneticDeviceService;

    @Autowired
    private IChargeFacilityService chargeFacilityService;


    /**
     * 查询SIM卡信息
     * @return
     */
    @GetMapping("/index")
    public ApiResponse<Object> index(@Valid @ModelAttribute("") SimQuery query, BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        Page<Sim> p = new Page<>(query.getPage(),query.getSize());
        EntityWrapper<Sim> ew = new EntityWrapper<>();
        try{
            ew = queryCondition(ew,query);
        }catch (Exception ex){
            return apiFail("日期格式错误:" + ex.getMessage());
        }
        p = simService.selectByPage(p,ew);

        return this.apiSuccess(p);
    }

    /**
     * 根据ID查询SIM卡 详细信息
     * @param id
     * @return
     */
    @GetMapping("/findSimById")
    public ApiResponse<Object> findSimById(@RequestParam(value = "id",required = false)Long id)throws NotFoundException{
        Sim sim = simService.detail(id);
        notFound(sim,"SIM卡信息不存在");

        EntityWrapper<ChargeFacility> ew = new EntityWrapper<>();
        ew.setSqlSelect("system_number").eq("id",sim.getTeminalDeviceId());
        Object codeObj = chargeFacilityService.selectObj(ew);
        if(codeObj == null)
            sim.setTeminalDeviceCode(null);
        else
            sim.setTeminalDeviceCode(codeObj.toString());

        return apiSuccess(sim);
    }

    /**
     * 创建SIM卡信息
     * @return
     */
    @PostMapping("/addSim")
    @Transactional
    public ApiResponse<Object> addSim(@Valid @RequestBody SimForm simForm,BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        Sim sim = new Sim();
        BeanUtils.copyProperties(simForm,sim);

        String errorMsg = simService.validate(sim.getManageOrgId(),sim.getFacOrgId(),sim.getSimNumber(),sim.getParkingId());
          if(StringUtils.isNotBlank(errorMsg))
              return apiFail(errorMsg);

        sim.setPutInAt(new Date());
        simService.insert(sim);

        String bindMsg = magneticDeviceService.bindOrg(sim.getFacOrgId());
          if(bindMsg != null)
               return apiFail(bindMsg);

        return apiSuccess(null);
    }

    /**
     * 修改SIM卡信息
     * @return
     */
    @PutMapping("/updateSim")
    @Transactional
    public ApiResponse<Object> updateSim(@Valid @RequestBody SimForm simForm,BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        Sim sim = new Sim();
        BeanUtils.copyProperties(simForm,sim);

        String errorMsg = simService.updateValidate(sim);
          if(errorMsg!=null)
               return apiFail(errorMsg);

        simService.updateById(sim);

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
    public ApiResponse<Object> startUsing(@RequestBody String ids) {
        if(StringUtils.isBlank(ids))
             return apiFail("非法参数");

        Map<String, Object> map = JSON.parseObject(ids);
        List<Long> newIds = (List<Long>) map.get("ids");

        List<Sim> sims = simService.selectBatchIds(newIds);
            if(newIds.size() > sims.size()){
                sims.forEach((item) -> newIds.remove(item.getId()));
                return apiFail("设备信息id不存在：" + newIds.toString());
            }
        sims.forEach((item) -> item.setStatus(true));
        simService.updateBatchById(sims);

        return apiSuccess(null);
    }

    /**
     * 批量停用设备
     * @param ids
     * @return
     */
    @PutMapping("/nonUse")
    @Transactional
    public ApiResponse<Object> nonUse(@RequestBody String ids){
        if(StringUtils.isBlank(ids))
             return apiFail("非法参数");

        Map<String, Object> map = JSON.parseObject(ids);
        List<Long> newIds = (List<Long>) map.get("ids");

        List<Sim> sims = simService.selectBatchIds(newIds);
            if(newIds.size() > sims.size()){
                sims.forEach((item) -> newIds.remove(item.getId()));
                return apiFail("设备信息id不存在：" + newIds.toString());
            }
        sims.forEach((item) -> item.setStatus(false));
        simService.updateBatchById(sims);

        return apiSuccess(null);
    }

    /**
     * 批量导入
     * @param list
     * @return
     */
    @PostMapping("/batch")
    @Transactional
    public ApiResponse<Object> createBatch(@RequestBody List<SimBatch> list){
        String errorMsg = simService.createBatch(list);
          if(StringUtils.isNotBlank(errorMsg))
               return apiFail(errorMsg);

        return apiSuccess(null);
    }


    private EntityWrapper<Sim> queryCondition(EntityWrapper<Sim> ew,SimQuery query)throws Exception{
        ew.eq(query.getPutInAt()!=null,"put_in_at",query.getPutInAt());
        ew.eq(query.getPutOutAt()!=null,"put_out_at",query.getPutOutAt());
        ew.eq(query.getStartusingAt()!=null,"startusing_at",query.getStartusingAt());
        ew.eq(query.getFacOrgId()!=null,"fac_org_id",query.getFacOrgId());
        ew.eq(query.getManageOrgId()!=null,"manage_org_id",query.getManageOrgId());
        ew.eq(StringUtils.isNotBlank(query.getManager()),"manager",query.getManager());
        ew.eq(query.getTerminalDeviceId()!=null,"terminal_device_id",query.getTerminalDeviceId());
        ew.eq(StringUtils.isNotBlank(query.getSimNumber()),"sim_number",query.getSimNumber());
        ew.eq(StringUtils.isNotBlank(query.getImsi()),"imsi",query.getImsi());
        ew.eq(StringUtils.isNotBlank(query.getOperator()),"operator",query.getOperator());
        ew.eq(query.getStatus()!=null,"status",query.getStatus());
        ew.between(StringUtils.isNotBlank(query.getStart_time()) &&
                            StringUtils.isNotBlank(query.getEnd_time()),"put_in_at",
                            DateParserUtil.parseDate(query.getStart_time(),true),
                            DateParserUtil.parseDate(query.getEnd_time(),false));

        return ew;
    }
}
