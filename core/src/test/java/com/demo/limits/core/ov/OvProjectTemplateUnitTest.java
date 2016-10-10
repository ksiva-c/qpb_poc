package com.demo.limits.core.ov;

import com.demo.limits.core.api.ProjectOperations;
import com.demo.limits.core.config.CoreConfig;
import com.demo.limits.core.config.TestCoreConfig;
import com.demo.limits.core.exception.*;
import com.demo.limits.common.config.CommonConfig;
import com.demo.limits.common.context.ApiContext;
import com.demo.limits.common.context.ContextHolder;
import com.demo.limits.common.identity.IdmUser;
import com.demo.limits.common.validation.ValidationException;
import com.demo.limits.core.config.CoreConfig;
import com.demo.limits.domain.core.EProject;
import com.demo.limits.idm.config.IDMConfig;
import com.demo.limits.model.core.Project;
import com.demo.limits.repository.core.ProjectRepository;
import com.demo.limits.repository.config.HikariCPDataSourceConfig;
import com.demo.limits.repository.config.RepositoryConfig;
import com.demo.limits.repository.exceptions.EntityDuplicateException;
import com.demo.limits.servicessupport.config.ServicesSupportConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by lnv.
 */
@ContextConfiguration(classes = {
        CoreConfig.class,
        CommonConfig.class,
        HikariCPDataSourceConfig.class,
        RepositoryConfig.class,
        CoreConfig.class,
        ServicesSupportConfig.class,
        TestCoreConfig.class,
        IDMConfig.class
})
public class OvProjectTemplateUnitTest extends AbstractTestNGSpringContextTests {
    @Inject
    @Named("ovProjectTemplate")
    ProjectOperations projectOperations;

    @Inject
    ProjectRepository projectRepository;

    @BeforeClass
    public void setUp() {
        ApiContext.RequestContext requestContext = new ApiContext.RequestContext();
        requestContext.setUserId(1L);
        ApiContext.SessionContext sessionContext = new ApiContext.SessionContext();
        sessionContext.setUser(new IdmUser(1L,"UUID-1","Test User"));
        ApiContext apiContext = new ApiContext(requestContext,sessionContext);
        ContextHolder.set(apiContext);
    }


    @Test(expectedExceptions = CollectionNotFoundException.class)
    public void testGetProjectNotFound() {
        Project project = projectOperations.getProjectByUuid("1");
    }

    @Test()
    public void testListAllProject() {
        List<Project> p1 = projectOperations.projects();
        Assert.assertNotNull(p1);
        int size = p1.size();
        Project project = new Project("My Project", "My Description", null,  false);
        Project p3 = projectOperations.create(project);
        p1 = projectOperations.projects();
        Assert.assertTrue(p1.size() == size + 1);
        Project project4 = new Project("My Project2", "My Description2", null,  false);
        Project p4 = projectOperations.create(project4);
        p1 = projectOperations.projects();
        Assert.assertTrue(p1.size() == size + 2);
    }

