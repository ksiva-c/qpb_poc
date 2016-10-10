package com.demo.limits.domain.core;

import com.demo.limits.domain.base.*;

import javax.persistence.*;

@Entity
@Table(name="COLLECTION")
@Inheritance(strategy = InheritanceType.JOINED)
public class ECollection  extends AbstractIdNamePersistable<Long> implements EAuditable.Update,EAuditable.Create{

    @Column(name = "COLLECTION_UUID", nullable = false, unique = true)
    private String uuid;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Column(name = "PARENT_UUID", nullable = true)
    private String parentUuid;

    @Version
    @Column(name = "VERSION", nullable = true)
    private Integer version;

    private EUpdateInfo updateInfo;
    private ECreateInfo createInfo;

    public ECollection(){}


    public ECollection(
            String uuid,
            String name,
            String description,
            String parentUuid,
            Integer version) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.parentUuid = parentUuid;
        this.version = version;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public EUpdateInfo getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(EUpdateInfo updateInfo) {
        this.updateInfo = updateInfo;
    }

    public ECreateInfo getCreateInfo() {
        return createInfo;
    }

    public void setCreateInfo(ECreateInfo createInfo) {
        this.createInfo = createInfo;
    }

}
