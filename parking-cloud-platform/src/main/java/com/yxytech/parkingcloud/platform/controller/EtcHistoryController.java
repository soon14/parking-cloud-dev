package com.yxytech.parkingcloud.platform.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.EtcHistory;
import com.yxytech.parkingcloud.core.entity.EtcVersion;
import com.yxytech.parkingcloud.core.service.IEtcHistoryService;
import com.yxytech.parkingcloud.core.service.IEtcVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liwd
 * @since 2017-10-18
 */
@RestController
@RequestMapping("/etcHistory")
public class EtcHistoryController extends BaseController {
	@Autowired
    private IEtcHistoryService etcHistoryService;

	@Autowired
    private IEtcVersionService etcVersionService;

    @GetMapping("/getByVersion/{version}")
    public ApiResponse<Object> getAllByVersion(@PathVariable Integer version,
                                               @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                               @RequestParam(value = "size", defaultValue = pageSize, required = false) Integer size) {
        Page<EtcHistory> pagination = new Page<>(page, size);

        Page<EtcHistory> ret = etcHistoryService.getAllByVersion(pagination, version);

        return this.apiSuccess(ret);
    }

    @PostMapping("/history")
    public ApiResponse getAllHistory(@RequestBody EtcHistory etcHistory) {
        String etcNumber = etcHistory.getEtcNumber();

        if (etcNumber == null) {
            return this.apiFail(500, "Invalid params.");
        }

        String version = etcHistoryService.getAllVersion(etcNumber);

        if (version == null) {
            return this.apiSuccess(new ArrayList<>());
        }

        List<Integer> versions = JSON.parseArray(version, Integer.class);

        Wrapper<EtcVersion> etcVersionWrapper = new EntityWrapper<>();
        etcVersionWrapper.in("version", versions);
        List<EtcVersion> etcVersions = etcVersionService.selectList(etcVersionWrapper);

        return this.apiSuccess(etcVersions);
    }
}
