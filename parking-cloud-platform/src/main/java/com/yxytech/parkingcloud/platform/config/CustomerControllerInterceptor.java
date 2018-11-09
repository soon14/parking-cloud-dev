package com.yxytech.parkingcloud.platform.config;

import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.commons.entity.UserContext;
import com.yxytech.parkingcloud.core.service.IPermissionService;
import com.yxytech.parkingcloud.core.service.IUserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Scope("request")
public class CustomerControllerInterceptor {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IUserService userService;

    @Pointcut("execution( * com.yxytech.parkingcloud.platform.controller.*.*(..)) ")
    public void ControllerInterceptorPointcut() {
    }

    @Around("ControllerInterceptorPointcut()")
    public Object Interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Access access = signature.getMethod().getAnnotation(Access.class);

        if (access == null) {
            return joinPoint.proceed();
        }

        String code = access.permissionCode();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = null;
        if (principal instanceof UserContext) {
            userId = Long.valueOf(((UserContext) principal).getUserId());
        }

        if (userId == null) {
            return new ApiResponse<>(ApiResponse.PERMISSION_DENIED, "拒绝访问!");
        }

        if (! userService.needDataIsolate(userId)) {
            return joinPoint.proceed();
        }

        List<String> permissionCodes  = permissionService.getPermissionCodesByUser(userId);
        if (! permissionCodes.contains(code)) {
            return new ApiResponse<>(ApiResponse.PERMISSION_DENIED, "拒绝访问!");
        }

        return joinPoint.proceed();
    }
}
