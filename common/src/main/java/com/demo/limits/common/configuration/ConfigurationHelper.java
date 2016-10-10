package com.demo.limits.common.configuration;

import com.demo.limits.common.context.ApiContext;
import com.demo.limits.common.context.ContextHolder;
import com.demo.limits.common.identity.*;

import java.util.Map;

/**
 * User: lnv
 * Date: 8/21/14
 * Time: 3:26 PM
 */
public class ConfigurationHelper {
    /**
     * Get the config value as a boolean.
     *
     * @param name The config setting name.
     * @param values The map of config values
     * @param defaultValue The default value to use if not found
     *
     * @return The value.
     */
    public static boolean getBoolean(String name, Map values, boolean defaultValue) {
        Object value = values.get( name );
        if ( value == null ) {
            return defaultValue;
        }
        if ( Boolean.class.isInstance( value ) ) {
            return ( (Boolean) value ).booleanValue();
        }
        if ( String.class.isInstance( value ) ) {
            return Boolean.parseBoolean( (String) value );
        }
        throw new RuntimeException(
                "Could not determine how to handle configuration value [name=" + name + ", value=" + value + "] as boolean"
        );
    }

    public static void defaultContext(){
        ApiContext.RequestContext requestContext = new ApiContext.RequestContext();
        requestContext.setUserId(1l);
        requestContext.setApplicationId(1l);
        requestContext.setLocaleId(1l);
        requestContext.setRealmId(1l);
        requestContext.setAuthToken("TOKEN");

        ApiContext.SessionContext session = new ApiContext.SessionContext();

        session.setUser(
                new IdmUser( requestContext.getUserId(),"UUID-" + requestContext.getUserId(),
                        "360@demo.com"
                ).setFirstName("360").setLastName("Application"));
        session.setApplication(new Application(requestContext.getApplicationId(), "360 Optimizer"));
        session.setLocale(new Locale(requestContext.getLocaleId(), "en_US"));
        session.setRealm(
                new Realm(requestContext.getRealmId(),"Realm UUID-" + requestContext.getUserId(), "FORD")
        );
        session.setAuthSession(new AuthenticatedSession(requestContext.getAuthToken()));

        ApiContext context = new ApiContext(requestContext, session);

        ContextHolder.set(context);
    }
}
