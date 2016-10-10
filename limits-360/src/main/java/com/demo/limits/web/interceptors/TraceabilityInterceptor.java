package com.demo.limits.web.interceptors;

import com.demo.limits.common.context.ContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by lnv.
 */
public class TraceabilityInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TraceabilityInterceptor.class.getName());

    private static final String DEFAULT_TID_REQUEST_HEADER_NAME = "x-tid";
    private static final String DEFAULT_TID_MDC_KEY  = "tid";
    private static final String DEFAULT_USERID_MDC_KEY  = "userId";


    private String tidHeaderName = DEFAULT_TID_REQUEST_HEADER_NAME;
    private String tidKey = DEFAULT_TID_MDC_KEY;
    private String userIdKey = DEFAULT_USERID_MDC_KEY;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String transaction = request.getHeader(tidHeaderName);
        if (transaction == null || transaction.length() == 0) {
            // TODO: Check on the best approach to generate a UUID for request id tracking
            transaction = UUID.randomUUID().toString();
        }

        MDC.put(tidKey, transaction);
        // TODO: Add user id to the context
        String userId = "-1";
        try {
            long contextUserId = ContextHolder.get().getRequestContext().getUserId();
            userId = Long.toString(contextUserId);
        }
        catch (Exception e) {
            // TODO: figure out how to test this path
            logger.error("Error fetching context details.", e);
        }
        MDC.put(userIdKey,userId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(tidKey);
        MDC.remove(userIdKey);
    }
}
