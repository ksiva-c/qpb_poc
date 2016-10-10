package com.demo.limits.common.context;


import com.demo.limits.common.identity.*;

public class ApiContext {
    private RequestContext requestContext;
    private SessionContext sessionContext;

    public SessionContext getSessionContext() {
        return sessionContext;
    }

    public RequestContext getRequestContext() {
        return requestContext;
    }

    public ApiContext(RequestContext requestContext, SessionContext sessionContext){
        this.requestContext = requestContext;
        this.sessionContext = sessionContext;
	}

    public static class RequestContext{
        private String authToken;
        private long userId;
        private long applicationId;
        private long localeId;
        private long realmId;

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public long getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(long applicationId) {
            this.applicationId = applicationId;
        }

        public long getLocaleId() {
            return localeId;
        }

        public void setLocaleId(long localeId) {
            this.localeId = localeId;
        }

        public long getRealmId() {
            return realmId;
        }

        public void setRealmId(long realmId) {
            this.realmId = realmId;
        }
    }

    public static class SessionContext{
		private AuthenticatedSession authSession;
        private IdmUser user;
        private Application application;
        private Locale locale;
        private Realm realm;

        public AuthenticatedSession getAuthSession() {
            return authSession;
        }

        public void setAuthSession(AuthenticatedSession authSession) {
            this.authSession = authSession;
        }

        public IdmUser getUser() {
            return user;
        }

        public void setUser(IdmUser user) {
            this.user = user;
        }

        public Application getApplication() {
            return application;
        }

        public void setApplication(Application application) {
            this.application = application;
        }

        public Locale getLocale() {
            return locale;
        }

        public void setLocale(Locale locale) {
            this.locale = locale;
        }

        public Realm getRealm() {
            return realm;
        }

        public void setRealm(Realm realm) {
            this.realm = realm;
        }
    }
}
