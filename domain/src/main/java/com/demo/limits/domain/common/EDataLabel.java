package com.demo.limits.domain.common;

import com.demo.limits.domain.base.AbstractPersistable;
import com.demo.limits.domain.base.EAuditable;
import com.demo.limits.domain.base.EUpdateInfo;
import com.demo.limits.domain.base.PersistableOperation;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * User: lnv
 * Date: 
 * Time: 
 */

@Entity
@Table(name="DATA_LABEL")
@Cacheable
public class EDataLabel extends AbstractPersistable implements PersistableOperation<EDataLabel.PK>, EAuditable.Update
{

    @EmbeddedId
    private PK id;

    @Column(name = "LABEL", nullable = true)
    private String label;

    @Column(name = "SYS_LABEL", nullable = true)
    private String sysLabel;

    @Embedded
    private EUpdateInfo updateInfo;

    public String getLabel() {
        return label;
    }

    public EDataLabel setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getSysLabel() {
        return sysLabel;
    }

    public EDataLabel setSysLabel(String sysLabel) {
        this.sysLabel = sysLabel;
        return this;
    }

    @Override
    public PersistableOperation<PK> setId(PK id) {
        this.id = id;
        return this;
    }

    @Override
    public PK getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public EUpdateInfo getUpdateInfo() {
        return updateInfo;
    }

    @Override
    public void setUpdateInfo(EUpdateInfo updateInfo) {
        this.updateInfo = updateInfo;
    }

    public static class PK implements Serializable {

        @Column(name = "OBJECT_NAME", nullable = false)
        private String objectName;

        @Column(name = "DATA_ID", nullable = false)
        private Long dataId;

        @Column(name = "LOCALE_ID", nullable = false)
        private Long localeId;

        public PK() {
        }

        public PK(String objectName, Long dataId, Long localeId) {
            this.objectName = objectName;
            this.dataId = dataId;
            this.localeId = localeId;
        }

        public String getObjectName() {
            return objectName;
        }

        public Long getDataId() {
            return dataId;
        }

        public Long getLocaleId() {
            return localeId;
        }

        public PK setObjectName(String objectName) {
            this.objectName = objectName;
            return this;
        }

        public PK setDataId(Long dataId) {
            this.dataId = dataId;
            return this;
        }

        public PK setLocaleId(Long localeId) {
            this.localeId = localeId;
            return this;
        }

        @Override
        public int hashCode() {
            return Objects.hash(objectName, dataId, localeId);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            PK pk = (PK) obj;
            return Objects.equals(this.objectName, pk.objectName) && Objects.equals(this.dataId, pk.dataId) && Objects.equals(this.localeId, pk.localeId);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).
                    append("objectName", objectName).
                    append("dataId", dataId).
                    append("localeId", localeId).
                    toString();
        }
    }
}
