package com.yxytech.parkingcloud.platform.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Advertisement;
import com.yxytech.parkingcloud.core.enums.AdvertisementTypeEnum;
import com.yxytech.parkingcloud.core.service.IAdvertisementService;
import com.yxytech.parkingcloud.platform.config.Access;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cj
 * @since 2017-10-27
 */
@RestController
@RequestMapping("/advertisement")
public class AdvertisementController extends BaseController {

    @Autowired
    private IAdvertisementService advertisementService;
    private final static String urlPattern = "(https?)://[^\\s]*";

    @GetMapping("/index")
    public ApiResponse<Object> index(
                                     @RequestParam(value = "size", defaultValue = pageSize, required = false) Integer size,
                                     @RequestParam(value = "page", defaultValue = "1",required = false) Integer page,
                                     @RequestParam(value = "title", required = false) String title
                                    )
    {
        Page<Advertisement> p = new Page<>(page, size);
        EntityWrapper<Advertisement> ew = new EntityWrapper<>();

        ew.like(StringUtils.isNotBlank(title), "title", title)
          .orderBy("id",true);

        p = advertisementService.selectPage(p, ew);

        return this.apiSuccess(p);
    }

    private void urlValidate(String url) throws Exception {
        if (url == null) {
            return;
        }

        if (! url.matches(urlPattern)) {
            throw new Exception("链接不符合格式");
        }
    }

    @PutMapping("/update")
    @Access(permissionName = "广告管理",permissionCode = "ADVERTISEMENT_MANAGE",moduleCode = "system_manage")
    public ApiResponse<Object> update(@RequestBody Advertisement advertisement){
        try {
            urlValidate(advertisement.getLink());
        } catch (Exception e) {
            return apiFail(e.getMessage());
        }

        advertisementService.updateAllColumnById(advertisement);


        return this.apiSuccess(null);
    }

    @PostMapping("/create")
    @Access(permissionName = "广告管理",permissionCode = "ADVERTISEMENT_MANAGE",moduleCode = "system_manage")
    public ApiResponse<Object> create(@RequestBody Advertisement advertisement) {
        try {
            urlValidate(advertisement.getLink());
        } catch (Exception e) {
            return apiFail(e.getMessage());
        }

        advertisementService.insertAllColumn(advertisement);


        return this.apiSuccess(null);
    }

    @PutMapping("/startUsing")
    @Access(permissionName = "广告管理",permissionCode = "ADVERTISEMENT_MANAGE",moduleCode = "system_manage")
    public ApiResponse<Object> startUsing(@RequestBody String ids) {
        Map<String, Object> map = JSON.parseObject(ids);
        List<Long> newIds = (List<Long>) map.get("ids");

        List<Advertisement> advertisements = advertisementService.selectBatchIds(newIds);

        if(newIds.size() > advertisements.size()){
            advertisements.forEach((item) -> newIds.remove(item.getId()));
            return apiFail("广告信息id不存在：" + newIds.toString());
        }

        advertisements.forEach(item -> item.setStatus(true));

        advertisementService.updateBatchById(advertisements);

        return this.apiSuccess(null);
    }

    @PutMapping("/nonUse")
    @Access(permissionName = "广告管理",permissionCode = "ADVERTISEMENT_MANAGE",moduleCode = "system_manage")
    public ApiResponse<Object> nonuse(@RequestBody String ids) {
        Map<String, Object> map = JSON.parseObject(ids);
        List<Long> newIds = (List<Long>) map.get("ids");
        List<Advertisement> advertisements = advertisementService.selectBatchIds(newIds);

        if(newIds.size() > advertisements.size()){
            advertisements.forEach((item) -> newIds.remove(item.getId()));
            return apiFail("广告信息id不存在：" + newIds.toString());
        }

        advertisements.forEach(item -> item.setStatus(false));

        advertisementService.updateBatchById(advertisements);

        return apiSuccess(null);
    }

    @GetMapping("findById")
    public ApiResponse<Object> findById(@RequestParam(value = "id", required = false) Integer id) {
        if(id != null){
            return this.apiSuccess(advertisementService.selectById(id));
        }

        return this.apiFail("查询失败");
    }

    @GetMapping("/findAllAdvertisementType")
    public ApiResponse<Object> findAllOrgNature(){
        List data = new ArrayList();
        for(AdvertisementTypeEnum value : AdvertisementTypeEnum.values()){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id",value.getValue());
            map.put("name",value.getDesc());
            data.add(map);
        }
        return this.apiSuccess(data);
    }
}
