package com.yxytech.parkingcloud.security.auth.jwt;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class SkipPathRequestMatcher implements RequestMatcher {

    /**
     * matcher
     */
    private OrRequestMatcher matchers;

    /**
     * 处理matcher
     */
    private RequestMatcher processingMatcher;

    /**
     * 构造方法
     * @param pathsToSkip
     * @param processingPath
     */
    public SkipPathRequestMatcher(List<String> pathsToSkip, String processingPath) {
        List<RequestMatcher> m = new ArrayList<>();
        for (String path : pathsToSkip) {
            m.add(new AntPathRequestMatcher(path));
        }

        this.matchers = new OrRequestMatcher(m);
        this.processingMatcher = new AntPathRequestMatcher(processingPath);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if (this.matchers.matches(request)) {
            return false;
        }

        return this.processingMatcher.matches(request);
    }

}
