package com.demo.limits.domain.base;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityListeners;
import java.util.Date;

/**
 * User: lnv
 * Date: 
 * Time: 
 */

@EntityListeners(AuditingEntityListener.class)
@Embeddable
public class EUpdateInfo {

    public EUpdateInfo() {
    }

    public EUpdateInfo(DateTime lastUpdatedOn, String lastUpdatedBy) {
        this.lastUpdatedOn = lastUpdatedOn;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @LastModifiedDate
    @Column(name = "UPDATED_ON", nullable = true)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime",
            parameters = {@Parameter(name="jadira.usertype.databaseZone",value="jvm"),@Parameter(name="jadira.usertype.javaZone",value = "jvm")}
    )
    private DateTime lastUpdatedOn;

    @LastModifiedBy
    @Column(name = "UPDATED_BY", nullable = true)
    private String lastUpdatedBy;

    public DateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public EUpdateInfo setLastUpdatedOn(DateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
        return this;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public EUpdateInfo setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public String toString(){
        return "Updated by - " + lastUpdatedBy + " on " + lastUpdatedOn;
    }

}
