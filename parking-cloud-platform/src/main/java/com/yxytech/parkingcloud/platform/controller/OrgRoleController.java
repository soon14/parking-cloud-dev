package com.yxytech.parkingcloud.platform.controller;


import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.service.IOrgRoleService;
import com.yxytech.parkingcloud.core.service.IOrganizationService;
import com.yxytech.parkingcloud.core.service.IRoleService;
import org.apache.ibatis.javassist.NotFoundException;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orgRole")
public class OrgRoleController extends BaseController{

    @Autowired
    private IOrgRoleService orgRoleService;

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IRoleService roleService;

    @PutMapping("/{id}")
    public ApiResponse<Object> bind(@PathVariable Long id, @RequestBody List<Long> roleIds) throws NotFoundException{
        Organization organization = organizationService.selectById(id);

        notFound(organization, "单位信息不存在");

        String errorMsg = roleService.validateRoleIds(roleIds);
        if (errorMsg != null) {
            return apiFail(errorMsg);
        }

        orgRoleService.syncRoles(roleIds, id);

        return apiSuccess(null);
    }

    @GetMapping("/{id}")
    public ApiResponse<Object> bindList(@PathVariable Long id) {

        return apiSuccess(orgRoleService.findBindRoleIds(id));
    }

    @GetMapping("/{id}/list")
    public ApiResponse<Object> roleList(@PathVariable Long id) {

        return apiSuccess(orgRoleService.findBindRoles(id));
    }
}
