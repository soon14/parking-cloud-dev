package com.yxytech.parkingcloud.platform.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.ParkingCell;
import com.yxytech.parkingcloud.core.service.IMagneticDeviceService;
import com.yxytech.parkingcloud.core.service.IParkingCellService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.ParkingCellForm;
import com.yxytech.parkingcloud.platform.form.ParkingCellQuery;
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
 * @since 2017-10-19
 */
@RestController
@RequestMapping("/parkingCell")
public class ParkingCellController extends BaseController {

    @Autowired
    private IParkingCellService parkingCellService;

    @Autowired
    private IMagneticDeviceService magneticDeviceService;

    @GetMapping("/index")
    @Access(permissionName = "停车场资源查询",permissionCode = "PARKING_RESOURCE_QUERY",moduleCode = "basic_information")
    public ApiResponse<Object> index(@Valid @ModelAttribute("") ParkingCellQuery query, BindingResult bindingResult)throws BindException{
        this.validate(bindingResult);

        Page<ParkingCell> p = new Page<>(query.getPage(),query.getSize());
        EntityWrapper<ParkingCell> ew = new EntityWrapper<>();
        try{
            ew = queryCondition(ew,query);
        }catch (Exception ex){
            return apiFail("日期格式错误:" + ex.getMessage());
        }

        p = parkingCellService.selectPage(p,ew);

        return apiSuccess(p);
    }

    /**
     * 创建停车位信息
     * @return
     */
    @PostMapping("/addParkingCell")
    @Transactional
    @Access(permissionName = "停车场资源新建",permissionCode = "PARKING_RESOURCE_CREATE",moduleCode = "basic_information")
    public ApiResponse<Object> addParkingCell(@Valid @RequestBody ParkingCellForm parkingCellForm,BindingResult bindingResult)throws BindException{
        validate(bindingResult);

        ParkingCell parkingCell = new ParkingCell();
        BeanUtils.copyProperties(parkingCellForm,parkingCell);

        String existMsg = parkingCellService.validate(parkingCell.getParkingId(),parkingCell.getRoadbedCode(),parkingCell.getCode());
          if(StringUtils.isNotBlank(existMsg))
               return apiFail(existMsg);

        String gpsMsg = magneticDeviceService.validateGps(parkingCell.getGps());
          if(StringUtils.isNotBlank(gpsMsg))
               return apiFail(gpsMsg);

        parkingCell.setGps(magneticDeviceService.formattingGps(parkingCell.getGps()));

        parkingCellService.createParkingCell(parkingCell);

        return apiSuccess(null);
    }

    @PutMapping("/updateParkingCell")
    @Transactional
    @Access(permissionName = "停车场资源修改",permissionCode = "PARKING_RESOURCE_UPDATE",moduleCode = "basic_information")
    public ApiResponse<Object> updateParking(@Valid @RequestBody ParkingCellForm parkingCellForm,BindingResult bindingResult)throws BindException {
        validate(bindingResult);

        ParkingCell parkingCell = new ParkingCell();
        BeanUtils.copyProperties(parkingCellForm,parkingCell);

        String errorMsg = parkingCellService.updateValidate(parkingCell);
          if(errorMsg!=null)
               return apiFail(errorMsg);

        String gpsMsg = magneticDeviceService.validateGps(parkingCell.getGps());
          if(StringUtils.isNotBlank(gpsMsg))
               return apiFail(gpsMsg);

        parkingCell.setGps(magneticDeviceService.formattingGps(parkingCell.getGps()));

        parkingCellService.updateParkingCell(parkingCell);

        return apiSuccess(null);
    }
    /**
     * 根据ID查询停车位详细信息
     * @param id
     * @return
     */
    @GetMapping("/findParkingCellById")
    public ApiResponse<Object> findParkingCellById(@RequestParam(value = "id",required = false)Long id)throws NotFoundException{
       ParkingCell parkingCell = parkingCellService.detail(id);
       notFound(parkingCell,"停车位不存在");

       parkingCell.setGps(String.format("%.6f", parkingCell.getLongitude()) + " " + String.format("%.6f", parkingCell.getLatitude()));

       return  apiSuccess(parkingCell);
    }

