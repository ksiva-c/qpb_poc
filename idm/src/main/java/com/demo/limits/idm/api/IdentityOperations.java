package com.demo.limits.idm.api;

import com.demo.limits.common.context.ApiContext;
import com.demo.limits.model.common.User;

/**
 *
 * User: lnv
 * Date: 
 * Time: 
 * To change this template use File | Settings | File Templates.
 */
public interface IdentityOperations {

    public ApiContext.SessionContext authenticate(ApiContext.RequestContext request);

    public User getUser(String username);

}
