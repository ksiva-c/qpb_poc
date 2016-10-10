package com.demo.limits.common.validation;

import com.demo.limits.model.common.validation.group.New;
import com.demo.limits.model.common.validation.group.Name;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.groups.Default;

/**
 * User: lnv
 * Date: 
 * Time: 
 */

@Component
@Validated
@ValidateException(TestValidationException.class)
public class ValidationService {

    //Validate all constraints in default and new group
    @ValidationGroup({New.class, Default.class})
    public void create(@Valid ValidationBean bean){
    }

    //Validate all constraints in default group
    @ValidationGroup({Default.class})
    public void update(@Valid ValidationBean bean){

    }

    //Validate all constraints in rename group
    @ValidationGroup({Name.class})
    public void rename(@Valid ValidationBean bean){

    }



}
