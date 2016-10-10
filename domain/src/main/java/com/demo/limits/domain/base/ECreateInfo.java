package com.demo.limits.domain.base;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

/**
 * User: lnv
 * Date: 
 * Time: 
 */

@Embeddable
public class ECreateInfo {

    public ECreateInfo() {
    }

    public ECreateInfo(DateTime createdOn, String createdBy) {
        this.createdOn = createdOn;
        this.createdBy = createdBy;
    }

    @CreatedDate
    @Column(name = "CREATED_ON", nullable = true)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime",
            parameters = {@Parameter(name="jadira.usertype.databaseZone",value="jvm"),@Parameter(name="jadira.usertype.javaZone",value = "jvm")}
    )
    private DateTime createdOn;

    @CreatedBy
    @Column(name = "CREATED_BY", nullable = true)
    private String createdBy;

    public DateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String toString(){
        return "Created by - " + createdBy + " on " + createdOn;
    }

}
