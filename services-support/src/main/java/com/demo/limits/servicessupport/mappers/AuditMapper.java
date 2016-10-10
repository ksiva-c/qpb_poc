package com.demo.limits.servicessupport.mappers;

import com.demo.limits.common.validation.MapperException;
import com.demo.limits.common.validation.ValidateException;
import com.demo.limits.domain.base.EAuditable;
import com.demo.limits.domain.base.ECreateInfo;
import com.demo.limits.domain.base.EUpdateInfo;
import com.demo.limits.idm.api.IdentityOperations;
import com.demo.limits.model.common.AuditInfo;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

/**
 * User: lnv
 */
@Component
@ValidateException(MapperException.class)
@Validated
public class AuditMapper {

    @Inject
    IdentityOperations identityOperations;

    /**
     * Maps the domain audit info to model audit info. Gets the user from the identity service.
     * @param createInfo
     * @param updateInfo
     * @return Return null if createInfo and updateInfo both are null.
     */
    public AuditInfo map(@NotNull ECreateInfo createInfo, @NotNull EUpdateInfo updateInfo) {
        return _map(createInfo, updateInfo);
    }

    /**
     * Maps the domain create info to model audit info. The model audit only contains create info.
     * @param createInfo
     * @return Returns null if createInfo is passed null.
     */
    public AuditInfo map(@NotNull ECreateInfo createInfo) {
        return _map(createInfo, null);
    }

    /**
     * Maps the domain update info to model audit info. The model audit only contains update info.
     * @param updateInfo
     * @return Returns null if updateInfo is passed null.
     */
    public AuditInfo map(@NotNull EUpdateInfo updateInfo) {
        return _map(null, updateInfo);
    }

    private AuditInfo _map(ECreateInfo createInfo, EUpdateInfo updateInfo) {
        if (createInfo == null && updateInfo == null) return null;
        AuditInfo auditInfo = new AuditInfo();
        if (createInfo != null){
            auditInfo.setCreatedOn(createInfo.getCreatedOn());
            auditInfo.setCreatedBy(identityOperations.getUser(createInfo.getCreatedBy()));
        }

        if (updateInfo != null){
            auditInfo.setLastUpdatedOn(updateInfo.getLastUpdatedOn());
            auditInfo.setLastUpdatedBy(identityOperations.getUser(updateInfo.getLastUpdatedBy()));
        }

        return auditInfo;
    }

    public AuditInfo map(AuditInfo auditInfo, @NotNull EAuditable.UpdateDate updateDate){
        if (auditInfo == null) auditInfo = new AuditInfo();
        auditInfo.setLastUpdatedOn(updateDate.getLastUpdatedOn());
        return auditInfo;
    }

    public AuditInfo map(AuditInfo auditInfo, @NotNull EAuditable.UpdateUser updateUser){
        if (auditInfo == null) auditInfo = new AuditInfo();
        auditInfo.setLastUpdatedBy(identityOperations.getUser(updateUser.getLastUpdatedBy()));
        return auditInfo;
    }

    public AuditInfo map(AuditInfo auditInfo, @NotNull EAuditable.CreateDate createDate){
        if (auditInfo == null) auditInfo = new AuditInfo();
        auditInfo.setCreatedOn(createDate.getCreatedOn());
        return auditInfo;
    }

    public AuditInfo map(AuditInfo auditInfo, @NotNull EAuditable.CreateUser createUser){
        if (auditInfo == null) auditInfo = new AuditInfo();
        auditInfo.setCreatedBy(identityOperations.getUser(createUser.getCreatedBy()));
        return auditInfo;
    }

}

