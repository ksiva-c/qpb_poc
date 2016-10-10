package com.demo.limits.repository.core;

import com.demo.limits.domain.core.EProject;
import com.demo.limits.repository.base.AbstractTransactionalRepositoryTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;

/**
 * Created by lnv.
 */
public class ProjectRepositoryTest extends AbstractTransactionalRepositoryTest {

    @Inject
    ProjectRepository projectRepository;

    @Test
    public void test1() {
        EProject project1 = new EProject(
                "fakeuuid1",
                "my proj 1",
                "my desc 1",
                "", 0
                );
        projectRepository.save(project1);
        EProject project2 = new EProject(
                "fakeuuid2",
                "my proj 2",
                "my desc 2",
                "", 0
                );
        projectRepository.save(project2);
        EProject projectEntity = projectRepository.findByName("my proj 1");
        Assert.assertNotNull(projectEntity);
    }
}
