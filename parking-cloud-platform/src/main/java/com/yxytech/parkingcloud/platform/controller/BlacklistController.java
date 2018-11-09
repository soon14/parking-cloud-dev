package com.yxytech.parkingcloud.platform.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Blacklist;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.service.IBlacklistService;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.core.utils.ValidList;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.StatusListCreateForm;
import com.yxytech.parkingcloud.platform.form.StatusListForm;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
 * @author liwd
 * @since 2017-10-20
 */
@RestController
@RequestMapping("/blacklist")
public class BlacklistController extends BaseController {

    @Autowired
    private IBlacklistService blacklistService;

    @Autowired
    private IParkingService parkingService;

    @GetMapping("")
    public ApiResponse index(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                             @RequestParam(value = "size", defaultValue = pageSize, required = false) Integer size,
                             @RequestParam(value = "status", required = false) Boolean status,
                             @RequestParam(value = "color", required = false) Integer color,
                             @RequestParam(value = "plate_number", defaultValue = "", required = false) String plateNumber,
                             @RequestParam(value = "start_time", required = false) String start,
                             @RequestParam(value = "end_time", required = false) String end,
                             @RequestParam(value = "parking_id", required = false) Integer parkingId) {
        Page<Blacklist> blacklistPage = new Page<>(page, size);
        Wrapper<Blacklist> blacklistWrapper = new EntityWrapper<>();

        Date startTime = null;
        Date endTime = null;

        try {
            startTime = DateParserUtil.parseDate(start, true);
            endTime = DateParserUtil.parseDate(end, false);
        } catch (Exception e) {
            return apiFail(e.getMessage());
        }

        blacklistWrapper
            .where(parkingId != null, "yxy_blacklist.parking_id = {0}", parkingId)
            .where(status != null, "yxy_blacklist.is_valid = {0}", status)
            .where(color != null, "yxy_blacklist.plate_color = {0}", color)
            .where((! plateNumber.equals("")), "yxy_blacklist.plate_number = {0}", plateNumber)
            .where(startTime != null, "yxy_blacklist.created_at >= {0}", startTime)
            .where(endTime != null, "yxy_blacklist.created_at <= {0}", endTime);

        return this.apiSuccess(blacklistService.customerSelect(blacklistPage, blacklistWrapper));
    }

    /**
     * 单个创建黑名单
     * @return
     */
    @PutMapping("")
    public ApiResponse create(@Valid @RequestBody StatusListCreateForm statusListForm, BindingResult bindingResult) throws BindException, NotFoundException {
        validate(bindingResult);

        Blacklist blacklist = new Blacklist();
        BeanUtils.copyProperties(statusListForm, blacklist);

        try {
            blacklistService.isReflect(blacklist);
        } catch (RuntimeException e) {
            return this.apiFail(e.getMessage());
        }

        Wrapper<Parking> parkingWrapper = new EntityWrapper<>();
        parkingWrapper.eq("id", blacklist.getParkingId());

        Parking parkingInfo = parkingService.selectOne(parkingWrapper);
        notFound(parkingInfo, "找不到停车场!");

        if (parkingInfo.getOperatorOrgId() != null) {
            blacklist.setOrganizationId(parkingInfo.getOperatorOrgId());
        } else {
            blacklist.setOrganizationId(0L);
        }

        blacklistService.insert(blacklist);

        return this.apiSuccess("ok");
    }

