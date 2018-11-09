package com.yxytech.parkingcloud.platform.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.MagneticDevice;
import com.yxytech.parkingcloud.core.entity.MagneticDeviceBatch;
import com.yxytech.parkingcloud.core.service.IChargeFacilityService;
import com.yxytech.parkingcloud.core.service.IMagneticDeviceService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.MagneticDeviceForm;
import com.yxytech.parkingcloud.platform.form.MagneticDeviceQuery;
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
@RequestMapping("/magneticDevice")
public class MagneticDeviceController extends BaseController {

    @Autowired
    private IMagneticDeviceService magneticDeviceService;

    @Autowired
    private IChargeFacilityService chargeFacilityService;

    /**
     * 分页查询地磁设备信息
     * @param query
     * @param bindingResult
     * @return
     * @throws BindException
     */
    @GetMapping("/index")
    public ApiResponse<Object> index(@Valid @ModelAttribute("") MagneticDeviceQuery query, BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        Page<MagneticDevice> p = new Page<>(query.getPage(),query.getSize());
        EntityWrapper<MagneticDevice> ew = new EntityWrapper<>();
        try{
            ew = queryCondition(ew,query);
        }catch (Exception ex){
            return apiFail("日期格式错误:" + ex.getMessage());
        }

        p = magneticDeviceService.selectByPage(p,ew);

        return apiSuccess(p);
    }

    /**
     * 根据ID查询地磁设备详细信息
     * @param id
     * @return
     */
    @GetMapping("/findMagneticDeviceById")
    public ApiResponse<Object> findMagneticDeviceById(@RequestParam(value = "id",required = false)Long id)throws NotFoundException{
        MagneticDevice magneticDevice = magneticDeviceService.detail(id);
        notFound(magneticDevice,"地磁设备信息不存在");

        magneticDevice.setGps(String.format("%.6f", magneticDevice.getLongitude()) + " " + String.format("%.6f", magneticDevice.getLatitude()));

        return apiSuccess(magneticDevice);
    }

    /**
     * 添加地磁设备信息
     * @return
     */
    @PostMapping("/addMagneticDevice")
    @Transactional
    public ApiResponse<Object> addMagneticDevice(@Valid @RequestBody MagneticDeviceForm magneticDeviceForm,BindingResult bindingResult)
                                                 throws BindException,NotFoundException{
        validate(bindingResult);

        MagneticDevice magneticDevice = new MagneticDevice();
        BeanUtils.copyProperties(magneticDeviceForm,magneticDevice);

        String errorMsg  = chargeFacilityService.validate(magneticDevice.getParkingCellCode(),magneticDevice.getManageOrgId(),magneticDevice.getParkingId(),
                                                           magneticDevice.getFacOrgId(),magneticDevice.getSn(),MagneticDevice.class);
           if(StringUtils.isNotBlank(errorMsg))
                return apiFail(errorMsg);

        String gpsMsg = magneticDeviceService.validateGps(magneticDevice.getGps());
           if(StringUtils.isNotBlank(gpsMsg))
                return apiFail(gpsMsg);

        magneticDevice.setGps(magneticDeviceService.formattingGps(magneticDevice.getGps()));
        magneticDevice.setPutInAt(new Date());

        magneticDeviceService.create(magneticDevice);

        String bindMsg = magneticDeviceService.bindOrg(magneticDevice.getFacOrgId());
          if(bindMsg != null)
               return apiFail(bindMsg);

        return apiSuccess(null);
    }

