package com.demo.limits.web.filters;

/**
 * Created by lnv.
 */
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Named("swaggerCORSFilter")
public class SwaggerCORSFilter extends OncePerRequestFilter {

    @Inject
    Environment environment;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {
        if(environment.acceptsProfiles("swagger")) {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
            //response.setHeader("Access-Control-Max-Age", "3600");
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}