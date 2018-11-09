package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.exception.ServiceException;
import com.yxytech.parkingcloud.core.service.*;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.RoleForm;
import org.apache.commons.collections.ListUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IOrgRoleService orgRoleService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IModuleService moduleService;

    @Autowired
    private IRolePermissionService rolePermissionService;

    /**
     * 查询角色列表
     * @return
     */
    @GetMapping("")
    public ApiResponse<Object> index() {
        Long userId = getCurrentUser().getId();
        UserAccount account = userAccountService.selectByUserId(userId);
        if (account == null) {
            return this.apiFail(ServiceException.NOT_FOUND_ACCOUNT, "账号不存在");
        }
        Boolean isSuperAdmin = userService.needDataIsolate(userId);

        Page<Role> page = new Page<>(1, Integer.valueOf(pageSize));
        EntityWrapper<Role> ew = new EntityWrapper<>();
        ew.orderBy("id",true);

        if (isSuperAdmin) {
            Long orgId = account.getOrgId();
            List<Long> roleIds = orgRoleService.findBindRoleIds(orgId);

            if (roleIds.isEmpty()) {
                return this.apiSuccess(page.setRecords(new ArrayList<>()));
            }
            return this.apiSuccess(page.setRecords(roleService.selectBatchIds(roleIds)));
        }

        page.setRecords(roleService.selectList(ew));

        return apiSuccess(page);
    }

    /**
     * 添加角色
     * @param errors
     * @return
     */
    @PostMapping(value = "")
    public ApiResponse<Object> create(@Valid @RequestBody RoleForm form,
                                      BindingResult errors) throws BindException {
        validate(errors);

        Role role = new Role();
        BeanUtils.copyProperties(form, role);
        roleService.insert(role);

        return apiSuccess(null);
    }

    /**
     * 查看角色详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApiResponse<Object> show(@PathVariable Long id) {
        Role role = roleService.selectById(id);
        return apiSuccess(role);
    }

    /**
     * 更新角色
     * @param id
     * @param form
     * @param errors
     * @return
     * @throws BindException
     */
    @PutMapping("/{id}")
    public ApiResponse<Object> update(@PathVariable Long id,
                                      @RequestBody RoleForm form, BindingResult errors) throws BindException {
        Role role = roleService.selectById(id);
        this.validate(errors);

        BeanUtils.copyProperties(form, role, "id");
        roleService.updateById(role);

        return apiSuccess(null);
    }

    /**
     * 查询所有权限及角色所选权限
     * @param id
     * @return
     */
    @GetMapping("/{id}/permissions")
    public ApiResponse<Object> queryPermissions(@PathVariable Long id) {
        Map<String, Object> data = new HashMap<>();

        EntityWrapper<Permission> ewPermission = new EntityWrapper<>();
        ewPermission.setSqlSelect("id", "name", "module_id").orderBy("id");
        List<Map<String, Object>> permissions = permissionService.selectMaps(ewPermission);
        Set<Long> moduleIds = new HashSet<>();
        permissions.forEach(item -> moduleIds.add((Long)item.get("module_id")));

        EntityWrapper<Module> ewModule = new EntityWrapper<>();
        ewModule.in("id", moduleIds);
        Map<Object, Object> modules = moduleService.selectIdNameMap(ewModule, "id", "name");

        EntityWrapper<RolePermission> ewRolePermission = new EntityWrapper<>();
        ewRolePermission.setSqlSelect("permission_id").eq("role_id", id);
        List<Long> selectedPermissionIds = ListUtils.typedList(rolePermissionService.selectObjs(ewRolePermission), Long.class);

        data.put("permissions", permissions);
        data.put("modules", modules);
        data.put("selectedPermissionIds", selectedPermissionIds);

        return apiSuccess(data);
    }

    @PutMapping("/{id}/permissions")
    public ApiResponse<Object> syncPermissions(@PathVariable Long id,
                                               @RequestBody List<Long> permissionIds) throws NotFoundException {
        Role role = roleService.selectById(id);
        notFound(role, "角色不存在");

        String errorMsg = permissionService.validatePermissionIds(permissionIds);
        if (errorMsg != null) {
            return apiFail(errorMsg);
        }

        rolePermissionService.syncPermissions(id, permissionIds);

        return apiSuccess(null);
    }
}
