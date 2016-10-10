package com.demo.limits.model.localization;

import com.demo.limits.model.common.AuditInfo;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
public class DataLabel {
    private String objectName;
    private Long dataId;
    private Long localeId;

    private String label;
    private String sysLabel;

    private AuditInfo auditInfo;

    public DataLabel(String objectName, Long dataId, Long localeId, String label, String sysLabel, AuditInfo auditInfo) {
        this.objectName = objectName;
        this.dataId = dataId;
        this.localeId = localeId;
        this.label = label;
        this.sysLabel = sysLabel;
        this.auditInfo = auditInfo;
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

    public String getLabel() {
        return label;
    }

    public String getSysLabel() {
        return sysLabel;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public DataLabel setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public DataLabel setDataId(Long dataId) {
        this.dataId = dataId;
        return this;
    }

    public DataLabel setLocaleId(Long localeId) {
        this.localeId = localeId;
        return this;
    }

    public DataLabel setLabel(String label) {
        this.label = label;
        return this;
    }

    public DataLabel setSysLabel(String sysLabel) {
        this.sysLabel = sysLabel;
        return this;
    }
}
