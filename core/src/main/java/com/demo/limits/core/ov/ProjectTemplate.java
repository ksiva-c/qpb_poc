package com.demo.limits.core.ov;

import com.demo.limits.core.api.ProjectOperations;

import com.demo.limits.core.exception.CollectionCompositeDeleteException;
import com.demo.limits.core.exception.CollectionNotFoundException;

import com.demo.limits.common.uuid.IdGenerator;
import com.demo.limits.common.validation.Assertions;
import com.demo.limits.common.validation.ValidationGroup;
import com.demo.limits.domain.core.EProject;
import com.demo.limits.domain.core.ProjectItem;
import com.demo.limits.model.core.Project;
import com.demo.limits.model.common.validation.group.New;
import com.demo.limits.model.common.validation.group.Update;
import com.demo.limits.repository.core.ProjectItemRepository;
import com.demo.limits.repository.core.ProjectRepository;
import com.demo.limits.repository.exceptions.EntityDuplicateException;
import com.demo.limits.servicessupport.mappers.AuditMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;


@Named("ovProjectTemplate")
@Transactional
public class ProjectTemplate implements ProjectOperations {

    @Inject
    ProjectRepository projectRepository;

    @Inject
    ProjectItemRepository projectItemRepository;

    @Inject
    @Named("jugUuidGenerator")
    IdGenerator idGenerator;

    @Inject
    Assertions Assert;

    @Inject
    AuditMapper auditMapper;


    @Override
    public List<Project> projects() {
        List<Project> projects = new ArrayList<>();
        for(EProject eProject : projectRepository.findAll()){
            projects.add(toValue(eProject));
        }
        return projects;
    }

    @Override
    public Project getProjectByUuid(String uuid) {
        EProject eProject = projectRepository.findByUuid(uuid);
        Assert.notNull(eProject,
                CollectionNotFoundException.class,
                new Object[]{"type", Project.class.getSimpleName(), "id", uuid});

        return toValue(eProject);
    }

    @Override
    @ValidationGroup({New.class})
    public @NotNull Project create(@Valid Project project) {


        String uuid = idGenerator.id(project.getName());
        EProject eProject = new EProject(
                uuid,
                project.getName().trim(),
                project.getDescription(),
                project.getParentUuid(),
                0
                
        );
        eProject = projectRepository.saveAndFlush(eProject);
        return toValue(eProject);
    }

    @Override
    @ValidationGroup({Default.class, Update.class})
    public Project update(@Valid Project project) {

        String uuid = project.getUuid();
        EProject eProject = projectRepository.findByUuid(uuid);

        Assert.notNull(eProject,
                CollectionNotFoundException.class,
                new Object[]{"type", Project.class.getSimpleName(), "id", uuid}
        );


        eProject.setName(project.getName().trim());
        eProject.setDescription(project.getDescription());
        eProject = projectRepository.saveAndFlush(eProject);
        return toValue(eProject);
    }

    @Override
    public void delete(String projectUuid) {

        EProject eProject = projectRepository.findByUuid(projectUuid);

        Assert.notNull(eProject,
                CollectionNotFoundException.class,
                new Object[]{"type", Project.class.getSimpleName(), "id", projectUuid}
        );


        if((projectItemRepository.findAllByCollectionUuId(projectUuid)).size() == 0){
            projectRepository.delete(eProject.getId());
        }else{
            Assert.throwEx(
                    CollectionCompositeDeleteException.class,
                    new Object[]{"type", Project.class.getSimpleName(), "id", projectUuid}
            );
        }
    }

    @Override
    public List<Project> getProjectByScenarioId(Long scenarioId){
        List<Project> projects = new ArrayList<>();
        List<EProject> eProjects = projectItemRepository.findProjectByScenarioId(scenarioId);
        if(eProjects.isEmpty()) {
            Assert.throwEx(
                    CollectionNotFoundException.class,"COLLECTION.PROJECT_NOT_FOUND_FOR_SCENARIO",
                    new Object[]{ "scenarioId", scenarioId}
            );
        }
        for(EProject eProject : eProjects){
            projects.add(toValue(eProject));
        }
        return projects;
    }

    @Override
    public Project get(String uuid, boolean forUpdate) {
        EProject eProject = projectRepository.findByUuid(uuid);

        Assert.notNull(eProject,
                CollectionNotFoundException.class,
                new Object[]{"type", Project.class.getSimpleName(), "id", uuid}
        );
        return toValue(eProject);
    }

    private Project toValue(EProject eProject){
        return new Project(
                eProject.getId(),
                eProject.getUuid(),
                eProject.getName(),
                eProject.getDescription(),
                eProject.getParentUuid(),
               
                auditMapper.map(eProject.getCreateInfo(), eProject.getUpdateInfo())
            );
    }

}
