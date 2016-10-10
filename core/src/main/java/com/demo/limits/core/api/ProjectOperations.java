package com.demo.limits.core.api;

import com.demo.limits.core.exception.CollectionValidationException;
import com.demo.limits.common.validation.ValidateException;
import com.demo.limits.common.validation.ValidationGroup;
import com.demo.limits.model.core.Project;
import com.demo.limits.model.common.validation.group.New;
import com.demo.limits.model.common.validation.group.Update;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;

@Validated
@ValidateException(CollectionValidationException.class)
public interface ProjectOperations {
    List<Project> projects();
    Project getProjectByUuid(String id);
    @ValidationGroup({New.class}) Project create(@Valid Project project);
    void delete(String projectUuid);
    @ValidationGroup({Default.class, Update.class}) Project update(@Valid Project project);
    List<Project> getProjectByScenarioId(Long scenarioId);
    Project get(String uuid, boolean forUpdate);
}
