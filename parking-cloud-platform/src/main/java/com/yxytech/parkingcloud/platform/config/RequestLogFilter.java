package com.yxytech.parkingcloud.platform.config;

import com.yxytech.parkingcloud.platform.log.ControllerLogAspect;
import com.yxytech.parkingcloud.platform.log.LogMarkers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RequestLogFilter implements Filter {
    private static Logger logger = LogManager.getLogger(ControllerLogAspect.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put("start", System.currentTimeMillis()+"");
        chain.doFilter(request, response);
        MDC.put("retCode", ((HttpServletResponse)response).getStatus() +"");
        logger.info(LogMarkers.GrpcMarker, "");
    }

    @Override
    public void destroy() {

    }
}
