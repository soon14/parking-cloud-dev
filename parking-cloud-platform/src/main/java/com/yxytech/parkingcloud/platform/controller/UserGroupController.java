package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.exception.ServiceException;
import com.yxytech.parkingcloud.core.service.*;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.UserGroupForm;
import org.apache.commons.collections.ListUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.baomidou.mybatisplus.toolkit.IdWorker.getId;

@RestController
@RequestMapping("/user_groups")
public class UserGroupController extends BaseController {

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IUserGroupService userGroupService;

    @Autowired
    private IUserUserGroupService userUserGroupService;

    @Autowired
    private IUserGroupRoleService userGroupRoleService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IAreaService areaService;

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IUserGroupAreaService userGroupAreaService;

    @Autowired
    private IUserGroupParkingService userGroupParkingService;

    @Autowired
    private IOrganizationService organizationService;

    @Autowired
    private IUserService userService;

    /**
     * 用户组列表
     * @return
     */
    @GetMapping("")
    public ApiResponse<Object> index(@RequestParam(value = "orgId",required = false)Long orgId) {

        if (orgId == null) {
            Long userId = getCurrentUser().getId();
            UserAccount account = userAccountService.selectByUserId(userId);
            if (account == null) {
                return this.apiFail(ServiceException.NOT_FOUND_ACCOUNT, "账号不存在");
            }

            orgId = account.getOrgId();
        }

        Page<UserGroup> p = new Page<>(1, Integer.parseInt(pageSize));
        EntityWrapper<UserGroup> ew = new EntityWrapper<>();
        ew.eq("org_id",orgId);

        return this.apiSuccess(p.setRecords(userGroupService.selectList(ew)));
    }

    /**
     * 当前查询单位的用户组
     */
    @GetMapping("/current_organization_user_groups/{user_id}")
    public ApiResponse<Object> currentOrganizationUserGroup(@PathVariable Long user_id)
    {

        UserAccount account = userAccountService.selectByUserId(user_id);
        if (account == null) {
            return this.apiFail(ServiceException.NOT_FOUND_ACCOUNT, "账号不存在");
        }

        Long orgId = account.getOrgId();

        Page<UserGroup> p = new Page<>(1, Integer.parseInt(pageSize));
        EntityWrapper<UserGroup> ew = new EntityWrapper<>();
        ew.eq("org_id",orgId);
        return this.apiSuccess(p.setRecords(userGroupService.selectList(ew)));
    }

    /**
     * 新增用户组
     * @param form
     * @param errors
     * @return
     * @throws BindException
     */
    @PostMapping("")
    @Access(permissionName = "用户组管理",permissionCode = "USERGROUP_MANAGE",moduleCode = "account_auth_manage")
    public ApiResponse<Object> create(@Valid @RequestBody UserGroupForm form,
                                      BindingResult errors) throws BindException {
        validate(errors);

        Map userInfo = userInfo();
        User user = (User) userInfo.get("user");
        UserAccount userAccount = (UserAccount) userInfo.get("userAccount");

        if (userService.needDataIsolate(user.getId())) {
            if (! userAccount.getOrgId().equals(form.getOrgId())) {
                return apiFail("非法访问!");
            }
        }

        UserGroup group = new UserGroup();
        BeanUtils.copyProperties(form, group);

        String errorMsg = userGroupService.validate(form.getOrgId(),form.getName());
          if(StringUtils.isNotBlank(errorMsg))
               return apiFail(errorMsg);

        userGroupService.insert(group);

        return apiSuccess(null);
    }

