package com.yxytech.parkingcloud.core.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.yxytech.parkingcloud.commons.entity.UserContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class YXYMetaObjectHandler extends MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter("createdAt") && metaObject.hasSetter("createdAt")) {
            setFieldValByName("createdAt", new Date(), metaObject);
        }

        if (metaObject.hasGetter("updatedAt") && metaObject.hasSetter("updatedAt")) {
            setFieldValByName("updatedAt", new Date(), metaObject);
        }

        Authentication auth = auth =  SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof UserContext) {
            UserContext context = (UserContext) auth.getPrincipal();
            if (metaObject.hasGetter("createdBy") && metaObject.hasSetter("createdBy")) {
                setFieldValByName("createdBy", Long.parseLong(context.getUserId()), metaObject);
            }

            if (metaObject.hasGetter("updatedBy") && metaObject.hasSetter("updatedBy")) {
                setFieldValByName("updatedBy", Long.parseLong(context.getUserId()), metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter("et.updatedAt") && metaObject.hasSetter("et.updatedAt")) {
            setFieldValByName("updatedAt", new Date(), metaObject);
        }

        if (metaObject.hasGetter("et.updatedBy") && metaObject.hasSetter("et.updatedBy")) {
            Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof UserContext) {
                UserContext context = (UserContext) auth.getPrincipal();
                setFieldValByName("updatedBy", Long.parseLong(context.getUserId()), metaObject);
            }
        }
    }
}
