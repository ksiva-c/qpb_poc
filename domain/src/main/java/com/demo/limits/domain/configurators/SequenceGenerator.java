package com.demo.limits.domain.configurators;

import com.demo.limits.common.configuration.ConfigurationHelper;
import org.hibernate.dialect.Dialect;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.util.Properties;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
public class SequenceGenerator extends SequenceStyleGenerator {

    public static final String CONFIG_SEQUENCE_PER_ENTITY_PREFIX = "sequence_per_entity_prefix";
    public static final String USE_ENTITY_NAME = "sequence_per_entity_using_entity_name";

    protected String determineSequenceName(Properties params, Dialect dialect) {

        String prefix = params.getProperty(CONFIG_SEQUENCE_PER_ENTITY_PREFIX);
        boolean useEntityName = ConfigurationHelper.getBoolean(USE_ENTITY_NAME, params, false);
        String sequenceName = null;

        if (useEntityName){
            sequenceName = super.determineSequenceName(params, dialect);
        }
        else{
            String entityName = params.getProperty(IdentifierGenerator.JPA_ENTITY_NAME);
            String tableName = params.getProperty("target_table");
            params.setProperty(IdentifierGenerator.JPA_ENTITY_NAME, tableName);
            sequenceName = super.determineSequenceName(params, dialect);
            params.setProperty(IdentifierGenerator.JPA_ENTITY_NAME, entityName);
        }

        return prefix + sequenceName;
    }
}
