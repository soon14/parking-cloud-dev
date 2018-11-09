package com.yxytech.parkingcloud.platform.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.Organization;
import com.yxytech.parkingcloud.core.entity.User;
import com.yxytech.parkingcloud.core.entity.UserAccount;
import com.yxytech.parkingcloud.core.service.*;
import com.yxytech.parkingcloud.platform.config.Access;
import com.yxytech.parkingcloud.platform.form.RepasswordForm;
import com.yxytech.parkingcloud.platform.form.UserForm;
import com.yxytech.parkingcloud.platform.form.UserQuery;
import com.yxytech.parkingcloud.platform.form.UsersWithRolesGroupsQuery;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IUserGroupRoleService userGroupRoleService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IUserUserGroupService userUserGroupService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserGroupService userGroupService;

    @Autowired
    private IOrganizationService organizationService;


    /**
     * 获取当前平台用户
     * @return
     */
    @RequestMapping("/me")
    public ApiResponse<Object> me() {
        LogManager.getLogger().info("---- a---");
        return this.apiSuccess(userInfo());
    }

    /**
     * 平台用户列表查询
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResponse<Object> index(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                     @RequestParam(value = "size", defaultValue = pageSize, required = false) Integer size,
                                     @Valid @ModelAttribute("") UserQuery query, BindingResult bindingResult)
            throws BindException {
        this.validate(bindingResult);

        Page<User> p = new Page<>(page, size);
        EntityWrapper<User> ew = new EntityWrapper<>();

        ew.like(StringUtils.isNotBlank(query.getName()), "u.name", query.getName())
            .eq(StringUtils.isNotBlank(query.getMobile()), "ua.mobile", query.getMobile())
            .eq(StringUtils.isNotBlank(query.getEmail()), "ua.email", query.getEmail());

        p = userService.selectPage(p, ew);

        return apiSuccess(p);
    }

    /**
     * 创建平台用户
     * @param userForm
     * @param userErrors
     * @return
     * @throws BindException
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @Transactional
    @Access(permissionName = "平台用户管理",permissionCode = "USER_MANAGE",moduleCode = "account_auth_manage")
    public ApiResponse<Object> create(@Valid @RequestBody UserForm userForm, BindingResult userErrors)
            throws BindException {
        // 验证用户账号字段
        validate(userErrors);

        User user = new User();
        UserAccount userAccount = new UserAccount();
        BeanUtils.copyProperties(userForm, user);
        BeanUtils.copyProperties(userForm, userAccount);

        // 判断新账号字段是否可用
        String invalidNewAccount = userAccountService.invalidNewAccount(userAccount);
        if (StringUtils.isNotBlank(invalidNewAccount)) {
            return apiFail(invalidNewAccount);
        }

        userService.insert(user);
        userAccount.setUserId(user.getId());
        userAccount = userAccountService.create(userAccount);

        return apiSuccess("");
    }

    /**
     * 修改平台用户
     * @param userForm
     * @param userErrors
     * @return
     * @throws BindException
     */
    @PutMapping("/update/{id}")
    public ApiResponse<Object> update(@PathVariable Long id, @Valid @RequestBody UserForm userForm, BindingResult userErrors)
            throws BindException {
        // 验证用户账号字段
        validate(userErrors);

        User user = new User();
        UserAccount userAccount = new UserAccount();
        BeanUtils.copyProperties(userForm, user);
        BeanUtils.copyProperties(userForm, userAccount);


        //判断新字段是否可用
        String invalidUpdateAccount = userAccountService.invalidUpdateAccount(userAccount);
        if (StringUtils.isNotBlank(invalidUpdateAccount)) {
            return apiFail(invalidUpdateAccount);
        }
        user.setId(id);
        userService.updateById(user);

        EntityWrapper<UserAccount> ew = new EntityWrapper<>();

        ew.eq("user_id", id);

        UserAccount userAccountById = userAccount.selectOne(ew);

        userAccount.setId(userAccountById.getId());

        String errorMsg = userAccountService.invalidUpdate(userAccount);
        if(StringUtils.isNotBlank(errorMsg))
            return apiFail(errorMsg);

        userAccountService.updateById(userAccount);

        return apiSuccess("");
    }


    /**
     * 查询附带用户角色，用户组信息的用户列表
     * @param query
     * @param errors
     * @return
     * @throws BindException
     */
    @GetMapping("/users_with_roles_groups")
    public ApiResponse<Object> usersWithRolesGroups(
            @Valid @ModelAttribute UsersWithRolesGroupsQuery query,
            BindingResult errors) throws BindException {
        validate(errors);

        Map<String, Object> data = new HashMap<>();
        Page<User> page = new Page<>(1, Integer.valueOf(pageSize));
        Page<User> users;

        Map<String, Object> params = buildUsersWithRolesGroupsParamMap(query);
        users = userService.selectUsersWithRolesGroups(page, params);

        if (users.getRecords().size() == 0) {
            data.put("users", users);
            return apiSuccess(data);
        }

        Set<Long> userIds = new HashSet<>();
        users.getRecords().forEach(item -> userIds.add(item.getId()));

        Map<Long, Set<Long>> usersRoles = userRoleService.queryUsersRolesMap(userIds);
        Map<Long, Set<Long>> usersUserGroups = userUserGroupService.queryUsersUserGroupsMap(userIds);
        Map<Long, Set<Long>> usersTransRoles = userGroupRoleService.transToUsersRoles(usersUserGroups);

        Set<Long> roleIds = new HashSet<>();
        Set<Long> userGroupIds = new HashSet<>();
        usersUserGroups.values().forEach(item -> userGroupIds.addAll(item));
        usersTransRoles.keySet().forEach(item -> usersTransRoles.get(item).addAll(usersRoles.get(item)));
        usersTransRoles.values().forEach(item -> roleIds.addAll(item));

        Map<Long, String> roles = roleService.selectIdNameMap(roleIds, "id", "name");
        Map<Long, String> groups = userGroupService.selectIdNameMap(userGroupIds, "id", "name");

        data.put("roles", roles);
        data.put("groups", groups);
        data.put("users", users);
        data.put("usersRoles", usersRoles);
        data.put("usersTransRoles", usersTransRoles);
        data.put("usersUserGroups", usersUserGroups);

        return apiSuccess(data);
    }

    /**
     * 构造附带用户角色，用户组信息的用户列表查询条件
     * @param query
     * @return
     */
    private Map<String, Object> buildUsersWithRolesGroupsParamMap(UsersWithRolesGroupsQuery query) {
        Map<String, Object> params = new HashMap<>();
        params.put("orgId", query.getOrgId());

        if (query.getRoleId() != null) {
            if (query.getRoleId() == 0) {
                params.put("noRoles", true);
            } else {
                params.put("roleIds", Arrays.asList(new Long[] { query.getRoleId() }));

                if (query.getGroupId() != null && query.getGroupId() != 0) {
                    Set<Long> groupIds = userGroupRoleService.groupsWithRole(query.getRoleId());
                    if (query.getGroupId() != null && query.getGroupId() != 0) {
                        groupIds.add(query.getGroupId());
                    }
                    if (groupIds.size() > 0) {
                        params.put("rolesOrGroups", true);
                    }
                } else {
                    params.put("rolesOrGroups", false);
                }
            }
        }
        if (query.getGroupId() != null) {
            if (query.getGroupId() == 0) {
                params.put("noGroups", true);
            } else {
                params.put("groupIds", Arrays.asList(new Long[] { query.getGroupId() }));
            }
        }

        return params;
    }


    /**
     * 查询单个用户的角色，用户组信息
     * @return
     */
    @GetMapping("/{id}/roles_groups")
    public ApiResponse<Object> userRolesGroups(@PathVariable Long id) throws NotFoundException {
        User user = userService.selectById(id);
        notFound(user, "用户不存在");

        Set<Long> userRoleIds = userRoleService.queryUserRoleIds(user.getId());
        Set<Long> userGroupIds = userUserGroupService.queryUserGroupIds(user.getId());
        Set<Long> userTransRoleIds = userGroupRoleService.transToUserRoleIds(userGroupIds);
        userTransRoleIds.addAll(userRoleIds);

        Map<String, Object> data = new HashMap<>();
        data.put("userRoles", userRoleIds);
        data.put("userGroups", userGroupIds);
        data.put("userTransRoles", userTransRoleIds);

        return apiSuccess(data);
    }


    /**
     * 更新用户角色绑定
     * @param id
     * @param roleIds
     * @return
     */
    @PutMapping("/{id}/roles")
    @Transactional
    @Access(permissionName = "用户角色管理",permissionCode = "USERROLE_MANAGE",moduleCode = "account_auth_manage")
    public ApiResponse<Object> updateRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) throws NotFoundException {
        User user = userService.selectById(id);
        notFound(user, "用户不存在");

        String errorMsg = roleService.validateRoleIds(roleIds);
        if (errorMsg != null) {
            return apiFail(errorMsg);
        }

        userRoleService.syncRoles(id, roleIds);

        return apiSuccess(null);
    }


    /**
     * 更新用户组绑定
     * @param id
     * @param groupIds
     * @return
     */
    @PutMapping("/{id}/groups")
    @Transactional
    public ApiResponse<Object> updateGroups(@PathVariable Long id, @RequestBody List<Long> groupIds) throws NotFoundException {
        User user = userService.selectById(id);
        notFound(user, "用户不存在");
        EntityWrapper<UserAccount> ew = new EntityWrapper<>();
        ew.eq("user_id", user.getId());
        UserAccount account = userAccountService.selectOne(ew);

        String errorMsg = userGroupService.validateGroupIds(account.getOrgId(), groupIds);
        if (errorMsg != null) {
            return apiFail(errorMsg);
        }

        userUserGroupService.syncGroups(id, groupIds);

        return apiSuccess(null);
    }

    /**
     * 查询用户，用户账户信息
     * @param id
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/show/{id}")
    public ApiResponse<Object> show(@PathVariable Long id)throws NotFoundException {
        User user = userService.selectById(id);
        notFound(user,"用户不存在");

        EntityWrapper<UserAccount> ew = new EntityWrapper<>();
        ew.eq("user_id", user.getId());
        UserAccount userAccount =  userAccountService.selectOne(ew);

        EntityWrapper<Organization> ewOrg = new EntityWrapper<>();
        ewOrg.setSqlSelect("full_name").eq("id",userAccount.getOrgId());
        user.setOrgName(organizationService.selectObj(ewOrg).toString());

        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        data.put("userAccount", userAccount);

        return this.apiSuccess(data);
    }

    @PutMapping("/repassword/{id}")
    public ApiResponse<Object> repassword(@PathVariable Long id, @RequestBody UserAccount userAccount)throws NotFoundException {
        User user = userService.selectById(id);

        EntityWrapper<UserAccount> ew = new EntityWrapper<>();
        ew.eq("user_id", user.getId());
        UserAccount userAccountById =  userAccountService.selectOne(ew);

        userAccount.setId(userAccountById.getId());
        userAccount.setPassword(userAccountService.encodePassword(userAccount.getPassword()));

        userAccountService.updateById(userAccount);

        return this.apiSuccess(null);
    }

    @PutMapping("/rename/{id}")
    public ApiResponse<Object> rename(@PathVariable Long id, @RequestBody User user) throws NotFoundException{

        user.setId(id);

        userService.updateById(user);

        return apiSuccess(null);
    }

    @PutMapping("/currentUserRepassword/{id}")
    public ApiResponse<Object> currentUserRepassword(@PathVariable Long id,@Valid @RequestBody RepasswordForm repasswordForm,BindingResult bindingResult ) throws BindException, NotFoundException{
        validate(bindingResult);

        UserAccount userAccount = userAccountService.selectById(id);
        notFound(userAccount,"用户不存在");

        Boolean bool = userAccountService.validateUserPassword(userAccount, repasswordForm.getOrigin());

        if(!bool) {
            return apiFail("原密码错误");
        }

        userAccount.setPassword(userAccountService.encodePassword(repasswordForm.getNewPassword()));

        userAccountService.updateById(userAccount);

        return apiSuccess(null);

    }
}
