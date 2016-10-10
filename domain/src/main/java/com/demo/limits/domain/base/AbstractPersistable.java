package com.demo.limits.domain.base;

import com.demo.limits.domain.configurators.PersistableEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Collections;
import java.util.Map;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
@MappedSuperclass
@EntityListeners(value = { PersistableEntityListener.class })
public abstract class AbstractPersistable {
    public enum ConstraintType{UniqueName, Others};

    /**
     * Returns the constraint name to its type mapping.
     * @param name
     * @return
     */
    public ConstraintType getConstraintType(String name){
        return null;
    }
}
