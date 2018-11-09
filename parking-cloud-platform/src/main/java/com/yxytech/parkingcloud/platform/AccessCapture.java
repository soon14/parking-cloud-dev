
package com.yxytech.parkingcloud.platform;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yxytech.parkingcloud.core.entity.Module;
import com.yxytech.parkingcloud.core.entity.Permission;
import com.yxytech.parkingcloud.core.service.IModuleService;
import com.yxytech.parkingcloud.core.service.IPermissionService;
import com.yxytech.parkingcloud.platform.config.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.*;


@Component
public class AccessCapture implements ApplicationRunner {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IModuleService moduleService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));

        Set<String> moduleCodeSet = new HashSet();
        Map<String,String> permissionMap = new HashMap<>();

        for (BeanDefinition beanDefinition : scanner.findCandidateComponents("com.yxytech.parkingcloud.platform.controller")){
            String beanName = beanDefinition.getBeanClassName();
            try{
                Class beanClass = Class.forName(beanName);
                Method[] methods = beanClass.getMethods();

                for(Method method : methods){
                    Access access = method.getAnnotation(Access.class);
                    if(access == null)
                        continue;

                    moduleCodeSet.add(access.moduleCode());
                    permissionMap.put(access.permissionName(),access.permissionCode()+","+access.moduleCode());
                }
            }catch (ClassNotFoundException cne){
                cne.getException();
            }
        }

        for(String moduleCode: moduleCodeSet){
            EntityWrapper<Module> ewModule = new EntityWrapper<>();
            ewModule.eq("code",moduleCode);
            if(moduleService.selectCount(ewModule) > 0)
                 continue;
            Module module = new Module(moduleCode,moduleCode,1);
            moduleService.insert(module);
        }
        EntityWrapper<Module> ewModule = new EntityWrapper<>();
        ewModule.setSqlSelect("code","id").in("code",moduleCodeSet);
        List<Map<String,Object>> moduleMapList = moduleService.selectMaps(ewModule);

        for(Map.Entry<String,String> entry : permissionMap.entrySet()){
            String permissionName = entry.getKey();
            String permissionCode = entry.getValue().split(",")[0].toString();
            String moduleCode = entry.getValue().split(",")[1].toString();
            Long moduleId = null;
            for(Map<String,Object> moduleMap : moduleMapList){
                if(moduleCode.equals(moduleMap.get("code")))
                    moduleId = (Long) moduleMap.get("id");
            }

            EntityWrapper<Permission> ewPermission = new EntityWrapper<>();
            ewPermission.eq("code",permissionCode);
            ewPermission.eq("name",permissionName);
            if(permissionService.selectCount(ewPermission) > 0)
                 continue;

            Permission permission = new Permission(permissionName,permissionCode,moduleId);
            permissionService.insert(permission);
        }
    }
}


