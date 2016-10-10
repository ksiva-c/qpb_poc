package com.demo.limits.rest.services.core;

import com.demo.limits.core.api.ProjectOperations;
import com.demo.limits.model.core.Project;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RestController("ProjectResourceV1")
@RequestMapping(
        value = "v1/project"
)
@Api(value="Project", description="Manage Projects")
public class ProjectResource {

    @Named("ovProjectTemplate")
    @Inject
    ProjectOperations projectOperations;

    private static final Logger logger = LoggerFactory.getLogger(ProjectResource.class.getName());

    @ApiOperation(
            value = "Create Project",
            notes = "360 Projects",
            response = Project.class,
            consumes = "application/json",
            produces = "application/json"
    )
    @RequestMapping(value = "", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Project create(@RequestBody Project project){
        return projectOperations.create(project);
    }

    @Transactional
    @ApiOperation(
            value = "Delete Project",
            notes = "360 Projects",
            response = Project.class,
            consumes = "application/json",
            produces = "application/json"
    )
    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    public void delete(@PathVariable ("uuid") String projectUuid){
        projectOperations.delete(projectUuid);
    }

    @ApiOperation(
            value = "Update Project",
            notes = "360 Projects",
            response = Project.class,
            consumes = "application/json",
            produces = "application/json"
    )
    @RequestMapping(value = "", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Project update(@RequestBody Project project){
        return projectOperations.update(project);
    }

    @ApiOperation(
            value = "Get All Projects",
            notes = "360 Projects",
            response = Project.class,
            consumes = "application/json",
            produces = "application/json",
            responseContainer = "list"
    )
    @RequestMapping(
            value = "", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> list(@RequestParam (value = "scenarioId", required = false) Long scenarioId) {
        if(null != scenarioId)
            return projectOperations.getProjectByScenarioId(scenarioId);
        else
            return projectOperations.projects();
    }





}

