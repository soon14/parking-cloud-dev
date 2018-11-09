package com.yxytech.parkingcloud.security.auth.ajax;

import com.yxytech.parkingcloud.commons.entity.UserContext;
import com.yxytech.parkingcloud.commons.entity.UserIdentity;
import com.yxytech.parkingcloud.commons.interfaces.UserAuthEntity;
import com.yxytech.parkingcloud.commons.interfaces.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;

@Component
@PropertySource("classpath:application.properties")
public class AjaxAuthenticationProvider extends ApplicationObjectSupport implements AuthenticationProvider {

    @Autowired
    Environment env;

    public UserAuthService getUserAuthService() {
        String service = env.getProperty("security.userservice");
        return (UserAuthService) getApplicationContext().getBean(service);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        UserAuthService userAuthService = getUserAuthService();

        /*
         * 用户标识和密码对象
         */
        UserIdentity userIdentity = (UserIdentity) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        /*
         * 用户user接口获取用户对象信息
         */
        UserAuthEntity userAccount = userAuthService.getUserByUserIdentity(userIdentity);
        if (userAccount == null) {
            throw new UsernameNotFoundException("未找到用户");
        }

        /*
         * 密码匹配性验证
         */
        if (!userAuthService.validateUserPassword(userAccount, password)) {
            throw new BadCredentialsException("用户名或密码不正确");
        }

        /*
         * 创建用户上下文
         */
        UserContext userContext = new UserContext(userAccount.getUserId() + "", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
