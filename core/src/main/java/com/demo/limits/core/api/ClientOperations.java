package com.demo.limits.core.api;

import com.demo.limits.core.exception.CollectionValidationException;
import com.demo.limits.common.validation.ValidateException;
import com.demo.limits.common.validation.ValidationGroup;
import com.demo.limits.model.core.Client;
import com.demo.limits.model.common.validation.group.New;
import com.demo.limits.model.common.validation.group.Update;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;

@Validated
@ValidateException(CollectionValidationException.class)
public interface ClientOperations {
    List<Client> Clients();
    Client getClientById(Long id);
    @ValidationGroup({New.class}) Client create(@Valid Client Client);
    void delete(Long ClientId);
    @ValidationGroup({Default.class, Update.class}) Client update(@Valid Client Client);
}