    /**
     * 批量创建
     * @param blacklists
     * @return
     */
    @PutMapping("/batch")
    @Transactional
    public ApiResponse createBatch(@Valid @RequestBody ValidList<StatusListForm> blacklists, BindingResult bindingResult) throws BindException {
        validate(bindingResult);

        try {
            for (StatusListForm blacklistParam : blacklists) {
                Blacklist blacklist = new Blacklist();

                BeanUtils.copyProperties(blacklistParam, blacklist);

                blacklist.setStartedAt(DateParserUtil.formatDate(blacklistParam.getStartedAt()));
                blacklist.setEndAt(DateParserUtil.formatDate(blacklistParam.getEndAt()));

                Wrapper<Parking> parkingWrapper = new EntityWrapper<>();

                parkingWrapper.eq("full_name", blacklist.getParkingName())
                        .orNew().eq("short_name", blacklist.getParkingName());

                Parking parking = parkingService.selectOne(parkingWrapper);
                notFound(parking, "停车场为: " + blacklist.getParkingName() + "找不到!");

                blacklist.setParkingId(parking.getId());

                if (parking.getOperatorOrgId() != null) {
                    blacklist.setOrganizationId(parking.getOperatorOrgId());
                } else {
                    blacklist.setOrganizationId(0L);
                }

                blacklistService.isReflect(blacklist);
                blacklistService.insert(blacklist);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return this.apiFail(e.getMessage());
        }

        return this.apiSuccess("");
    }

    /**
     * 获取单个黑名单详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApiResponse getOne(@PathVariable Integer id) throws NotFoundException {
        Blacklist blacklist = blacklistService.selectById(id);

        notFound(blacklist, "获取黑名单详情失败!");

        return apiSuccess(blacklist);
    }

    /**
     * 修改某个黑名单
     * @param id
     * @param blacklist
     * @return
     */
    @PostMapping("/{id}")
    public ApiResponse modify(@PathVariable Integer id, @RequestParam Blacklist blacklist) throws NotFoundException {
        Blacklist blacklistResult = blacklistService.selectById(id);
        notFound(blacklistResult, "非法的黑名单!");

        Wrapper<Blacklist> blacklistWrapper = new EntityWrapper<>();

        blacklistWrapper.where("end_at > {0}", blacklist.getStartedAt())
                .and("plate_number = {0}", blacklist.getPlateNumber())
                .and("plate_color = {0}", blacklist.getPlateColor())
                .and("parking_id = {0}", blacklist.getParkingId());

        Boolean ret = blacklistService.update(blacklist, blacklistWrapper);

        return this.apiSuccess("");
    }

    /**
     * 单个修改状态
     * @param id
     * @param blacklist
     * @return
     */
    @PostMapping("/status/{id}")
    public ApiResponse setStatus(@PathVariable Integer id, @RequestBody Blacklist blacklist) {
        Wrapper<Blacklist> blacklistWrapper = new EntityWrapper<>();
        Blacklist blacklistInstance = new Blacklist();

        blacklistWrapper.eq("id", id);

        blacklistInstance.setValid(blacklist.getValid());

        if (blacklist.getValid()) {
            blacklistInstance.setJoinReason(blacklist.getJoinReason());
        } else {
            blacklistInstance.setOutReason(blacklist.getOutReason());
        }

        Boolean ret = blacklistService.update(blacklistInstance, blacklistWrapper);

        return this.apiSuccess("");
    }

    /**
     * 查询是否在黑名单中
     * @param plateNumber
     * @param color
     * @return
     */
    @PostMapping("/exists")
    public ApiResponse isBlacklist(@RequestParam(value = "plate_number", defaultValue = "") String plateNumber,
                                   @RequestParam(value = "plate_color", defaultValue = "0") Integer color,
                                   @RequestParam(value = "parking_id", defaultValue = "0") Long parkingId) {
        Boolean ret = blacklistService.isInBlackList(plateNumber, color, parkingId);

        if (! ret) {
            return this.apiSuccess("no");
        }

        return this.apiSuccess("yes");
    }

    /**
     * 批量修改状态
     * @param params
     * @return
     */
    @PostMapping("/batch/changeStatus")
    public ApiResponse changeStatusBatch(@RequestBody String params) {
        Map<String, Object> param = JSON.parseObject(params);

        List<Long> ids = (List<Long>) param.get("ids");
        Boolean status = (Boolean) param.get("status");

        Wrapper<Blacklist> blacklistWrapper = new EntityWrapper<>();
        blacklistWrapper.in("id", ids);

        Blacklist blacklist = new Blacklist();
        blacklist.setValid(status);

        Boolean ret = blacklistService.update(blacklist, blacklistWrapper);

        return this.apiSuccess("");
    }
}
