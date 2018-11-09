package com.yxytech.parkingcloud.platform.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Parking;
import com.yxytech.parkingcloud.core.entity.Whitelist;
import com.yxytech.parkingcloud.core.service.IParkingService;
import com.yxytech.parkingcloud.core.service.IWhitelistService;
import com.yxytech.parkingcloud.core.utils.DateParserUtil;
import com.yxytech.parkingcloud.core.utils.ValidList;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.StatusForm;
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
@RequestMapping("/whitelist")
public class WhitelistController extends BaseController {

    @Autowired
    private IWhitelistService whitelistService;

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
        Page<Whitelist> whitelistPage = new Page<>(page, size);
        Wrapper<Whitelist> whitelistEntityWrapper = new EntityWrapper<>();

        Date startTime = null;
        Date endTime = null;

        try {
            startTime = DateParserUtil.parseDate(start, true);
            endTime = DateParserUtil.parseDate(end, false);
        } catch (Exception e) {
            return apiFail(e.getMessage());
        }

        whitelistEntityWrapper
            .where(parkingId != null, "yxy_whitelist.parking_id = {0}", parkingId)
            .where(status != null, "yxy_whitelist.is_valid = {0}", status)
            .where(color != null, "yxy_whitelist.plate_color = {0}", color)
            .where((! plateNumber.equals("")), "yxy_whitelist.plate_number = {0}", plateNumber)
            .where(startTime != null, "yxy_whitelist.created_at >= {0}", startTime)
            .where(endTime != null, "yxy_whitelist.created_at <= {0}", endTime);

        return this.apiSuccess(whitelistService.customerSelect(whitelistPage, whitelistEntityWrapper));
    }

    /**
     * 单个创建白名单
     * @return
     */
    @PutMapping("")
    @Access(permissionName = "状态名单新建",permissionCode = "STATUSLIST_CREATE",moduleCode = "status_list_manage")
    public ApiResponse create(@Valid @RequestBody StatusListCreateForm statusListForm, BindingResult bindingResult) throws BindException, NotFoundException {
        validate(bindingResult);

        Whitelist whitelist = new Whitelist();

        BeanUtils.copyProperties(statusListForm, whitelist);

        try {
            whitelistService.isReflect(whitelist);
        } catch (RuntimeException e) {
            return this.apiFail(e.getMessage());
        }

        Wrapper<Parking> parkingWrapper = new EntityWrapper<>();
        parkingWrapper.eq("id", whitelist.getParkingId());

        Parking parkingInfo = parkingService.selectOne(parkingWrapper);
        notFound(parkingInfo, "找不到停车场!");

        if (parkingInfo.getOperatorOrgId() != null) {
            whitelist.setOrganizationId(parkingInfo.getOperatorOrgId());
        } else {
            whitelist.setOrganizationId(0L);
        }

        whitelistService.insert(whitelist);

        return this.apiSuccess("");
    }

    /**
     * 批量创建
     * @param whitelists
     * @return
     */
    @PutMapping("/batch")
    @Transactional
    public ApiResponse createBatch(@Valid @RequestBody ValidList<StatusListForm> whitelists, BindingResult bindingResult) throws BindException {
        validate(bindingResult);

        try {
            for (StatusListForm whitelistParam : whitelists) {
                Whitelist whitelist = new Whitelist();

                BeanUtils.copyProperties(whitelistParam, whitelist);

                whitelist.setStartedAt(DateParserUtil.formatDate(whitelistParam.getStartedAt()));
                whitelist.setEndAt(DateParserUtil.formatDate(whitelistParam.getEndAt()));

                Wrapper<Parking> parkingWrapper = new EntityWrapper<>();

                parkingWrapper.eq("full_name", whitelist.getParkingName())
                        .orNew().eq("short_name", whitelist.getParkingName());

                Parking parking = parkingService.selectOne(parkingWrapper);

                if (parking == null) {
                    return this.apiFail(500, "停车场为: " + whitelist.getParkingName() + "找不到!");
                }

                whitelist.setParkingId(parking.getId());

                if (parking.getOperatorOrgId() != null) {
                    whitelist.setOrganizationId(parking.getOperatorOrgId());
                } else {
                    whitelist.setOrganizationId(0L);
                }

                whitelistService.isReflect(whitelist);
                whitelistService.insert(whitelist);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return this.apiFail(e.getMessage());
        }

        return this.apiSuccess("ok");
    }

    /**
     * 获取单个白名单详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApiResponse getOne(@PathVariable Integer id) throws NotFoundException {
        Whitelist whitelist = whitelistService.selectById(id);

        notFound(whitelist, "获取黑名单详情失败!");

        return apiSuccess(whitelist);
    }

    /**
     * 修改某个白名单
     * @param id
     * @param whitelist
     * @return
     */
    @PostMapping("/{id}")
    public ApiResponse modify(@PathVariable Integer id, @RequestParam Whitelist whitelist) throws NotFoundException {
        Whitelist whitelistResult = whitelistService.selectById(id);
        notFound(whitelistResult, "非法的白名单!");

        Wrapper<Whitelist> whitelistWrapper = new EntityWrapper<>();

        whitelistWrapper.where("end_at > {0}", whitelist.getStartedAt())
                .and("plate_number = {0}", whitelist.getPlateNumber())
                .and("plate_color = {0}", whitelist.getPlateColor())
                .and("parking_id = {0}", whitelist.getParkingId());

        whitelistService.update(whitelist, whitelistWrapper);

        return this.apiSuccess("ok");
    }

    /**
     * 单个修改状态
     * @param id
     * @param whitelist
     * @return
     */
    @PostMapping("/status/{id}")
    @Access(permissionName = "状态名单移除",permissionCode = "STATUSLIST_DELETE",moduleCode = "status_list_manage")
    public ApiResponse setStatus(@PathVariable Integer id, @RequestBody Whitelist whitelist) {
        Wrapper<Whitelist> whitelistWrapper = new EntityWrapper<>();
        Whitelist whitelistInstance = new Whitelist();

        whitelistWrapper.eq("id", id);

        whitelistInstance.setValid(whitelist.getValid());

        if (whitelist.getValid()) {
            whitelistInstance.setJoinReason(whitelist.getJoinReason());
        } else {
            whitelistInstance.setOutReason(whitelist.getOutReason());
        }

        whitelistService.update(whitelistInstance, whitelistWrapper);

        return this.apiSuccess("ok");
    }

    /**
     * 查询是否在白名单
     * @param statusForm
     * @return
     */
    @PostMapping("/exists")
    public ApiResponse isBlacklist(@Valid @RequestBody StatusForm statusForm, BindingResult bindingResult) throws BindException {
        validate(bindingResult);

        Wrapper<Whitelist> whitelistWrapper = new EntityWrapper<>();

        whitelistWrapper.where("plate_number = {0}", statusForm.getPlateNumber())
                .and("parking_id = {0}", statusForm.getParkingId())
                .and("plate_color = {0}", statusForm.getPlateColor())
                .and("end_at >= now()")
                .and("is_valid = TRUE");

        List<Whitelist> whitelists = whitelistService.selectList(whitelistWrapper);

        if (whitelists != null && whitelists.size() > 0) {
            return this.apiSuccess("yes");
        }

        return this.apiSuccess("no");
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

        Wrapper<Whitelist> whitelistWrapper = new EntityWrapper<>();
        whitelistWrapper.in("id", ids);

        Whitelist whitelist = new Whitelist();
        whitelist.setValid(status);

        whitelistService.update(whitelist, whitelistWrapper);

        return this.apiSuccess("ok");
    }
}
