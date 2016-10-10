package com.demo.limits.model.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.demo.limits.model.common.AuditInfo;
import com.demo.limits.model.common.User;
import com.demo.limits.model.common.validation.group.New;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;


/**
 * Created by lnv.
 */
@JsonPropertyOrder({
        "id",
        "uuid",
        "name" ,
        "description",
        "parentUuid",
        "auditInfo"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project extends Collection {

    public Project(
            Long id,
            String uuid,
            String name,
            String description,
            String parentId,
            DateTime createdOn,
            User createdBy,
            DateTime lastUpdatedOn,
            User lastUpdatedBy
    
    ) {
        super( id, uuid,  name, description,parentId, createdOn, createdBy, lastUpdatedOn, lastUpdatedBy);
 
    }

    public Project(
            Long id,
            String uuid,
            String name,
            String description,
            String parentId,

            AuditInfo auditInfo

    ) {
        super( id, uuid,  name, description,parentId, auditInfo);
 
    }

    @JsonCreator
    public Project(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("parentId") String parentId,
            @JsonProperty("isMaster") Boolean isMaster) {
        super(name, description, parentId);

    }

}
