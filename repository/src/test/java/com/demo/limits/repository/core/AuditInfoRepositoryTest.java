package com.demo.limits.repository.core;

import com.demo.limits.common.context.ContextHolder;
import com.demo.limits.domain.core.EProject;
import com.demo.limits.repository.base.AbstractTransactionalRepositoryTest;
import com.demo.limits.repository.core.ProjectRepository;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;

public class AuditInfoRepositoryTest extends AbstractTransactionalRepositoryTest {
    @Inject
    ProjectRepository projectRepository;

    @Test()
    public void testProjectAuditOnCreateAndUpdate() {
        EProject eProject = new EProject(
                "fakeuuid1",
                "Project1",
                "",
                "fakeuuid2",
                0
               
        );
        eProject = projectRepository.save(eProject);
        EProject projectEntity = projectRepository.findByName("Project1");
        Assert.assertNotNull(projectEntity);
        Assert.assertEquals(projectEntity.getCreateInfo().getCreatedBy(),ContextHolder.get().getSessionContext().getUser().getUuid());
        Assert.assertNotNull(projectEntity.getCreateInfo().getCreatedOn());
        Assert.assertEquals(projectEntity.getUpdateInfo().getLastUpdatedBy(),ContextHolder.get().getSessionContext().getUser().getUuid());
        Assert.assertNotNull(projectEntity.getUpdateInfo().getLastUpdatedOn());
        projectEntity.setName("ABC");
        eProject = projectRepository.save(eProject);
        projectEntity = projectRepository.findByName("ABC");
        Assert.assertEquals(projectEntity.getCreateInfo().getCreatedBy(),ContextHolder.get().getSessionContext().getUser().getUuid());
        Assert.assertNotNull(projectEntity.getCreateInfo().getCreatedOn());
        Assert.assertEquals(projectEntity.getUpdateInfo().getLastUpdatedBy(),ContextHolder.get().getSessionContext().getUser().getUuid());
        Assert.assertNotNull(projectEntity.getUpdateInfo().getLastUpdatedOn());
    }
}
