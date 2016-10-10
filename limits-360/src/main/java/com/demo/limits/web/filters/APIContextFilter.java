package com.demo.limits.web.filters;

import com.demo.limits.common.context.ApiContext;
import com.demo.limits.common.context.ContextHolder;
import com.demo.limits.idm.api.IdentityOperations;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * User: lnv
 * To change this template use File | Settings | File Templates.
 */

@Named("apiContextFilter")
public class APIContextFilter extends OncePerRequestFilter{

    @Inject
    @Named("ovIdentityTemplate")
    IdentityOperations identityOperations;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            ApiContext.RequestContext requestContext = new ApiContext.RequestContext();
            requestContext.setUserId(1l);
            requestContext.setApplicationId(1l);
            requestContext.setLocaleId(1l);
            requestContext.setRealmId(1l);
            requestContext.setAuthToken("TOKEN");

            ApiContext.SessionContext sessionContext = identityOperations.authenticate(requestContext);

            ApiContext context = new ApiContext(requestContext, sessionContext);

            ContextHolder.set(context);

            filterChain.doFilter(request, response);
        }
        finally {
            ContextHolder.unset();
        }
    }
}