    /**
     * 修改地磁设备信息
     * @return
     */
    @PutMapping("/updateMagneticDevice")
    @Transactional
    public ApiResponse<Object> updateMagneticDevice(@Valid @RequestBody MagneticDeviceForm magneticDeviceForm,BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        MagneticDevice magneticDevice = new MagneticDevice();
        BeanUtils.copyProperties(magneticDeviceForm,magneticDevice);

        String errorMsg = magneticDeviceService.updateValidate(magneticDevice);
          if(errorMsg!=null)
              return apiFail(errorMsg);

        String gpsMsg = magneticDeviceService.validateGps(magneticDevice.getGps());
          if(StringUtils.isNotBlank(gpsMsg))
               return apiFail(gpsMsg);

        magneticDevice.setGps(magneticDeviceService.formattingGps(magneticDevice.getGps()));
        magneticDeviceService.updateMagneticDevice(magneticDevice);

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

        List<MagneticDevice> magneticDevices = magneticDeviceService.selectBatchIds(newIds);
            if(newIds.size() > magneticDevices.size()){
                magneticDevices.forEach((item) -> newIds.remove(item.getId()));
                return apiFail("设备信息id不存在：" + newIds.toString());
            }
        List<MagneticDevice> toUpdateMdList = new ArrayList<>();
        for(MagneticDevice magneticDevice : magneticDevices){
            magneticDevice.setStatus(true);
            magneticDevice.setGps(null);
            toUpdateMdList.add(magneticDevice);
        }
        magneticDeviceService.updateBatch(toUpdateMdList);

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

        List<MagneticDevice> magneticDevices = magneticDeviceService.selectBatchIds(newIds);
            if(newIds.size() > magneticDevices.size()){
            magneticDevices.forEach((item) -> newIds.remove(item.getId()));
            return apiFail("设备信息id不存在：" + newIds.toString());
            }
        List<MagneticDevice> toUpdateMdList = new ArrayList<>();
        for(MagneticDevice magneticDevice : magneticDevices){
            magneticDevice.setStatus(false);
            magneticDevice.setGps(null);
            toUpdateMdList.add(magneticDevice);
        }
        magneticDeviceService.updateBatch(toUpdateMdList);

        return apiSuccess(null);
    }

    /**
     * 批量导入
     * @param list
     * @return
     */
    @PostMapping("/batch")
    @Transactional
    public ApiResponse<Object> createBatch(@RequestBody List<MagneticDeviceBatch> list){
        String errMsg = chargeFacilityService.createBatch(list,MagneticDevice.class);
          if(StringUtils.isNotBlank(errMsg))
               return apiFail(errMsg);

        return apiSuccess(null);
    }


    private EntityWrapper<MagneticDevice> queryCondition(EntityWrapper<MagneticDevice> ew,MagneticDeviceQuery query)throws Exception{
        ew.eq(StringUtils.isNotBlank(query.getSystemNumber()),"system_number", query.getSystemNumber());
        ew.eq(StringUtils.isNotBlank(query.getSn()),"sn", query.getSn());
        ew.eq(query.getStatus()!=null,"status", query.getStatus());
        ew.eq(query.getPutInAt()!=null,"put_in_at", query.getPutInAt());
        ew.eq(query.getPutOutAt()!=null,"put_out_at", query.getPutOutAt());
        ew.eq(query.getStartusingAt()!=null,"startusing_at", query.getStartusingAt());
        ew.eq(query.getFacOrgId()!=null,"fac_org_id", query.getFacOrgId());
        ew.eq(StringUtils.isNotBlank(query.getIp()),"ip", query.getIp());
        ew.eq(query.getManageOrgId()!=null,"manage_org_id", query.getManageOrgId());
        ew.eq(StringUtils.isNotBlank(query.getManager()),"manager", query.getManager());
        ew.eq(query.getParkingId()!=null,"parking_id", query.getParkingId());
        ew.eq(StringUtils.isNotBlank(query.getGps()),"gps",query.getGps());
        ew.eq(StringUtils.isNotBlank(query.getParkingCellCode()),"parking_cell_code",query.getParkingCellCode());
        ew.between(StringUtils.isNotBlank(query.getStart_time()) &&
                            StringUtils.isNotBlank(query.getEnd_time()),
                   "put_in_at",
                            DateParserUtil.parseDate(query.getStart_time(),true),
                            DateParserUtil.parseDate(query.getEnd_time(),false));
        return ew;
    }

}
