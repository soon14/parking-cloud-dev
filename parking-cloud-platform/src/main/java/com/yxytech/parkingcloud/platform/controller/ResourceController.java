package com.yxytech.parkingcloud.platform.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.core.entity.*;
import com.yxytech.parkingcloud.core.service.*;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/resources")
public class ResourceController extends BaseController {

    @Autowired
    private IUserGroupRoleService userGroupRoleService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IUserUserGroupService userUserGroupService;

    @Autowired
    protected IResourceService resourceService;

    @Autowired
    protected IRolePermissionService rolePermissionService;

    @Autowired
    protected IPermissionResourceService permissionResourceService;

    @Autowired
    protected IRoleService roleService;

    @GetMapping("")
    public ApiResponse<Object> index() {

        EntityWrapper<Resource> ewOne = new EntityWrapper<>();

        ewOne.eq("level", 1).orderBy("sort",true);

        List<Resource> resourceOnes = resourceService.selectList(ewOne);

        List<Object> tree = new ArrayList<>();

        resourceOnes.forEach(one -> {

            Map<String, Object> ones = mapPut(one);
            List<Resource> ewTwo = selectWrapper(one);

            List<Object> twoList = new ArrayList<>();
            ewTwo.forEach(two -> {
                Map<String, Object> twos = mapPut(two);

                List<Resource> ewThree = selectWrapper(two);

                List<Object> threeList = new ArrayList<>();

                ewThree.forEach(three -> {
                    Map<String, Object> threes = mapPut(three);

                    List<Resource> ewFour = selectWrapper(three);

                    List<Object> fourList = new ArrayList<>();

                    ewFour.forEach(four -> {
                        Map<String, Object> fours = mapPut(four);

                        fourList.add(fours);
                    });

                    threes.put("children", fourList);

                    threeList.add(threes);
                });

                twos.put("children", threeList);
                twoList.add(twos);

            });
            ones.put("children",twoList);
            tree.add(ones);
        });


        return this.apiSuccess(tree);
    }

    protected Map<String, Object> mapPut(Resource resource) {

        Map<String, Object> map = new HashMap<>();

        map.put("id", resource.getId());
        map.put("name", resource.getName());

        return map;

    }

    protected List<Resource> selectWrapper(Resource resource) {


        EntityWrapper<Resource> wrapper = new EntityWrapper<>();

        wrapper.eq("parent_id", resource.getId());

        List<Resource> resources = resourceService.selectList(wrapper);

        return resources;

    }

    @GetMapping("/menus")
    public ApiResponse<Object> menus() {
        User user = getCurrentUser();

        EntityWrapper<UserRole> userRole = new EntityWrapper<>();

        userRole.setSqlSelect("role_id").eq("user_id", user.getId());

        List<Long> userRoleIds =  ListUtils.typedList(userRoleService.selectObjs(userRole),Long.class);

        EntityWrapper<UserUserGroup> userUserGroup = new EntityWrapper<>();

        userUserGroup.setSqlSelect("group_id").eq("user_id", user.getId());

        List<Long> userUserGroupIds =  ListUtils.typedList(userUserGroupService.selectObjs(userUserGroup),Long.class);

        if(userUserGroupIds.size() > 0){
            Set<Long> userGroupRoleIds = new HashSet<>();
            userUserGroupIds.forEach(userUserGroupId -> {
                EntityWrapper<UserGroupRole> userGroupRole = new EntityWrapper<>();

                userGroupRole.setSqlSelect("role_id").eq("group_id", userUserGroupId);

                List<Long> userUserGroupRoleIds =  ListUtils.typedList(userGroupRoleService.selectObjs(userGroupRole),Long.class);

                userGroupRoleIds.addAll(userUserGroupRoleIds);
            });

            userRoleIds.addAll(userGroupRoleIds);
        }

        if(userRoleIds.size() == 0){
            return this.apiSuccess(new ArrayList<>());
        }

        Role role = roleService.roleByCode("SUPER_ADMIN");


        if(userRoleIds.contains(role.getId())) {
            EntityWrapper<Resource> ew = new EntityWrapper<>();
            ew.orderBy("sort");

            return this.apiSuccess(resourceService.selectList(ew));
        }

        EntityWrapper<RolePermission> rolePermission = new EntityWrapper<>();

        rolePermission.setSqlSelect("permission_id").in("role_id", userRoleIds);

        List<Long> rolePermissionIds =  ListUtils.typedList(rolePermissionService.selectObjs(rolePermission),Long.class);


        if(rolePermissionIds.size() == 0){
            return this.apiSuccess(new ArrayList<>());
        }

        EntityWrapper<PermissionResource> permissionResource = new EntityWrapper<>();

        permissionResource.setSqlSelect("resource_id").in("permission_id", rolePermissionIds);

        List<Long> resourceIds =  ListUtils.typedList(permissionResourceService.selectObjs(permissionResource),Long.class);


        if(resourceIds.size() == 0){
            return this.apiSuccess(new ArrayList<>());
        }

        EntityWrapper<Resource> resourcesEw = new EntityWrapper<>();

        resourcesEw.in("id", resourceIds);
        resourcesEw.orderBy("sort");

        List<Resource> resources = resourceService.selectList(resourcesEw);

        return this.apiSuccess(resources);
    }
}
