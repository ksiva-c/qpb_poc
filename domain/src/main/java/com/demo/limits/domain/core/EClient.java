package com.demo.limits.domain.core;

import com.demo.limits.domain.base.*;

import javax.persistence.*;

@Entity
@Table(name="CLIENT")
@Inheritance(strategy = InheritanceType.JOINED)
public class EClient  extends AbstractIdPersistable<Long> implements EAuditable.Update,EAuditable.Create{


    @Column(name = "NAME", nullable = true, length = 100)
    protected String name;


    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Version
    @Column(name = "VERSION", nullable = true)
    private Integer version;

    private EUpdateInfo updateInfo;
    private ECreateInfo createInfo;

    public EClient(){}


    public EClient(
          
            String name,
            String description,
            
            Integer version) {
       
        this.name = name;
        this.description = description;
       
        this.version = version;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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