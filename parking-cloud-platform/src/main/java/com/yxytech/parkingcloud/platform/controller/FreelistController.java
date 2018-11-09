package com.yxytech.parkingcloud.platform.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.service.IFreelistService;
import com.yxytech.parkingcloud.core.service.IOrganizationService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.service.ITimespanService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.core.utils.FreeEntity;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.StatusForm;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liwd
 * @since 2017-10-20
 */
@RestController
@RequestMapping("/freelist")
public class FreelistController extends BaseController {
    @Autowired
    private IFreelistService freelistService;

    @Autowired
    private ITimespanService timespanService;

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IOrganizationService organizationService;

    /**
     * 列表
     * @param page
     * @param size
     * @param status
     * @return
     */
    @GetMapping("")
    public ApiResponse index(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                             @RequestParam(value = "size", defaultValue = pageSize, required = false) Integer size,
                             @RequestParam(value = "status", required = false) Boolean status,
                             @RequestParam(value = "color", defaultValue = "0", required = false) Integer color,
                             @RequestParam(value = "plate_number", defaultValue = "", required = false) String plateNumber,
                             @RequestParam(value = "start_time", required = false) String start,
                             @RequestParam(value = "end_time", required = false) String end,
                             @RequestParam(value = "parking_id", required = false) Integer parkingId) {
        Page<Freelist> freelistPage = new Page<>(page, size);
        Wrapper<Freelist> freelistWrapper = new EntityWrapper<>();

        Date startTime = null;
        Date endTime = null;

        try {
            startTime = DateParserUtil.parseDate(start, true);
            endTime = DateParserUtil.parseDate(end, false);
        } catch (Exception e) {
            return apiFail(e.getMessage());
        }

        freelistWrapper
            .where(parkingId != null, "yxy_freelist.parking_id = {0}", parkingId)
            .where(status != null, "yxy_freelist.is_valid = {0}", status)
            .where(color != 0, "yxy_freelist.plate_color = {0}", color)
            .where((! plateNumber.equals("")), "yxy_freelist.plate_number = {0}", plateNumber)
            .where(startTime != null, "yxy_freelist.created_at >= {0}", startTime)
            .where(endTime != null, "yxy_freelist.created_at <= {0}", endTime);

        return this.apiSuccess(freelistService.customerSelect(freelistPage, freelistWrapper));
    }

    /**
     * 插入
     * @param freelist
     * @return
     */
    @PutMapping("")
    public ApiResponse create(@RequestBody Freelist freelist) {
        Wrapper<Parking> parkingWrapper = new EntityWrapper<>();
        parkingWrapper.eq("id", freelist.getParkingId());

        Parking parkingInfo = parkingService.selectOne(parkingWrapper);

        if (parkingInfo == null) {
            return this.apiFail(500, "找不到停车场!");
        }

        if (parkingInfo.getOperatorOrgId() != null) {
            freelist.setOrganizationId(parkingInfo.getOperatorOrgId());
        } else {
            freelist.setOrganizationId(0L);
        }

        try {
            freelistService.create(freelist);
        } catch (RuntimeException e) {
            return this.apiFail("创建免费车信息失败: " + e.getMessage());
        }

        return this.apiSuccess("ok");
    }

    /**
     * 获取
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApiResponse getDetail(@PathVariable Integer id) throws NotFoundException {
        Freelist freelist = freelistService.selectById(id);

        notFound(freelist, "免费车信息不存在!");

        Wrapper<Timespan> timespanWrapper = new EntityWrapper<>();
        timespanWrapper.where("freelist_id = {0}", freelist.getId());
        List<Timespan> timespanList = timespanService.selectList(timespanWrapper);
        freelist.setTimespans(timespanList);

        Wrapper<Parking> parkingWrapper = new EntityWrapper<>();
        parkingWrapper.eq("id", freelist.getParkingId());
        Parking parking = parkingService.selectOne(parkingWrapper);

        Wrapper<Organization> organizationWrapper = new EntityWrapper<>();
        organizationWrapper.eq("id", parking.getOperatorOrgId());
        Map orgName = organizationService.selectIdNameMap(organizationWrapper, "id", "full_name");

        Map<String, Object> result = new HashMap<String, Object>() {{
            put("freelist", freelist);
            put("parking", new HashMap<Long, String >() {{
                put(parking.getId(), parking.getFullName());
            }});
            put("organization", orgName);
        }};

        return this.apiSuccess(result);
    }

    /**
     * 查询是否在黑名单中
     * @param statusForm
     * @param bindingResult
     * @return
     * @throws BindException
     */
    @PostMapping("/exists")
    public ApiResponse exists(@Valid @RequestBody StatusForm statusForm, BindingResult bindingResult) throws BindException {
        validate(bindingResult);

        Wrapper<Freelist> freelistWrapper = new EntityWrapper<>();
        Date startTime = null;
        Date endTime = null;

        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(statusForm, orderInfo);

        try {
            startTime = DateParserUtil.formatDate(statusForm.getEnterTime());
            endTime = DateParserUtil.formatDate(statusForm.getLeaveTime());
        } catch (Exception e) {
            return this.apiFail("非法的时间格式!");
        }

        orderInfo.setEnterAt(startTime);
        orderInfo.setLeaveAt(endTime);

        freelistWrapper.where("plate_number = {0}", statusForm.getPlateNumber())
                .and("plate_color = {0}", statusForm.getPlateColor())
                .and("parking_id = {0}", statusForm.getParkingId())
                .and("end_at > {0}", startTime)
                .and("is_valid = TRUE");

        List<Freelist> freelists = freelistService.selectList(freelistWrapper);
        List<FreeEntity> freeEntities = freelistService.getAllFreeTime(orderInfo, freelists, startTime, endTime);

        if (freeEntities.size() != 0) {
            return this.apiSuccess(freeEntities);
        }

        return this.apiSuccess("no");
    }

    /**
     * 设置状态
     * @param id
     * @param params
     * @return
     */
    @PostMapping("/changeStatus/{id}")
    public ApiResponse setStatus(@PathVariable Integer id, @RequestBody Freelist params) throws NotFoundException {
        Freelist freelist = freelistService.selectById(id);
        Boolean status = params.getValid();

        notFound(freelist, "免费车信息不存在!");
        notFound(status, "状态不能为空!");

        freelist.setIsValid(status);

        if (status) {
            freelist.setJoinReason(params.getJoinReason());
        } else {
            freelist.setOutReason(params.getOutReason());
        }

        Boolean ret = freelistService.updateById(freelist);

        return this.apiSuccess("ok");
    }
}