    /**
     * 用户组详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ApiResponse<Object> show(@PathVariable Long id) throws NotFoundException {
        UserGroup group = userGroupService.selectById(id);
        notFound(group, "用户组不存在");

        EntityWrapper<Organization> ew = new EntityWrapper<>();
        ew.setSqlSelect("full_name").eq("id",group.getOrgId());
        group.setOrgName(organizationService.selectObj(ew).toString());

        return apiSuccess(group);
    }

    /**
     * 更新用户组
     * @param id
     * @param form
     * @param errors
     * @return
     * @throws BindException
     * @throws NotFoundException
     */
    @PutMapping("/{id}")
    public ApiResponse<Object> update(@PathVariable Long id,
                                      @Valid @RequestBody UserGroupForm form,
                                      BindingResult errors) throws BindException, NotFoundException {
        UserGroup group = userGroupService.selectById(id);
        notFound(group, "用户组不存在");
        validate(errors);

        BeanUtils.copyProperties(form, group, "id");
        userGroupService.updateById(group);

        return apiSuccess(null);
    }

    /**
     * 删除用户组
     * @param id
     * @return
     * @throws NotFoundException
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Object> delete(@PathVariable Long id) throws NotFoundException {
        UserGroup group = userGroupService.selectById(id);
        notFound(group, "用户组不存在");

        if (userUserGroupService.hasGroup(id)) {
            return apiFail("当前组下存在用户，不能删除");
        }

        userGroupService.deleteById(id);

        return apiSuccess(null);
    }

    @PutMapping("/{id}/roles")
    @Transactional
    public ApiResponse<Object> updateRoles(@PathVariable Long id,
                                           @RequestBody List<Long> roleIds) throws NotFoundException {
        UserGroup group = userGroupService.selectById(id);
        notFound(group, "用户组不存在");

        String errorMsg = roleService.validateRoleIds(roleIds);
        if (errorMsg != null) {
            return apiFail(errorMsg);
        }

        userGroupRoleService.syncRoles(id, roleIds);

        return apiSuccess(null);
    }

    @PutMapping("/{id}/areas")
    @Transactional
    public ApiResponse<Object> updateAreas(@PathVariable Long id, @RequestBody List<Long> areaIds) throws NotFoundException {
        UserGroup group = userGroupService.selectById(id);
        notFound(group, "用户组不存在");

        String errorMsg = areaService.validateAreaIds(areaIds);
        if (errorMsg != null) {
            return apiFail(errorMsg);
        }

        userGroupAreaService.syncAreas(id, areaIds);

        return apiSuccess(null);
    }

    @PutMapping("/{id}/parkings")
    @Transactional
    public ApiResponse<Object> updateParkings(@PathVariable Long id, @RequestBody List<Long> parkingIds) throws NotFoundException {
        UserGroup group = userGroupService.selectById(id);
        notFound(group, "用户组不存在");

        if (parkingIds == null) {
            parkingIds = new ArrayList<Long>();
        }
        String errorMsg = parkingService.validateParkingIds(parkingIds);
        if (errorMsg != null) {
            return apiFail(errorMsg);
        }

        userGroupParkingService.syncParkings(id, parkingIds);

        return apiSuccess(null);
    }

    @GetMapping("/{id}/parkings")
    public ApiResponse<Object> parkings(@PathVariable Long id) throws NotFoundException {
        UserGroup group = userGroupService.selectById(id);
        notFound(group, "用户组不存在");

        EntityWrapper<UserGroupParking> ew = new EntityWrapper<>();
        ew.setSqlSelect("parking_id").eq("group_id", id);
            List<Long> parkingIds = ListUtils.typedList(userGroupParkingService.selectObjs(ew), Long.class);
        if(parkingIds.isEmpty())
            return apiSuccess(null);

        EntityWrapper<Parking> ewParking = new EntityWrapper<>();
        ewParking.setSqlSelect("id", "code","full_name", "approve_status").in("id", parkingIds);
        List<Map<String, Object>> parkings = parkingService.selectMaps(ewParking);

        EntityWrapper<UserGroupRole> ewRole = new EntityWrapper<>();
        ewRole.setSqlSelect("role_id").eq("group_id",id);
            List<Long> roleIds = ListUtils.typedList(userGroupRoleService.selectObjs(ewRole),Long.class);

        Map<String,Object> data = new HashMap<>();
        data.put("parkingList", this.nullToArrayList(parkings));
        data.put("roleIds", this.nullToArrayList(roleIds));

        return apiSuccess(data);
    }
}
