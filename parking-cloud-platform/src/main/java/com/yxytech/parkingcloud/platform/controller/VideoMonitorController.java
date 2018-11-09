package com.yxytech.parkingcloud.platform.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.VideoMonitor;
import com.yxytech.parkingcloud.core.entity.VideoMonitorBatch;
import com.yxytech.parkingcloud.core.service.IChargeFacilityService;
import com.yxytech.parkingcloud.core.service.IMagneticDeviceService;
import com.yxytech.parkingcloud.core.service.IVideoMonitorService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.VideoMonitorForm;
import com.yxytech.parkingcloud.platform.form.VideoMonitorQuery;
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
@RequestMapping("/videoMonitor")
public class VideoMonitorController extends BaseController {

    @Autowired
    private IVideoMonitorService videoMonitorService;

    @Autowired
    private IMagneticDeviceService magneticDeviceService;

    @Autowired
    private IChargeFacilityService chargeFacilityService;

    /**
     * 查询视频健康设备信息
     * @return
     */
    @GetMapping("/index")
    public ApiResponse<Object> index(@Valid @ModelAttribute("") VideoMonitorQuery query, BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        Page<VideoMonitor> p = new Page<>(query.getPage(),query.getSize());
        EntityWrapper<VideoMonitor> ew = new EntityWrapper<>();
        try{
            ew = queryCondition(ew,query);
        }catch (Exception ex){
            return apiFail("日期格式错误:" + ex.getMessage());
        }

        p = videoMonitorService.selectByPage(p,ew);

        return apiSuccess(p);
    }

    /**
     * 根据ID查询视频监控设备详细信息
     * @param id
     * @return
     */
    @GetMapping("/findVideoMonitorById")
    public ApiResponse<Object> findVideoMonitorById(@RequestParam(value = "id",required = false)Long id)throws NotFoundException{
        VideoMonitor videoMonitor = videoMonitorService.detail(id);
        notFound(videoMonitor,"视频监控设备不存在");

        videoMonitor.setGps(String.format("%.6f", videoMonitor.getLongitude()) + " " + String.format("%.6f", videoMonitor.getLatitude()));

        return apiSuccess(videoMonitor);
    }

    /**
     * 创建视频监控设备信息
     * @return
     */
    @PostMapping("/addVideoMonitor")
    @Transactional
    public ApiResponse<Object> addVideoMonitor(@Valid @RequestBody VideoMonitorForm videoMonitorForm,BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        VideoMonitor videoMonitor = new VideoMonitor();
        BeanUtils.copyProperties(videoMonitorForm,videoMonitor);

        String errorMsg = chargeFacilityService.validate(videoMonitor.getParkingCellCode(),videoMonitor.getManageOrgId(),videoMonitor.getParkingId(),
                                                videoMonitor.getFacOrgId(),videoMonitor.getSn(),VideoMonitor.class);
          if(StringUtils.isNotBlank(errorMsg))
               return apiFail(errorMsg);

        String gpsMsg = magneticDeviceService.validateGps(videoMonitor.getGps());
          if(StringUtils.isNotBlank(gpsMsg))
               return apiFail(gpsMsg);

        videoMonitor.setGps(magneticDeviceService.formattingGps(videoMonitor.getGps()));
        videoMonitor.setPutInAt(new Date());
        videoMonitorService.create(videoMonitor);

        magneticDeviceService.bindOrg(videoMonitor.getFacOrgId());

        return apiSuccess(null);
    }

    /**
     * 修改视频监控设备信息
     * @return
     */
    @PutMapping("/updateVideoMonitor")
    @Transactional
    public ApiResponse<Object> updateVideoMonitor(@Valid @RequestBody VideoMonitorForm videoMonitorForm,BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        VideoMonitor videoMonitor = new VideoMonitor();
        BeanUtils.copyProperties(videoMonitorForm,videoMonitor);

        String errorMsg = videoMonitorService.updateValidate(videoMonitor);
          if(errorMsg!=null)
               return apiFail(errorMsg);

        String gpsMsg = magneticDeviceService.validateGps(videoMonitor.getGps());
          if(StringUtils.isNotBlank(gpsMsg))
               return apiFail(gpsMsg);

        videoMonitor.setGps(magneticDeviceService.formattingGps(videoMonitor.getGps()));
        videoMonitorService.updateVideoMonitor(videoMonitor);

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

        List<VideoMonitor> videoMonitors = videoMonitorService.selectBatchIds(newIds);
            if(newIds.size() > videoMonitors.size()){
                videoMonitors.forEach((item) -> newIds.remove(item.getId()));
                return apiFail("设备信息id不存在：" + newIds.toString());
            }
        List<VideoMonitor> toUpdateList = new ArrayList<>();
        for(VideoMonitor videoMonitor : videoMonitors){
            videoMonitor.setStatus(true);
            videoMonitor.setGps(null);
            toUpdateList.add(videoMonitor);
        }
        videoMonitorService.updateBatch(toUpdateList);

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

        List<VideoMonitor> videoMonitors = videoMonitorService.selectBatchIds(newIds);
            if(newIds.size() > videoMonitors.size()){
                videoMonitors.forEach((item) -> newIds.remove(item.getId()));
                return apiFail("设备信息id不存在：" + newIds.toString());
            }
        List<VideoMonitor> toUpdateList = new ArrayList<>();
        for(VideoMonitor videoMonitor : videoMonitors){
            videoMonitor.setStatus(false);
            videoMonitor.setGps(null);
            toUpdateList.add(videoMonitor);
        }
        videoMonitorService.updateBatch(toUpdateList);

        return apiSuccess(null);
    }

    /**
     * 批量导入
     * @param list
     * @return
     */
    @PostMapping("/batch")
    @Transactional
    public ApiResponse<Object> createBatch(@RequestBody List<VideoMonitorBatch> list){
        String errorMsg = chargeFacilityService.createBatch(list,VideoMonitor.class);
          if(StringUtils.isNotBlank(errorMsg))
               return apiFail(errorMsg);

        return apiSuccess(null);
    }

    private EntityWrapper<VideoMonitor> queryCondition(EntityWrapper<VideoMonitor> ew,VideoMonitorQuery query)throws Exception{
        ew.eq(StringUtils.isNotBlank(query.getSystemNumber()),"system_number",query.getSystemNumber());
        ew.eq(StringUtils.isNotBlank(query.getSn()),"sn",query.getSn());
        ew.eq(query.getStatus()!=null,"status",query.getStatus());
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
