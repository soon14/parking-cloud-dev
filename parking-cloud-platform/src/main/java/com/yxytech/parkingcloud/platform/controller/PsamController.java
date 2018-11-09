package com.yxytech.parkingcloud.platform.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.ChargeFacility;
import com.yxytech.parkingcloud.core.entity.Psam;
import com.yxytech.parkingcloud.core.entity.PsamBatch;
import com.yxytech.parkingcloud.core.service.IChargeFacilityService;
import com.yxytech.parkingcloud.core.service.IPsamService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.PsamForm;
import com.yxytech.parkingcloud.platform.form.PsamQuery;
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

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cj
 * @since 2017-10-24
 */
@RestController
@RequestMapping("/psam")
public class PsamController extends BaseController {

    @Autowired
    private IPsamService psamService;

    @Autowired
    private IChargeFacilityService chargeFacilityService;

    /**
     * 查询PSAM设备信息
     * @return
     */
    @GetMapping("/index")
    public ApiResponse<Object> index(@Valid @ModelAttribute("") PsamQuery query, BindingResult bindingResult )throws BindException{
        this.validate(bindingResult);

        Page<Psam> p = new Page<>(query.getPage(),query.getSize());
        EntityWrapper<Psam> ew = new EntityWrapper<>();
        try{
            ew = queryCondition(ew,query);
        }catch (Exception ex){
            return apiFail("日期格式错误:" + ex.getMessage());
        }

        p = psamService.selectByPage(p,ew);

        return apiSuccess(p);
    }

    /**
     * 根据ID查询PSAM设备详细信息
     * @param id
     * @return
     */
    @GetMapping("/findPsamById")
    public ApiResponse<Object> findPsamById(@RequestParam(value = "id",required = false)Long id)throws NotFoundException{
        Psam psam = psamService.detail(id);
        notFound(psam,"PSAM设备不存在");

        EntityWrapper<ChargeFacility> ew = new EntityWrapper<>();
        ew.setSqlSelect("system_number").eq("id",psam.getTerminalDeviceId());
        Object codeObj = chargeFacilityService.selectObj(ew);
        if(codeObj == null)
            psam.setTerminalDeviceCode(null);
        else
            psam.setTerminalDeviceCode(codeObj.toString());

        return apiSuccess(psam);
    }

    /**
     * 创建PSAM设备信息
     * @return
     */
    @PostMapping("/addPsam")
    public ApiResponse<Object> addPsam(@Valid @RequestBody PsamForm psamForm,BindingResult bindingResult)throws BindException {
        validate(bindingResult);

        Psam psam = new Psam();
        BeanUtils.copyProperties(psamForm,psam);

        String errorMsg = psamService.validate(psam.getPsamCardNumber(),psam.getManageOrgId(),psam.getParkingId());
          if(errorMsg!=null)
               return apiFail(errorMsg);

        psam.setPutInAt(new Date());
        psamService.insert(psam);

        return apiSuccess(null);
    }

    /**
     * 修改PSAM设备信息
     * @return
     */
    @PutMapping("/updatePsam")
    @Transactional
    public ApiResponse<Object> updatePsam(@Valid @RequestBody PsamForm psamForm,BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        Psam psam = new Psam();
        BeanUtils.copyProperties(psamForm,psam);

        String errorMsg = psamService.updateValidate(psam);
        if(errorMsg!=null)
            return apiFail(errorMsg);

        psamService.updateById(psam);

        return apiSuccess(null);
    }

    /**
     * 批量导入
     * @param list
     * @return
     */
    @PostMapping("/batch")
    @Transactional
    public ApiResponse<Object> createBatch(@RequestBody List<PsamBatch> list){
        String errorMsg = psamService.createBatch(list);
          if(StringUtils.isNotBlank(errorMsg))
               return apiFail(errorMsg);

        return apiSuccess(null);
    }


    private EntityWrapper<Psam> queryCondition(EntityWrapper<Psam> ew,PsamQuery query)throws Exception{
        ew.eq(query.getPutInAt()!=null,"put_in_at", query.getPutInAt());
        ew.eq(query.getPutOutAt()!=null,"put_out_at", query.getPutOutAt());
        ew.eq(query.getStartusingAt()!=null,"startusing_at", query.getStartusingAt());
        ew.eq(query.getFacOrgId()!=null,"fac_org_id", query.getFacOrgId());
        ew.eq(query.getManageOrgId()!=null,"manage_org_id", query.getManageOrgId());
        ew.eq(StringUtils.isNotBlank(query.getManager()),"manager", query.getManager());
        ew.eq(query.getTerminalDeviceId()!=null,"terminal_device_id",query.getTerminalDeviceId());
        ew.eq(StringUtils.isNotBlank(query.getPsamCardNumber()),"psam_card_number",query.getPsamCardNumber());
        ew.eq(query.getPsamType()!=null,"psam_type",query.getPsamType());
        ew.between(StringUtils.isNotBlank(query.getStart_time()) &&
                            StringUtils.isNotBlank(query.getEnd_time()),"put_in_at",
                            DateParserUtil.parseDate(query.getStart_time(),true),
                            DateParserUtil.parseDate(query.getEnd_time(),false));

        return ew;
    }
}

