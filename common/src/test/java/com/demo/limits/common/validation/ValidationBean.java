package com.demo.limits.common.validation;

import com.demo.limits.model.common.validation.group.New;
import com.demo.limits.model.common.validation.group.Name;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * User: lnv
 * Date: 
 */
public class ValidationBean {

    //Id shall be null when creating, for all other cases it shall never be null
    @Null(groups = {New.class})
    @NotNull(groups = {Name.class})
    private Long id;

    //Name shall never be null and shall have size (1, 100)
    @NotNull(groups = {Name.class})
    @Size(min = 1, max = 100, groups = {Name.class})
    private String name;

    //Name shall never be null and shall have size (1, 100)
    @NotNull
    @Size(min = 1, max = 100)
    private String description;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
