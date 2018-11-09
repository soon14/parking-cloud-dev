package com.yxytech.parkingcloud.platform.config;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Access  {

    String  permissionName();

    String  permissionCode();

    String  moduleCode();

}
