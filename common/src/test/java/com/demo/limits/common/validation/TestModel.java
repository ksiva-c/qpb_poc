package com.demo.limits.common.validation;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by lnv.
 */
public class TestModel {
    private Long id;
    private String uuid;
    @NotNull
    @NotBlank
    @Size(min = 1 , max = 1000)
    private String name;
    @Size(min = 1 , max = 1000)
    private String description;

    public TestModel() {
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
}
