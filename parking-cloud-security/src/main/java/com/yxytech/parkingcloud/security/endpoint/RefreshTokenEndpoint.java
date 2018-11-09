package com.yxytech.parkingcloud.security.endpoint;

import com.yxytech.parkingcloud.commons.AbstractController;
import com.yxytech.parkingcloud.commons.entity.ApiResponse;
import com.yxytech.parkingcloud.commons.entity.UserContext;
import com.yxytech.parkingcloud.commons.interfaces.UserAuthEntity;
import com.yxytech.parkingcloud.commons.interfaces.UserAuthService;
import com.yxytech.parkingcloud.security.auth.jwt.extractor.TokenExtractor;
import com.yxytech.parkingcloud.security.config.JwtProperties;
import com.yxytech.parkingcloud.security.model.token.JwtTokenFactory;
import com.yxytech.parkingcloud.security.model.token.RawAccessJwtToken;
import com.yxytech.parkingcloud.security.model.token.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@RestController
@PropertySource("classpath:application.properties")
public class RefreshTokenEndpoint extends AbstractController {
    @Autowired
    Environment env;

    /**
     * token生产工厂
     */
    @Autowired
    private JwtTokenFactory tokenFactory;

    /**
     * jwt一些参数设置
     */
    @Autowired
    private JwtProperties jwtSettings;


    /**
     * token提取执行器
     */
    @Autowired
    @Qualifier("jwtHeaderTokenExtractor")
    private TokenExtractor tokenExtractor;

    public UserAuthService getUserAuthService(HttpServletRequest request) {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        String service = env.getProperty("security.userservice");
        return (UserAuthService) ctx.getBean(service);
    }

    /**
     * 刷新token
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping(value="/auth/token")
    public ApiResponse<Object> refreshToken(HttpServletRequest request,
                                            HttpServletResponse response) throws IOException, ServletException {

        String token = request.getHeader(this.jwtSettings.getTokenHeaderParam());
        String tokenPayload = this.tokenExtractor.extract(token);
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);

        /*
         * 校验原有token是否有效
         */
        Optional<RefreshToken> refreshToken = RefreshToken.create(rawToken, this.jwtSettings.getTokenSigningKey());
        if (!refreshToken.isPresent()) {
            this.apiFail("token无效");
        }

        UserAuthService userAuthService = getUserAuthService(request);
        /*
         * 从token中解析username是否存在
         */
        String userId = refreshToken.get().getSubject();
        UserAuthEntity user = userAuthService.getUserByAccountId(Long.parseLong(userId));
        if (user == null) {
            this.apiFail("账号不存在");
        }

        UserContext userContext = new UserContext(userId, new ArrayList<>());
        Map<String, Object> data = tokenFactory.createTokenData(userContext);

        return this.apiSuccess(data);
    }
}
