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

public class Collection implements Auditable {

    @Null(groups = {New.class})
    @JsonProperty
    private Long id;

    @Null(groups = {New.class})
    @NotNull(groups = {Update.class})
    @JsonProperty
    private String uuid;

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
    @Null(groups={New.class, Update.class})
    private String parentUuid;

    @JsonProperty
    private AuditInfo auditInfo;

    public Collection(
            Long id,
            String uuid,
            String name,
            String description,
            String parentUuid,
            DateTime createdOn,
            User createdBy,
            DateTime lastUpdatedOn,
            User lastUpdatedBy) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.parentUuid = parentUuid;
        auditInfo = new AuditInfo(createdOn,createdBy,lastUpdatedOn,lastUpdatedBy);
    }

    public Collection(
            Long id,
            String uuid,
            String name,
            String description,
            String parentUuid,
            AuditInfo auditInfo) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.parentUuid = parentUuid;
        this.auditInfo = auditInfo;
    }

    public Collection(String name, String description, String parentUuid) {
        this.name = name;
        this.description = description;
        this.parentUuid = parentUuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    @Override
    public AuditInfo getAuditInfo() {
        return auditInfo;
    }
}