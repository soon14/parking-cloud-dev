package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Permission;
import com.yxytech.parkingcloud.core.entity.PermissionResource;
import com.yxytech.parkingcloud.core.service.IModuleService;
import com.yxytech.parkingcloud.core.service.IPermissionResourceService;
import com.yxytech.parkingcloud.core.service.IPermissionService;
import com.yxytech.parkingcloud.platform.config.Access;
import org.apache.commons.collections.ListUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/permissions")
public class PermissionController extends BaseController {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IPermissionResourceService permissionResourceService;

    @Autowired
    private IModuleService moduleService;

    @GetMapping("")
    @Access(permissionName = "权限管理",permissionCode = "PERMISSION_MANAGE",moduleCode = "account_auth_manage")
    public ApiResponse<Object> index(@RequestParam(value = "page", defaultValue = "1", required = false) Integer curPage,
                                     @RequestParam(value = "size", defaultValue = pageSize, required = false) Integer size) {
        Page<Permission> page = new Page<>(curPage, size);
        EntityWrapper<Permission> ew = new EntityWrapper<>();
        ew.orderBy("id",true);

        page = permissionService.selectPage(page, ew);

        Set<Long> moduleIds = new HashSet<>();
        Map<Long, String> modules = new HashMap<>();
        if (page.getRecords().size() > 0) {
            page.getRecords().forEach(item -> moduleIds.add(item.getModuleId()));
            modules = moduleService.selectIdNameMap(moduleIds, "id", "name");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("permissions", page);
        data.put("modules", modules);

        return apiSuccess(data);
    }

    @GetMapping("/{id}")
    public ApiResponse<Object> show(@PathVariable Long id) throws NotFoundException {
        Permission permission = permissionService.selectById(id);
        notFound(permission, "权限不存在");

        Set<Long> moduleIds = new HashSet<>();
        moduleIds.add(permission.getModuleId());
        Map<Long, String> modules = moduleService.selectIdNameMap(moduleIds, "id", "name");

        Map<String, Object> data = new HashMap<>();
        data.put("permission", permission);
        data.put("modules", modules);

        return apiSuccess(data);
    }

    @GetMapping("/{id}/resources")
    public ApiResponse<Object> queryResources(@PathVariable Long id) throws NotFoundException {
        Permission permission = permissionService.selectById(id);
        notFound(permission, "权限不存在");

        EntityWrapper<PermissionResource> ew = new EntityWrapper<>();

        ew.setSqlSelect("resource_id").eq("permission_id", id);

        List<Object> resourceIds =  ListUtils.typedList(permissionResourceService.selectObjs(ew),Long.class);

        return apiSuccess(resourceIds);
    }

    @PutMapping("/{id}/resources")
    public ApiResponse<Object> syncResources(@PathVariable Long id, @RequestBody List<Long> resourceIds) {

        permissionResourceService.syncResource(id, resourceIds);

        return apiSuccess(null);
    }
}
