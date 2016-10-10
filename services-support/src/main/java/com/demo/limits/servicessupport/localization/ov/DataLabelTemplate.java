package com.demo.limits.servicessupport.localization.ov;

import com.demo.limits.common.context.ContextHolder;
import com.demo.limits.common.validation.Assertions;
import com.demo.limits.domain.common.EDataLabel;
import com.demo.limits.model.localization.DataLabel;
import com.demo.limits.repository.common.DataLabelRepository;
import com.demo.limits.repository.exceptions.EntityNotFoundException;
import com.demo.limits.servicessupport.localization.api.DataLabelOperations;
import com.demo.limits.servicessupport.mappers.AuditMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * User: lnv
 */
@Named("ovDataLabelTemplate")
public class DataLabelTemplate implements DataLabelOperations{

    @Inject
    DataLabelRepository dataLabelRepository;

    @Inject
    AuditMapper auditMapper;

    @Inject
    Assertions assertions;

    @Inject
    CacheInitializer cacheInitializer;

    @Override
    @Cacheable(value="data-labels", key="'obj' + #objectName + '_' + #dataId")
    public DataLabel get(String objectName, Long dataId) {
        Long localeId = ContextHolder.get().getRequestContext().getLocaleId();
        EDataLabel.PK id = new EDataLabel.PK(objectName, dataId, localeId);
        EDataLabel dataLabel = dataLabelRepository.findOne(id);

        assertions.notNull(dataLabel, EntityNotFoundException.class, new Object[]{
                EntityNotFoundException.KEY_ID, id,
                EntityNotFoundException.KEY_TYPE, EDataLabel.class.getSimpleName()
        });

        return new DataLabel(objectName, dataId, localeId,
                            dataLabel.getLabel(), dataLabel.getSysLabel(),
                            auditMapper.map(dataLabel.getUpdateInfo()));
    }

    @Override
    @Cacheable(value="data-labels", key="'lbl' + #objectName + '_' + #dataId")
    public String getLabel(String objectName, Long dataId, String defaultValue){
        Long localeId = ContextHolder.get().getRequestContext().getLocaleId();
        EDataLabel.PK id = new EDataLabel.PK(objectName, dataId, localeId);
        EDataLabel dataLabel = dataLabelRepository.findOne(id);
        if (dataLabel != null){
            return dataLabel.getLabel();
        }
        return defaultValue;
    }

    @PostConstruct
    public void postConstruct(){
        if (cacheInitializer.isEnabled()){
            for (EDataLabel dataLabel : dataLabelRepository.findAll()) {
                cacheInitializer.cacheLabel(dataLabel.getId().getObjectName(),
                        dataLabel.getId().getDataId(), dataLabel.getLabel());
            }
        }
    }

    @Component
    static class CacheInitializer {
        @CachePut(value="data-labels", key="'lbl' + #objectName + '_' + #dataId")
        public String cacheLabel(String objectName, Long dataId, String value){
            return value;
        }

        @Value(value = "${cache.preload.data-labels.enabled:true}")
        private String enabled;

        public Boolean isEnabled() {
            return Boolean.parseBoolean(enabled);
        }
    }
}
