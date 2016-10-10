package com.demo.limits.repository.utils;

import com.demo.limits.common.validation.Assertions;
import com.demo.limits.common.validation.BaseException;
import com.demo.limits.domain.base.AbstractIdNamePersistable;
import com.demo.limits.domain.base.AbstractPersistable;
import com.demo.limits.domain.base.EAuditable;
import com.demo.limits.repository.exceptions.EntityDuplicateException;
import com.demo.limits.repository.exceptions.EntityNotFoundException;
import com.demo.limits.repository.exceptions.IntegrityViolationException;
import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.DateTime;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * User: lnv
 * Date: 
 * Time: 
 */

@Component
public class RepositoryHelper {

    @Inject
    Assertions assertions;

    public <T extends AbstractPersistable, ID extends Serializable> T findOneNotNull(CrudRepository<T, ID> rep, ID key,
                                                                        Class<? extends AbstractPersistable> T){
        return findOneNotNull(rep, key, T, null, null, null);
    }

    public <T extends AbstractPersistable, ID extends Serializable> T findOneNotNull(CrudRepository<T, ID> rep,
                                                                                     ID key,
                                                                                     Class<? extends AbstractPersistable> T,
                                                                                     Class<? extends BaseException> exClass){
        return findOneNotNull(rep, key, T, exClass, null, null);
    }
    public <T extends AbstractPersistable, ID extends Serializable> T findOneNotNull(CrudRepository<T, ID> rep,
                                                                                     ID key,
                                                                                     Class<? extends AbstractPersistable> T,
                                                                                     Class<? extends BaseException> exClass,
                                                                                     String code){
        return findOneNotNull(rep, key, T, exClass, code, null);
    }

    public <T extends AbstractPersistable, ID extends Serializable> T findOneNotNull(CrudRepository<T, ID> rep,
                                                                        ID key,
                                                                        Class<? extends AbstractPersistable> T,
                                                                        Class<? extends BaseException> exClass,
                                                                        String code,
                                                                        Object[] args){
        T item = rep.findOne(key);
        if (item == null) {
            if (exClass == null){
                exClass = EntityNotFoundException.class;
            }
            if (args == null){
                args = new Object[]{
                        EntityNotFoundException.KEY_TYPE, T.getSimpleName(),
                        EntityNotFoundException.KEY_ID, key
                };
            }
            if (code == null){
                throw assertions.createEx(exClass,args);
            }
            else{
                throw assertions.createEx(exClass, code, args);
            }
        }
        return item;
    }

    public <T extends Object, ID extends Serializable> T touch(CrudRepository<T, ID> rep, EAuditable.Update entity,
                                                               Class<? extends EAuditable.Update> T){
        entity.getUpdateInfo().setLastUpdatedOn(DateTime.now());
        return rep.save((T)entity);
    }

    public <T extends AbstractPersistable, ID extends Serializable> T saveAndFlush(JpaRepository<T, ID> rep, T entity){
        try{
            return rep.saveAndFlush(entity);
        }
        catch(DataIntegrityViolationException dive){
            if (dive.getCause() instanceof ConstraintViolationException){
                ConstraintViolationException cve = (ConstraintViolationException) dive.getCause();
                final String constraintName = cve.getConstraintName().substring(cve.getConstraintName().indexOf(".") + 1);
                AbstractPersistable.ConstraintType constraintType = entity.getConstraintType(constraintName);
                if (constraintType == AbstractPersistable.ConstraintType.UniqueName
                        && entity instanceof AbstractIdNamePersistable){
                    //Its a unique name constraint failure
                    //Entity has the name field
                    throw assertions.wrap(dive.getCause(),
                            EntityDuplicateException.class,
                            EntityDuplicateException.CODE + "." + constraintName,
                            new Object[]{
                                    EntityDuplicateException.KEY_TYPE, entity.getClass().getSimpleName(),
                                    EntityDuplicateException.KEY_NAME, ((AbstractIdNamePersistable)entity).getName()
                            });
                }
                else{
                    //Its any other constraint failure or entity does not name field
                    throw assertions.wrap(dive.getCause(), IntegrityViolationException.class,
                            constraintName,
                            new Object[]{
                                    "constraint",  constraintName
                            });
                }
            }
            else{
                //If cause is not known throw it with internal error code
                throw assertions.wrap(dive, IntegrityViolationException.class, "INTERNAL_ERROR", new Object[]{"cause", dive.getMessage()});
            }
        }
    }
}
