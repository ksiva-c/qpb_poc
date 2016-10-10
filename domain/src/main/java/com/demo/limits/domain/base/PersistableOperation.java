package com.demo.limits.domain.base;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
public interface PersistableOperation<ID extends Serializable> extends Persistable<ID> {
    public PersistableOperation<ID> setId(ID id);
}
