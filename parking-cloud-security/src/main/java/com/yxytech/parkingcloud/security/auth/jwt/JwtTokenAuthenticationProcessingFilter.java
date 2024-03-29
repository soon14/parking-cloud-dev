package com.yxytech.parkingcloud.security.auth.jwt;

import com.yxytech.parkingcloud.security.auth.jwt.extractor.TokenExtractor;
import com.yxytech.parkingcloud.security.config.JwtProperties;
import com.yxytech.parkingcloud.security.model.token.RawAccessJwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 权限失败处理器
     */
    private final AuthenticationFailureHandler failureHandler;

    private final JwtProperties jwtProperties;

    /**
     * token提取器
     */
    private final TokenExtractor tokenExtractor;

    @Autowired
    public JwtTokenAuthenticationProcessingFilter(final AuthenticationFailureHandler failureHandler,
                                                  final TokenExtractor tokenExtractor,
                                                  RequestMatcher matcher, JwtProperties jwtProperties) {
        super(matcher);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String tokenPayload = request.getHeader(jwtProperties.getTokenHeaderParam());
        RawAccessJwtToken token = new RawAccessJwtToken(tokenExtractor.extract(tokenPayload));
        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
