package com.yxytech.parkingcloud.platform.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.ParkingPort;
import com.yxytech.parkingcloud.core.service.IMagneticDeviceService;
import com.yxytech.parkingcloud.core.service.IParkingPortService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.ParkingPortForm;
import com.yxytech.parkingcloud.platform.form.ParkingPortQuery;
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
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cj
 * @since 2017-10-18
 */
@RestController
@RequestMapping("/parkingPort")
public class ParkingPortController extends BaseController {

    @Autowired
    private IParkingPortService parkingPortService;

    @Autowired
    private IMagneticDeviceService magneticDeviceService;

    @Autowired
    private IParkingService parkingService;
    /**
     * 查询停车场出入口信息
     * @return
     */
    @GetMapping("/index")
    public ApiResponse<Object> index(@Valid @ModelAttribute("") ParkingPortQuery query, BindingResult bindingResult)throws BindException{
        this.validate(bindingResult);

        Page<ParkingPort> p = new Page<ParkingPort>(query.getPage(),query.getSize());
        EntityWrapper<ParkingPort> ew = new EntityWrapper();
        try{
            ew = queryCondition(ew,query);
        }catch (Exception ex){
            return apiFail("日期格式错误:" + ex.getMessage());
        }

        p = parkingPortService.selectPage(p,ew);

        return apiSuccess(p);
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping("/findPortById")
    public ApiResponse<Object> findById(@RequestParam("id")Long id)throws NotFoundException{
        ParkingPort parkingPort = parkingPortService.detail(id);
        notFound(parkingPort,"出入口不存在");

        parkingPort.setGps(String.format("%.6f", parkingPort.getLongitude()) + " " + String.format("%.6f", parkingPort.getLatitude()));

        return apiSuccess(parkingPort);
    }

    /**
     * 添加出入口信息
     * @return
     */
    @PostMapping("/addPort")
    @Transactional
    public ApiResponse<Object> addPort(@Valid @RequestBody ParkingPortForm parkingPortForm,BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        ParkingPort parkingPort = new ParkingPort();
        BeanUtils.copyProperties(parkingPortForm,parkingPort);

        String existMsg = parkingPortService.validate(parkingPort.getParkingId(),parkingPort.getCode());
          if(StringUtils.isNotBlank(existMsg))
               return apiFail(existMsg);

        String gpsMsg = magneticDeviceService.validateGps(parkingPort.getGps());
          if(StringUtils.isNotBlank(gpsMsg))
               return apiFail(gpsMsg);

        parkingPort.setGps(magneticDeviceService.formattingGps(parkingPort.getGps()));
        parkingPortService.createParkingPort(parkingPort);

        return apiSuccess(null);
    }

    /**
     * 修改出入口信息
     * @param parkingPortForm
     * @param bindingResult
     * @return
     * @throws BindException
     */
    @PutMapping("/updatePort")
    @Transactional
    public ApiResponse<Object> updateParking(@Valid @RequestBody ParkingPortForm parkingPortForm,BindingResult bindingResult)throws BindException {
        validate(bindingResult);

        ParkingPort parkingPort = new ParkingPort();
        BeanUtils.copyProperties(parkingPortForm,parkingPort);
        String errorMsg = parkingPortService.updateValidate(parkingPort);
          if(errorMsg!=null)
               return apiFail(errorMsg);

        String gpsMsg = magneticDeviceService.validateGps(parkingPort.getGps());
          if(StringUtils.isNotBlank(gpsMsg))
               return apiFail(gpsMsg);

        parkingPort.setGps(magneticDeviceService.formattingGps(parkingPort.getGps()));
        parkingPortService.updateParkingPort(parkingPort);

        return apiSuccess(null);
    }

    /**
     * 批量停用出入口
     * @param ids
     * @return
     */
    @PutMapping("/nonUse")
    @Transactional
    public ApiResponse<Object> nonUsePort(@RequestBody String ids){
        if(StringUtils.isBlank(ids))
             return apiFail("非法参数");

        Map<String,Object> map = JSON.parseObject(ids);
        List<Long> newIds = (List<Long>) map.get("ids");

        List<ParkingPort> parkingPorts = parkingPortService.selectBatchIds(newIds);
        if(newIds.size() > parkingPorts.size()){
            parkingPorts.forEach((item) -> newIds.remove(item.getId()));
            return apiFail("设备信息id不存在：" + newIds.toString());
        }
        List<ParkingPort> toUpdatePortList = new ArrayList<>();
        for(ParkingPort parkingPort : parkingPorts){
            parkingPort.setUsing(false);
            parkingPort.setGps(null);
            toUpdatePortList.add(parkingPort);
        }
        parkingPortService.updateBatch(toUpdatePortList);

        return apiSuccess(null);
    }

    /**
     * 批量启用出入口
     * @param ids
     * @return
     */
    @PutMapping("/startUsing")
    @Transactional
    public ApiResponse<Object> startUsingPort(@RequestBody String ids){
        if(StringUtils.isBlank(ids))
             return apiFail("非法参数");

        Map<String,Object> map = JSON.parseObject(ids);
        List<Long> newIds = (List<Long>) map.get("ids");

        List<ParkingPort> parkingPorts = parkingPortService.selectBatchIds(newIds);
        if(newIds.size() > parkingPorts.size()){
            parkingPorts.forEach((item) -> newIds.remove(item.getId()));
            return apiFail("设备信息id不存在：" + newIds.toString());
        }
        List<ParkingPort> toUpdatePortList = new ArrayList<>();
        for(ParkingPort parkingPort : parkingPorts){
            parkingPort.setUsing(true);
            parkingPort.setGps(null);
            toUpdatePortList.add(parkingPort);
        }
        parkingPortService.updateBatch(toUpdatePortList);

        return apiSuccess(null);
    }

    /**
     * 根据停车场ID查询停车场的所有出入口
     * @param id
     * @return
     */
    @GetMapping("/findPortByParking/{id}")
    public ApiResponse<Object> findPortByParking(@PathVariable Long id)throws NotFoundException{
        Parking parking = parkingService.selectById(id);
        notFound(parking,"停车场不存在");

        Wrapper<ParkingPort> wrapper = new EntityWrapper<ParkingPort>();
        wrapper.eq("parking_id",id);
        List<ParkingPort> list = parkingPortService.selectList(wrapper);

        return apiSuccess(list);
    }


    private EntityWrapper<ParkingPort> queryCondition(EntityWrapper<ParkingPort> ew,ParkingPortQuery query)throws Exception{
        ew.eq(StringUtils.isNotBlank(query.getCode()),"yxy_parking_port.code",query.getCode());
        ew.eq(query.getParkingId()!=null,"parking_id",query.getParkingId());
        ew.eq(query.getPortType()!=null,"port_type",query.getPortType());
        ew.eq(query.getUsing()!=null,"yxy_parking_port.is_using",query.getUsing());
        ew.eq(StringUtils.isNotBlank(query.getGps()),"yxy_parking_port.gps",query.getGps());
        ew.between(DateParserUtil.parseDate(query.getStart_time(),true)!=null&&
                            DateParserUtil.parseDate(query.getEnd_time(),false)!=null, "created_at",
                            DateParserUtil.parseDate(query.getStart_time(),true),
                            DateParserUtil.parseDate(query.getEnd_time(),false));


        return ew;
    }
}
