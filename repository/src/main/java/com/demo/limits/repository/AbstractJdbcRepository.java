package com.demo.limits.repository;

import com.demo.limits.domain.configurators.PersistableEntityListener;
import com.demo.limits.domain.base.*;
import com.nurkiewicz.jdbcrepository.JdbcRepository;
import com.nurkiewicz.jdbcrepository.RowUnmapper;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
public abstract class AbstractJdbcRepository <T extends AbstractIdPersistable<ID>, ID extends Serializable> extends JdbcRepository<T, ID> {

    private static PersistableEntityListener auditEntityListener = new PersistableEntityListener();

    public AbstractJdbcRepository(Class<T> type, String tableIdentifier){
        this(type, tableName(type, tableIdentifier), new DefaultRowMapper<T, ID>(type));
    }

    public AbstractJdbcRepository(Class<T> type, String tableIdentifier, DefaultRowMapper<T, ID> dimensionRowMapper) {
        super(dimensionRowMapper, tableName(type, tableIdentifier));
    }

    private static String tableName(Class clazz, String identifier){
        Table table = (Table) clazz.getAnnotation(Table.class);
        String tableName = table.name().replaceFirst("<X>", identifier);
        return tableName;
    }

    @Override
    protected <S extends T> S postUpdate(S entity) {
        return entity;
    }

    @Override
    protected <S extends T> S postCreate(S entity, Number generatedId) {
        if (generatedId != null){
            entity.setId((ID)generatedId);
        }
        return entity;
    }


    protected static class DefaultRowMapper <S extends AbstractIdPersistable<ID>, ID extends Serializable> implements RowMapper<S>, RowUnmapper<S>{

        private static final String FIELD_UPDATED_ON = "UPDATED_ON";
        private static final String FIELD_UPDATED_BY = "UPDATED_BY";
        private static final String FIELD_CREATED_ON = "CREATED_ON";
        private static final String FIELD_CREATED_BY = "CREATED_BY";
        private static final String FIELD_360D = "IS_360D";
        private static final String FIELD_360O = "IS_360O";

        Class<S> clazz;
        private Map<String, Field> fieldMap = new HashMap<String, Field>();
        private Map<String, Column> columnMap = new HashMap<String, Column>();

        public DefaultRowMapper(Class<S> clazz){
            this.clazz = clazz;
            init();
        }

        private void init(){
            List<Field> allFields = getAllFields(clazz);
            for (Field field: allFields) {
                // checks if MethodInfo annotation is present for the method
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    if (column != null){
                        fieldMap.put(column.name(), field);
                        columnMap.put(column.name(), column);
                        field.setAccessible(true);
                    }
                }
            }
        }

        private List<Field> getAllFields(Class cls){
            List<Field> allFields = new LinkedList<Field>();
            while(cls.equals(Object.class) == false){
                Field[] fields = cls.getDeclaredFields();
                allFields.addAll(Arrays.asList(fields));
                cls = cls.getSuperclass();
            }
            return allFields;
        }

        private S newInstance(){
            try {
                return clazz.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public S mapRow(ResultSet rs, int i) throws SQLException {
            S element = newInstance();
            for(String fieldName: columnMap.keySet()){
                try {
                    fieldMap.get(fieldName).set(element, rs.getObject(fieldName));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            mapRow(rs, i, element);
            return element;
        }

        protected void setFieldValue(S element, ResultSet rs, String fieldName) throws SQLException {
            try {
                fieldMap.get(fieldName).set(element, rs.getObject(fieldName));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Map<String, Object> mapColumns(S element) {
            Map<String, Object> valueMap = new HashMap<String, Object>();
            for(String fieldName: columnMap.keySet()){
                try {
                    valueMap.put(fieldName, fieldMap.get(fieldName).get(element));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            mapColumns(element, valueMap);
            return valueMap;
        }

        protected void mapRow(ResultSet rs, int i, S element) throws SQLException {
            ID id = (ID)rs.getObject(getIdFieldName());
            if (id instanceof BigDecimal){
                Object longValue = ((BigDecimal)id).longValue();
                element.setId((ID)longValue);
            }
            else{
                element.setId(id);
            }

            //element.setId((ID)rs.getObject(getIdFieldName()));
            if (element instanceof EMultiApp){
                ((EMultiApp)element).setAppInfo(new EAppInfo().setIs360D(rs.getBoolean(FIELD_360D)).setIs360O(rs.getBoolean(FIELD_360O)));
            }

            if (element instanceof EAuditable.Create){
                ((EAuditable.Create)element).setCreateInfo(new ECreateInfo(new DateTime(rs.getDate(FIELD_CREATED_ON)), rs.getString(FIELD_CREATED_BY)));
            }

            if (element instanceof EAuditable.Update){
                ((EAuditable.Update)element).setUpdateInfo(new EUpdateInfo(new DateTime(rs.getDate(FIELD_UPDATED_ON)), rs.getString(FIELD_UPDATED_BY)));
            }
        }

        protected void mapColumns(S element, Map<String, Object> valueMap) {
            if (element.isNew()){
                auditEntityListener.prePersist(element);
            }
            else{
                valueMap.put(getIdFieldName(), (ID)element.getId());
                auditEntityListener.preUpdate(element);
            }

            if (element instanceof EMultiApp){
                EAppInfo appInfo = ((EMultiApp)element).getAppInfo();
                valueMap.put(FIELD_360D, appInfo.getIs360D());
                valueMap.put(FIELD_360O, appInfo.getIs360O());
            }

            if (element instanceof EAuditable.Create){
                ECreateInfo createInfo = ((EAuditable.Create)element).getCreateInfo();
                valueMap.put(FIELD_CREATED_ON, createInfo.getCreatedOn());
                valueMap.put(FIELD_CREATED_BY, createInfo.getCreatedBy());
            }

            if (element instanceof EAuditable.Create){
                EUpdateInfo updateInfo = ((EAuditable.Update)element).getUpdateInfo();
                valueMap.put(FIELD_UPDATED_ON, updateInfo.getLastUpdatedOn());
                valueMap.put(FIELD_UPDATED_BY, updateInfo.getLastUpdatedBy());
            }
        }

        protected String getIdFieldName(){
            return "ID";
        }

        protected Long getLongAsLongOrNull(ResultSet rs, String columnName) throws SQLException {
            Long lValue = rs.getLong(columnName);
            return rs.wasNull()? null : lValue;
        }

    }

}
