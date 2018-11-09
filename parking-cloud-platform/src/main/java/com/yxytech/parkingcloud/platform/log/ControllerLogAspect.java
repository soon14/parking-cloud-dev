package com.yxytech.parkingcloud.platform.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxytech.parkingcloud.commons.entity.UserContext;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@Scope("request")
public class ControllerLogAspect {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String POINT = "execution( * com.yxytech.parkingcloud.platform.controller.*.*(..)) " +
            "|| execution( * com.yxytech.parkingcloud.security.endpoint.RefreshTokenEndpoint.refreshToken(..)) ";


    @Pointcut(POINT)
    public void aopPointCut() {}

    @Before("aopPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        String remoteIp = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(remoteIp)) {
            remoteIp = request.getRemoteAddr();
        }

        String params = null;
        List<Object> args = new ArrayList<>();
        for (Object arg :joinPoint.getArgs()) {
            if (arg instanceof BindingResult || arg instanceof HttpServletRequest) continue;
            if (arg instanceof MultipartFile) {
                args.add(((MultipartFile) arg).getOriginalFilename());
            } else {
                args.add(arg);
            }
        }
        try {
            params = mapper.writeValueAsString(args);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();


            if (principal instanceof UserContext) {
                userId = ((UserContext) principal).getUserId();
            }
        }

        MDC.put("method", method);
        MDC.put("uri", uri);
        MDC.put("queryString", queryString);
        MDC.put("remoteIp", remoteIp);
        MDC.put("params", params);
        MDC.put("userId", userId);
    }

    @After("aopPointCut()")
    public void doAfter() {
    }

    @AfterReturning(returning = "object", pointcut = "aopPointCut()")
    public void doAfterReturning(Object object) {
        String ret = null;
        try {
            ret = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            ret = object.toString();
        }

        long executed = System.currentTimeMillis() - Long.valueOf(MDC.get("start"));
        MDC.put("executedTime", executed + "");
        MDC.put("ret", ret);

    }

    @AfterThrowing(value = "aopPointCut()", throwing = "ex")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception ex) {
        long executed = System.currentTimeMillis() - Long.valueOf(MDC.get("start"));
        MDC.put("executedTime", executed + "");
        String ret = ex.getMessage();
        if (ret == null) {
            ret = ex.toString();
        }
        MDC.put("ret", ret);

    }
}
