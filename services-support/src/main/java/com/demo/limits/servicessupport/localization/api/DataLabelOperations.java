package com.demo.limits.servicessupport.localization.api;

import com.demo.limits.domain.common.EDataLabel;
import com.demo.limits.model.localization.DataLabel;

/**
 * User: lnv
 */
public interface DataLabelOperations {

    public DataLabel get(String objectName, Long dataId);
    public String getLabel(String objectName, Long dataId, String defaultValue);
}
