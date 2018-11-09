package com.yxytech.parkingcloud.platform.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.VideoPile;
import com.yxytech.parkingcloud.core.entity.VideoPileBatch;
import com.yxytech.parkingcloud.core.service.IChargeFacilityService;
import com.yxytech.parkingcloud.core.service.IMagneticDeviceService;
import com.yxytech.parkingcloud.core.service.IVideoPileService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.VideoPileForm;
import com.yxytech.parkingcloud.platform.form.VideoPileQuery;
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
@RequestMapping("/videoPile")
public class VideoPileController extends BaseController {

    @Autowired
    private IVideoPileService videoPileService;

    @Autowired
    private IMagneticDeviceService magneticDeviceService;

    @Autowired
    private IChargeFacilityService chargeFacilityService;

    /**
     * 分页查询视频桩信息
     * @param query
     * @param bindingResult
     * @return
     * @throws BindException;
     */
    @GetMapping("/index")
    public ApiResponse<Object> index(@Valid @ModelAttribute("") VideoPileQuery query, BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        Page<VideoPile> p = new Page<>(query.getPage(),query.getSize());
        EntityWrapper<VideoPile> ew = new EntityWrapper<>();
        try{
            ew = queryCondition(ew,query);
        }catch (Exception ex){
            return apiFail("日期格式错误:" + ex.getMessage());
        }

        p = videoPileService.selectByPage(p,ew);

        return apiSuccess(p);
    }

    /**
     * 根据ID查询视频桩详细信息
     * @param id
     * @return
     */
    @GetMapping("/findVideoPileById")
    public ApiResponse<Object> findVideoPileById(@RequestParam(value = "id",required = false)Long id)throws NotFoundException{
        VideoPile videoPile = videoPileService.detail(id);
        notFound(videoPile,"视频桩信息不存在");

        videoPile.setGps(String.format("%.6f", videoPile.getLongitude()) + " " + String.format("%.6f", videoPile.getLatitude()));

        return apiSuccess(videoPile);
    }

    /**
     * 创建视频桩设备信息
     * @return
     */
    @PostMapping("/addVideoPile")
    @Transactional
    public ApiResponse<Object> addVideoPile(@Valid @RequestBody VideoPileForm videoPileForm,BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        VideoPile videoPile = new VideoPile();
        BeanUtils.copyProperties(videoPileForm,videoPile);

        String bindMsg = chargeFacilityService.validate(videoPile.getParkingCellCode(),videoPile.getManageOrgId(),videoPile.getParkingId(),videoPile.getFacOrgId(),videoPile.getSn(),VideoPile.class);
          if(StringUtils.isNotBlank(bindMsg))
               return apiFail(bindMsg);

        String gpsMsg = magneticDeviceService.validateGps(videoPile.getGps());
          if(StringUtils.isNotBlank(gpsMsg))
               return apiFail(gpsMsg);

        videoPile.setGps(magneticDeviceService.formattingGps(videoPile.getGps()));
        videoPile.setPutInAt(new Date());
        videoPileService.create(videoPile);

        magneticDeviceService.bindOrg(videoPile.getFacOrgId());

        return apiSuccess(null);
    }

    /**
     * 修改视频桩设备信息
     * @return
     */
    @PutMapping("/updateVideoPile")
    @Transactional
    public ApiResponse<Object> updateVideoPile(@Valid @RequestBody VideoPileForm videoPileForm,BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        VideoPile videoPile = new VideoPile();
        BeanUtils.copyProperties(videoPileForm,videoPile);

        String errorMsg = videoPileService.updateValidate(videoPile);
          if(errorMsg!=null)
               return apiFail(errorMsg);

        String gpsMsg = magneticDeviceService.validateGps(videoPile.getGps());
          if(StringUtils.isNotBlank(gpsMsg))
               return apiFail(gpsMsg);

        videoPile.setGps(magneticDeviceService.formattingGps(videoPile.getGps()));
        videoPileService.updateVideoPile(videoPile);

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

        List<VideoPile> videoPiles = videoPileService.selectBatchIds(newIds);
            if(newIds.size() > videoPiles.size()){
                videoPiles.forEach((item) -> newIds.remove(item.getId()));
                return apiFail("设备信息id不存在：" + newIds.toString());
            }
        List<VideoPile> toUpdateList = new ArrayList<>();
        for(VideoPile videoPile : videoPiles){
            videoPile.setStatus(true);
            videoPile.setGps(null);
            toUpdateList.add(videoPile);
        }
        videoPileService.updateBatch(toUpdateList);

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

        List<VideoPile> videoPiles = videoPileService.selectBatchIds(newIds);
            if(newIds.size() > videoPiles.size()){
                videoPiles.forEach((item) -> newIds.remove(item.getId()));
                return apiFail("设备信息id不存在：" + newIds.toString());
            }
        List<VideoPile> toUpdateList = new ArrayList<>();
        for(VideoPile videoPile : videoPiles){
            videoPile.setStatus(false);
            videoPile.setGps(null);
            toUpdateList.add(videoPile);
        }
        videoPileService.updateBatch(toUpdateList);

        return apiSuccess(null);
    }

    /**
     * 批量导入
     * @param list
     * @return
     */
    @PostMapping("/batch")
    @Transactional
    public ApiResponse<Object> createBatch(@RequestBody List<VideoPileBatch> list){
        String errorMsg = chargeFacilityService.createBatch(list,VideoPile.class);
        if(StringUtils.isNotBlank(errorMsg))
             return apiFail(errorMsg);

        return apiSuccess(null);
    }

    private EntityWrapper<VideoPile> queryCondition(EntityWrapper<VideoPile> ew,VideoPileQuery query) throws Exception{
        ew.eq(StringUtils.isNotBlank(query.getSystemNumber()),"system_number",query.getSystemNumber());
        ew.eq(StringUtils.isNotBlank(query.getSn()),"sn",query.getSn());
        ew.eq(query.getStatus()!=null,"status",query.getStatus());
        ew.eq(query.getStartusingAt()!=null,"startusing_at",query.getStartusingAt());
        ew.eq(query.getPutInAt()!=null,"put_in_at",query.getPutInAt());
        ew.eq(query.getFacOrgId()!=null,"v.fac_org_id",query.getFacOrgId());
        ew.eq(StringUtils.isNotBlank(query.getManager()),"v.manager",query.getManager());
        ew.eq(query.getManageOrgId()!=null,"v.manage_org_id",query.getManageOrgId());
        ew.eq(query.getParkingId()!=null,"v.parking_id",query.getParkingId());
        ew.eq(StringUtils.isNotBlank(query.getIp()),"ip",query.getIp());
        ew.between(StringUtils.isNotBlank(query.getStart_time()) &&
                            StringUtils.isNotBlank(query.getEnd_time()),"put_in_at",
                            DateParserUtil.parseDate(query.getStart_time(),true),
                            DateParserUtil.parseDate(query.getEnd_time(),false));

        return ew;
    }
}