    @Test()
     public void testCreateProject() {
        Project project = new Project("My Project3", "My Description3", null,  false);
        Project p1 = projectOperations.create(project);
        Assert.assertNotNull(p1);
        Project p2 = projectOperations.getProjectByUuid(p1.getUuid());
        Assert.assertNotNull(p2);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void testCreateProjectWithoutDescription() {
        Project project = new Project("My Project3", "My Description3", null,  false);
        project.setDescription(null);
        Project p1 = projectOperations.create(project);
        Assert.assertNotNull(p1);
        Project p2 = projectOperations.getProjectByUuid(p1.getUuid());
        Assert.assertNotNull(p2);
    }


    @Test(expectedExceptions = ValidationException.class)
    public void testCreateProjectWithNullName() {
        Project project = new Project("My Project3", "My Description3", null,  false);
        project.setName(null);
        Project p1 = projectOperations.create(project);
    }

    @Test(expectedExceptions = CollectionValidationException.class)
    public void testCreateProjectWithParentUuid() {
        Project project = new Project("My Project3", "My Description3", "invalidparentUuid",  false);
        Project p1 = projectOperations.create(project);
    }

    @Test()
     public void testCreateProjectWithEmptyDescription() {
        Project project = new Project("My Project3", "", null,  false);
        Project p1 = projectOperations.create(project);
    }

    @Test(expectedExceptions = CollectionValidationException.class)
    public void testCreateProjectWithNullDescription() {
        Project project = new Project("My Project3", null, null,  false);
        Project p1 = projectOperations.create(project);
    }


    @Test(expectedExceptions = CollectionValidationException.class)
    public void testCreateProjectWithSpecialCharactersInName() {
        Project project = new Project("*My Project ><", "My Description3", null,  false);
        Project p1 = projectOperations.create(project);
    }

    @Test()
    public void testCreateProjectThrowsConstraintViolationException() {
        Project project = new Project("*My Project ><", "My Description3", null,  false);
        try {
            Project p1 = projectOperations.create(project);
        }catch(CollectionValidationException cve){
            Assert.assertTrue(cve.getViolations().size() > 0);
        }
    }

    @Test(expectedExceptions = ValidationException.class)
    public void testCreateProjectWithInvalidLengthInName() {
        Project project = new Project("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg" +
                "ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg" +
                "lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll"
                , "My Description3", null,  false);
        Project p1 = projectOperations.create(project);
    }

    @Test(expectedExceptions = CollectionValidationException.class)
    public void testCreateDuplicateProject() {
        Project project = new Project("My Project4", "My Description4", null,  false);
        Project p1 = projectOperations.create(project);
        Project p2 = projectOperations.getProjectByUuid(p1.getUuid());
        Project p3 = projectOperations.create(p2);
    }


    @Test
    public void testUpdateProject() {
        Project project = new Project("My Project34", "My Description3", null,  false);
        Project p1 = projectOperations.create(project);
        Project p2 = projectOperations.getProjectByUuid(p1.getUuid());
        Assert.assertNotNull(p2);
        p2.setName("updated project name");
        Project p4 = projectOperations.update(p2);
        Assert.assertTrue("updated project name" == p4.getName());
    }

    @Test(expectedExceptions = ValidationException.class)
    public void testUpdateProjectWithSpecialCharInName() {
        Project project = new Project("My Project34", "My Description3", null,  false);
        Project p1 = projectOperations.create(project);
        Project p2 = projectOperations.getProjectByUuid(p1.getUuid());
        Assert.assertNotNull(p2);
        p2.setName("%#@updated project name*(&^&");
        Project p4 = projectOperations.update(p2);
    }

    @Test(expectedExceptions = CollectionNotFoundException.class)
    public void testUpdateProjectWithInvalidUuid() {
        Project project = new Project("My Project35", "My Description3", null,  false);
        Project p1 = projectOperations.create(project);
        Project p2 = projectOperations.getProjectByUuid(p1.getUuid());
        Assert.assertNotNull(p2);
        p2.setName("updated project name");
        p2.setUuid("12");
        Project p4 = projectOperations.update(p2);
        Assert.assertTrue("updated project name" == p4.getName());
    }

    @Test(expectedExceptions = ValidationException.class)
    public void testUpdateProjectWithNullUuid() {
        Project project = new Project("My Project36", "My Description3", null,  false);
        Project p1 = projectOperations.create(project);
        Project p2 = projectOperations.getProjectByUuid(p1.getUuid());
        Assert.assertNotNull(p2);
        p2.setUuid(null);
        Project p4 = projectOperations.update(p2);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void testUpdateProjectWithNullName() {
        Project project = new Project("My Project37", "My Description3", null,  false);
        Project p1 = projectOperations.create(project);
        Project p2 = projectOperations.getProjectByUuid(p1.getUuid());
        Assert.assertNotNull(p2);
        p2.setName(null);
        Project p4 = projectOperations.update(p2);
    }

}
