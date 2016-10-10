package com.demo.limits.domain.configurators;

import com.demo.limits.common.context.ContextHolder;
import com.demo.limits.domain.base.AbstractPersistable;
import com.demo.limits.domain.base.EAuditable;
import com.demo.limits.domain.base.ECreateInfo;
import com.demo.limits.domain.base.EUpdateInfo;
import org.joda.time.DateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * User: lnv
 * Date: 
 * Time: 
 */
public class PersistableEntityListener {

    @PrePersist
    public void prePersist(AbstractPersistable e) {
        if (e instanceof EAuditable.Create){
            EAuditable.Create create = (EAuditable.Create) e;
            String createdBy = ContextHolder.get().getSessionContext().getUser().getUuid();
            create.setCreateInfo(new ECreateInfo(new DateTime(),createdBy));
        }
        if (e instanceof EAuditable.CreateDate){
            EAuditable.CreateDate createDate = (EAuditable.CreateDate) e;
            createDate.setCreatedOn(new DateTime());
        }
        if (e instanceof EAuditable.CreateUser){
            EAuditable.CreateUser createUser = (EAuditable.CreateUser) e;
            String createdBy = ContextHolder.get().getSessionContext().getUser().getUuid();
            createUser.setCreatedBy(createdBy);
        }
        preUpdate(e);
    }

    @PreUpdate
    public void preUpdate(AbstractPersistable e) {
        if (e instanceof EAuditable.Update){
            EAuditable.Update update = (EAuditable.Update) e;
            String updatedBy = ContextHolder.get().getSessionContext().getUser().getUuid();
            update.setUpdateInfo(new EUpdateInfo(new DateTime(),updatedBy));
        }
        if (e instanceof EAuditable.UpdateDate){
            EAuditable.UpdateDate updateDate = (EAuditable.UpdateDate) e;
            updateDate.setLastUpdatedOn(new DateTime());
        }
        if (e instanceof EAuditable.UpdateUser){
            EAuditable.UpdateUser updateUser = (EAuditable.UpdateUser) e;
            String updatedBy = ContextHolder.get().getSessionContext().getUser().getUuid();
            updateUser.setLastUpdatedBy(updatedBy);
        }
    }
}