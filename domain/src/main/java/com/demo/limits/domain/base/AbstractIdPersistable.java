package com.demo.limits.domain.base;

import com.demo.limits.domain.configurators.SequenceGenerator;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
@MappedSuperclass
public abstract class AbstractIdPersistable<T extends Serializable>
        extends AbstractPersistable implements PersistableOperation<T> {

    @Id
    @GeneratedValue(generator="limitsSeqGen")
    @GenericGenerator(name="limitsSeqGen",strategy="com.demo.limits.domain.configurators.SequenceGenerator",
            parameters={
                    @Parameter(name = SequenceGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY, value = "true"),
                    @Parameter(name = SequenceGenerator.CONFIG_SEQUENCE_PER_ENTITY_SUFFIX, value = ""),
                    @Parameter(name = SequenceGenerator.CONFIG_SEQUENCE_PER_ENTITY_PREFIX, value = "SEQ_"),
                    @Parameter(name = SequenceGenerator.USE_ENTITY_NAME, value = "false")
            })
    @Column(name = "ID", nullable = false)
    protected T id;

    protected AbstractIdPersistable() {
    }

    protected AbstractIdPersistable(T id) {
        this.id = id;
    }

    @Override
    public T getId() {
        return id;
    }

    @Override
    public AbstractIdPersistable<T> setId(T id) {
        this.id = id;
        return this;
    }


    @Override
    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        AbstractIdPersistable<T> pk = (AbstractIdPersistable) obj;
        return Objects.equals(this.id, pk.id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", id).
                toString();
    }

}