    /**
     * 批量停用
     * @param ids
     * @return
     */
    @PutMapping("/nonUse")
    @Transactional
    @Access(permissionName = "停车场资源停用",permissionCode = "PARKING_RESOURCE_NONUSE",moduleCode = "basic_information")
    public ApiResponse<Object> nonUse(@RequestBody String ids){
        Map<String,Object> map = JSON.parseObject(ids);
        List<Long> newIds = (List<Long>) map.get("ids");

        List<ParkingCell> parkingCells = parkingCellService.selectBatchIds(newIds);
            if(newIds.size() > parkingCells.size()){
                parkingCells.forEach((item) -> newIds.remove(item.getId()));
                return apiFail("设备信息id不存在：" + newIds.toString());
            }
        List<ParkingCell> toUpdateCellList = new ArrayList<>();
        for(ParkingCell parkingCell : parkingCells){
            parkingCell.setUsing(false);
            parkingCell.setGps(String.format("%.6f", parkingCell.getLongitude()) + "," + String.format("%.6f", parkingCell.getLatitude()));
            toUpdateCellList.add(parkingCell);
        }
        parkingCellService.updateBatch(toUpdateCellList);

        return apiSuccess(null);
    }

    /**
     * 批量启用
     * @param ids
     * @return
     */
    @PutMapping("/startUsing")
    @Transactional
    @Access(permissionName = "停车场资源启用",permissionCode = "PARKING_RESOURCE_USING",moduleCode = "basic_information")
    public ApiResponse<Object> startUsing(@RequestBody String ids){
        if(StringUtils.isBlank(ids))
             return apiFail("非法参数");

        Map<String,Object> map = JSON.parseObject(ids);
        List<Long> newIds = (List<Long>) map.get("ids");

        List<ParkingCell> parkingCells = parkingCellService.selectBatchIds(newIds);
            if(newIds.size() > parkingCells.size()){
                parkingCells.forEach((item) -> newIds.remove(item.getId()));
                return apiFail("设备信息id不存在：" + newIds.toString());
            }
        List<ParkingCell> toUpdateCellList = new ArrayList<>();
        for(ParkingCell parkingCell : parkingCells){
            parkingCell.setUsing(true);
            parkingCell.setGps(String.format("%.6f", parkingCell.getLongitude()) + "," + String.format("%.6f", parkingCell.getLatitude()));
            toUpdateCellList.add(parkingCell);
        }
        parkingCellService.updateBatch(toUpdateCellList);

        return apiSuccess(null);
    }


    @GetMapping("/findAllParkingcells")
    public ApiResponse<Object> parkingCells(@RequestParam(value = "parkingId",required = false)Long parkingId,
                                            @RequestParam(value = "code",required = false)String code){
        EntityWrapper<ParkingCell> ew = new EntityWrapper<>();
        ew.setSqlSelect("code").eq("parking_id",parkingId).like("code",code);

        return apiSuccess(parkingCellService.selectMaps(ew));
    }


    private EntityWrapper<ParkingCell> queryCondition(EntityWrapper<ParkingCell> ew,ParkingCellQuery query)throws Exception{
        ew.eq(StringUtils.isNotBlank(query.getCode()),"yxy_parking_cell.code",query.getCode());
        ew.eq(StringUtils.isNotBlank(query.getRoadbedCode()),"roadbed_code",query.getRoadbedCode());
        ew.eq(query.getParkingId()!=null,"yxy_parking_cell.parking_id",query.getParkingId());
        ew.like(StringUtils.isNotBlank(query.getGps()),"yxy_parking_cell.gps",query.getGps());
        ew.eq(query.getUsing()!=null,"yxy_parking_cell.is_using",query.getUsing());
        ew.between(DateParserUtil.parseDate(query.getStart_time(),true)!=null&&
                            DateParserUtil.parseDate(query.getEnd_time(),false)!=null,
                   "created_at",
                            DateParserUtil.parseDate(query.getStart_time(),true),
                            DateParserUtil.parseDate(query.getEnd_time(),false));
        return ew;
    }
}
