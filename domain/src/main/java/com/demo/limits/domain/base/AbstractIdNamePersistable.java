package com.demo.limits.domain.base;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
@MappedSuperclass
public abstract class AbstractIdNamePersistable<T extends Serializable> extends AbstractIdPersistable<T> {

    @Column(name = "NAME", nullable = true, length = 100)
    protected String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected AbstractIdNamePersistable() {
    }

    protected AbstractIdNamePersistable(String name) {
        this.name = name;
    }

    protected AbstractIdNamePersistable(T id) {
        super(id);
    }

    protected AbstractIdNamePersistable(T id, String name) {
        super(id);
        this.name = name;
    }
}
