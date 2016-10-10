package com.demo.limits.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

public class AuditInfo {
    @JsonProperty
    DateTime createdOn;
    @JsonProperty
    User createdBy;
    @JsonProperty
    DateTime lastUpdatedOn;
    @JsonProperty
    User lastUpdatedBy;

    @JsonCreator
    public AuditInfo() {
    }

    @JsonCreator
    public AuditInfo(
            @JsonProperty("createdOn") DateTime createdOn,
            @JsonProperty("createdBy") User createdBy,
            @JsonProperty("lastUpdatedOn") DateTime lastUpdatedOn,
            @JsonProperty("lastUpdatedBy") User lastUpdatedBy) {
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.lastUpdatedOn = lastUpdatedOn;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(User lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public DateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }

    public DateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(DateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    @Override
    public String toString() {
        return "AuditInfo{" +
                "createdOn=" + createdOn +
                ", createdBy=" + createdBy +
                ", lastUpdatedOn=" + lastUpdatedOn +
                ", lastUpdatedBy=" + lastUpdatedBy +
                '}';
    }
}
