package com.demo.limits.idm.service;

import com.demo.limits.common.context.ApiContext;
import com.demo.limits.common.identity.*;
import com.demo.limits.idm.api.IdentityOperations;
import com.demo.limits.model.common.User;

import javax.inject.Named;

@Named("ovIdentityTemplate")
public class IdentityTemplate implements IdentityOperations {

    @Override
    public ApiContext.SessionContext authenticate(ApiContext.RequestContext request) {

        ApiContext.SessionContext session = new ApiContext.SessionContext();

        session.setUser(
                new IdmUser( request.getUserId(),"UUID-" + request.getUserId(),
                        "360@demo.com"
                ).setFirstName("360").setLastName("Application"));
        session.setApplication(new Application(request.getApplicationId(), "360 Optimizer"));
        session.setLocale(new Locale(request.getLocaleId(), "en_US"));
        session.setRealm(
                new Realm(request.getRealmId(),"Realm UUID-" + request.getUserId(), "FORD")
        );
        session.setAuthSession(new AuthenticatedSession(request.getAuthToken()));

        return session;
    }

    @Override
    public User getUser(String uuid) {
        User user = new User(uuid, "me");
        return user;
    }
}
