package com.demo.limits.qa.core;

import com.demo.limits.model.core.Project;
import com.demo.limits.qa.config.*;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lnv.
 */
@ContextConfiguration(classes = {TestConfig.class})
public class ProjectIT extends AbstractTestNGSpringContextTests {
    @Inject
    @Named("restTemplate")
    RestOperations restOperations;

    private final String baseUri = "http://localhost:8080/limits";
    private final String projectUri = baseUri + "/v1/project";
    private final String getProjectByScenarioIdUri = projectUri + "?scenarioId={scenarioId}";
    private final String projectFavoriteUri = baseUri + "/v1/favorite/project";
    private final String deleteProjectFavoriteUri = baseUri + "/v1/favorite/project?uuid={uuid}";

    @DataProvider
    public Object[][] createMasterProject() {
        ResponseEntity<Project[]> response = restOperations.getForEntity(projectUri, Project[].class);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.OK);
        Project[] projects = response.getBody();
        Assert.assertNotNull(projects);
        Assert.assertTrue(projects.length > 0);
        Project masterProject = null;
        for(Project project: projects) {
            String uuid = project.getUuid();
            if(uuid.equals("b47be837de14692f22298ad0d8acf5a5")){
                masterProject = project ;
            }
        }
        return new Object[][]{
                { masterProject}
        };
    }

    @Test()
    public void testGetProjectByScenarioId() {
        Long scenarioId = 1L;
        ResponseEntity<Project[]> response = restOperations.getForEntity(getProjectByScenarioIdUri, Project[].class, scenarioId);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.OK);
        Project[] projects = response.getBody();
        Assert.assertNotNull(projects);
        Assert.assertTrue(projects.length > 0);
        Project masterProject = projects[0];
        Assert.assertEquals("b47be837de14692f22298ad0d8acf5a5", masterProject.getUuid());
    }

    @Test()
    public void testGetProjectByScenarioIdForInvalidScenario() {
        Long scenarioId = 1000L;
        try {
            ResponseEntity<Project[]> response = restOperations.getForEntity(getProjectByScenarioIdUri, Project[].class, scenarioId);
        }catch(HttpClientErrorException ex){
            Assert.assertEquals(ex.getStatusCode(),HttpStatus.NOT_FOUND);
        }

    }

    @Test(dataProvider = "createMasterProject")
    public void testMasterProject(Project project) {

        HttpHeaders httpHeaders = headers();
        HttpEntity<Project> projectHttpEntity = new HttpEntity(project, httpHeaders);

        try {
            restOperations.put(projectUri,project);
            Assert.assertTrue(false,"Test case should have resulted in an exception");
        } catch (HttpClientErrorException exception) {
            Assert.assertEquals(exception.getStatusCode(), HttpStatus.FORBIDDEN);
        }
        catch (Exception exception) {
            Assert.assertTrue(false, "Test case caught an un expected exception");
        }

        // Create a master project again
        try {
            project.setId(null);
            project.setUuid(null);
            restOperations.postForObject(projectUri, project, Project.class);
            Assert.assertTrue(false,"Test case  should have resulted in an exception");
        }
        catch (HttpClientErrorException exception) {
            Assert.assertEquals(exception.getStatusCode(), HttpStatus.CONFLICT);
        }
        catch (Exception exception) {
            Assert.assertTrue(false, "Test case caught an un expected exception");
        }


    }


    @DataProvider
    public Object[][] createProjects() {
        return new Object[][] {
                {new Project("My Project-1", "My Description", null, false)},
                {new Project("My Project-2", "My Description", null, false)},
                {new Project("My Project-3", "My Description", null, false)},
                {new Project("My Project-3", "My Description", null, false)},
                {new Project("My Project-4", "My Description", null, false)},
                {new Project("My Project-5", "My Description", null, false)},
                {new Project("My Project-6", "My Description", null, false)},
                {new Project("My Project-6", "My Description", null, false)}
        };
    }

    @Test(dataProvider = "createProjects")
    public void testCreateProject(Project project) {
        HttpHeaders httpHeaders = headers();
        HttpEntity<Project> projectHttpEntity = new HttpEntity(project, httpHeaders);
        ResponseEntity<Project> response = restOperations.postForEntity(projectUri, projectHttpEntity, Project.class);

        Assert.assertEquals(response.getStatusCode(),HttpStatus.OK);
        Project p1 = response.getBody();
        Assert.assertNotNull(p1);
        Long id = p1.getId();
        Assert.assertNotNull(id);
        String uuid = p1.getUuid();
        Assert.assertNotNull(uuid);
        String actualName = p1.getName();
        String expectedName = project.getName();
        Assert.assertEquals(actualName, expectedName, "Project name was not set correctly");
    }

    @Test(dependsOnMethods = "testCreateProject")
    public void testGetAllProjects() {
        ResponseEntity<Project[]> response = restOperations.getForEntity(projectUri, Project[].class);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.OK);
        Project[] projects = response.getBody();
        Assert.assertNotNull(projects);
        Assert.assertTrue(projects.length > 0);
        for(Project project: projects) {
            Long id = project.getId();
            Assert.assertNotNull(id);
            String uuid = project.getUuid();
            Assert.assertNotNull(uuid);
        }
        // TODO: Need to figure out how we can have test scoped test data, so that we can validate here
    }

    @Test(dependsOnMethods = "testCreateProject")
    public void testCreateProjectFavoriteAndListFavorites() {
        HttpHeaders httpHeaders = headers();
        ResponseEntity<Project[]> response = restOperations.getForEntity(projectUri, Project[].class);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.OK);
        Project[] projects = response.getBody();
        for(Project project: projects) {
            Long id = project.getId();
            Assert.assertNotNull(id);
            String uuid = project.getUuid();
            Assert.assertNotNull(uuid);
            HttpEntity<Project> favoriteHttpEntity2 = new HttpEntity(project, httpHeaders);
            ResponseEntity<Project> response2 = restOperations.postForEntity(projectFavoriteUri, favoriteHttpEntity2, Project.class);
            Assert.assertEquals(response2.getStatusCode(), HttpStatus.NO_CONTENT);
        }
    }

    @Test(dependsOnMethods = "testCreateProjectFavoriteAndListFavorites")
    public void testGetAllProjectFavorites() {
        ResponseEntity<Project[]> response = restOperations.getForEntity(projectFavoriteUri, Project[].class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Project[] projects = response.getBody();
        Assert.assertNotNull(projects);
        Assert.assertTrue(projects.length > 0);
        for (Project project : projects) {
            String uuid = project.getUuid();
            Assert.assertNotNull(uuid);
        }
    }

    @Test()
    public void testUnfavoriteAllFavorites() {
        ResponseEntity<Project[]> response = restOperations.getForEntity(projectFavoriteUri, Project[].class);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.OK);
        Project[] projects = response.getBody();
        for(Project project: projects) {
            project.setId(null);
            project.setDescription(null);
            project.setParentUuid(null);
            project.setIsMaster(null);
            try {
                restOperations.delete(deleteProjectFavoriteUri,project.getUuid());
            }
            catch (Exception exception) {
                Assert.assertTrue(false, "Test case caught an un expected exception");
            }
        }
        ResponseEntity<Project[]> response2 = restOperations.getForEntity(projectFavoriteUri, Project[].class);
        Assert.assertEquals(response2.getStatusCode(), HttpStatus.OK);
        Project[] projects2 = response.getBody();
        Assert.assertNotNull(projects2);
        Assert.assertTrue(projects2.length == 0);
    }

    @Test(dependsOnMethods = "testGetAllProjects")
    public void testUpdateProjectWithNullName() {
        ResponseEntity<Project[]> response = restOperations.getForEntity(projectUri, Project[].class);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.OK);
        Project[] projects = response.getBody();
        for(Project project: projects) {
            Project p1 = new Project(null, "My Description", null, false);
            p1.setId(project.getId());
            p1.setUuid(project.getUuid());
            p1.setDescription(project.getDescription());
            p1.setParentUuid(project.getParentUuid());
            p1.setIsMaster(project.getIsMaster());
            try {
                restOperations.put(projectUri,p1);
                Assert.assertTrue(false,"Test case  should have resulted in an exception");
            } catch (HttpClientErrorException exception) {
                Assert.assertEquals(exception.getStatusCode(), HttpStatus.BAD_REQUEST);
            }
            catch (Exception exception) {
                Assert.assertTrue(false, "Test case caught an un expected exception");
            }
        }
    }

    @Test(dependsOnMethods = "testGetAllProjects")
    public void testUpdateProjectWithNullUuid() {
        ResponseEntity<Project[]> response = restOperations.getForEntity(projectUri, Project[].class);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.OK);
        Project[] projects = response.getBody();
        for(Project project: projects) {
            Project p1 = new Project("My Project", "My Description", null, false);
            p1.setId(project.getId());
            p1.setUuid(null);
            p1.setDescription(project.getDescription());
            p1.setParentUuid(project.getParentUuid());
            p1.setIsMaster(project.getIsMaster());
            try {
                restOperations.put(projectUri,p1);
                Assert.assertTrue(false,"Test case should have resulted in an exception");
            } catch (HttpClientErrorException exception) {
                Assert.assertEquals(exception.getStatusCode(), HttpStatus.BAD_REQUEST);
            }
            catch (Exception exception) {
                Assert.assertTrue(false, "Test case caught an un expected exception");
            }
        }
    }

    @Test(dependsOnMethods = "testGetAllProjects")
    public void testUpdateProjects() {
        Project[] projects = restOperations.getForObject(projectUri, Project[].class);
        for(Project project: projects) {
            if(!project.getIsMaster()) {
                Project p1 = new Project("Update" + project.getName(), "My Description", null, false);
                p1.setId(project.getId());
                p1.setUuid(project.getUuid());
                p1.setDescription(project.getDescription());
                p1.setParentUuid(project.getParentUuid());
                p1.setIsMaster(project.getIsMaster());

                HttpHeaders httpHeaders = headers();
                HttpEntity<Project> projectHttpEntity = new HttpEntity(p1, httpHeaders);
                ResponseEntity<Project> response =
                        restOperations.exchange(projectUri, HttpMethod.PUT, projectHttpEntity, Project.class);
                Assert.assertTrue(HttpStatus.OK.value() == response.getStatusCode().value());
                Project p2 = response.getBody();
                String actualName = p2.getName();
                String expectedName = p1.getName();
                Assert.assertEquals(actualName, expectedName, "Project update failed");
            }
            else {
                try {
                    Project p1 = new Project("Update" + project.getName(), "My Description", null, false);
                    p1.setId(project.getId());
                    p1.setUuid(project.getUuid());
                    p1.setDescription(project.getDescription());
                    p1.setParentUuid(project.getParentUuid());
                    p1.setIsMaster(project.getIsMaster());
                    restOperations.put(projectUri,p1);
                    Assert.assertTrue(false,"Test case should have resulted in an exception");
                } catch (HttpClientErrorException exception) {
                    Assert.assertEquals(exception.getStatusCode(), HttpStatus.FORBIDDEN);
                }
                catch (Exception exception) {
                    Assert.assertTrue(false, "Test case caught an un expected exception");
                }
            }
        }
    }

    @DataProvider
    public Object[][] createProjectsWithPresetUuid() {
        List<Project> projects = new ArrayList<>();
        Project p = new Project("My Project-1", "My Description", null, false);
        p.setUuid("uuid");
        projects.add(p);
        p = new Project("My Project-2", "My Description", null, false);
        p.setUuid("uuid");
        projects.add(p);
        p = new Project("My Project-3", "My Description", null, false);
        p.setUuid("uuid");
        projects.add(p);
        p = new Project("My Project-4", "My Description", null, false);
        p.setUuid("uuid");
        projects.add(p);

        return new Object[][] {
                {projects}
        };
    }

    @Test(dataProvider = "createProjectsWithPresetUuid")
    public void testCreateProjectUuidNotBlank(List<Project> projects) {
        for(Project project: projects) {
            try {
                restOperations.postForObject(projectUri, project, Project.class);
                Assert.assertTrue(false,"Test case should have resulted in an exception");
            } catch (HttpClientErrorException exception) {
                Assert.assertEquals(exception.getStatusCode(),HttpStatus.BAD_REQUEST);
            }
            catch (Exception exception) {
                Assert.assertTrue(false, "Test case caught an un expected exception");
            }
        }
    }

    @DataProvider
    public Object[][] createProjectsWithNullName() {
        return new Object[][] {
                {new Project(null, "My Description", null, false)},
                {new Project(null, "My Description", null, false)},
                {new Project(null, "My Description", null, false)},
                {new Project(null, "My Description", null, false)},
                {new Project(null, "My Description", null, false)},
                {new Project(null, "My Description", null, false)},
                {new Project(null, "My Description", null, false)},
                {new Project(null, "My Description", null, false)}
        };
    }


    @Test(dataProvider = "createProjectsWithNullName")
    public void testCreateProjectWithNameNull(Project project) {
        try {
            restOperations.postForObject(projectUri, project, Project.class);
            Assert.assertTrue(false,"Test case should have resulted in an exception");
        } catch (HttpClientErrorException exception) {
            Assert.assertEquals(exception.getStatusCode(),HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            Assert.assertTrue(false, "Test case caught an un expected exception");
        }
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
