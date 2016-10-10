package com.demo.limits.model.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.demo.limits.model.common.validation.group.New;
import com.demo.limits.model.common.validation.group.Update;
import com.demo.limits.model.common.AuditInfo;
import com.demo.limits.model.common.Auditable;
import com.demo.limits.model.common.User;
import org.joda.time.DateTime;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
//import javax.validation.groups.Update;

public class Client implements Auditable {

    @Null(groups = {New.class})
    @JsonProperty
    private Long id;


    @JsonProperty
    @NotBlank(groups={New.class, Update.class})
    @Size(min = 1 , max = 1000, groups={New.class, Update.class})
    @Pattern(regexp = "[^\"\\*/:<>?^|]*", groups={New.class, Update.class})
    private String name;

    @JsonProperty
    @NotNull(groups={New.class, Update.class})
    @Size(min = 0 , max = 1000, groups={New.class, Update.class})
    private String description;



    @JsonProperty
    private AuditInfo auditInfo;

    public Client(
            Long id,
           
            String name,
            String description,
            
            DateTime createdOn,
            User createdBy,
            DateTime lastUpdatedOn,
            User lastUpdatedBy) {
        this.id = id;
     
        this.name = name;
        this.description = description;
       
        auditInfo = new AuditInfo(createdOn,createdBy,lastUpdatedOn,lastUpdatedBy);
    }

    public Client(
            Long id,
          
            String name,
            String description,
           
            AuditInfo auditInfo) {
        this.id = id;
        
        this.name = name;
        this.description = description;
      
        this.auditInfo = auditInfo;
    }

    public Client(String name, String description) {
        this.name = name;
        this.description = description;
      
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    @Override
    public AuditInfo getAuditInfo() {
        return auditInfo;
    }
}