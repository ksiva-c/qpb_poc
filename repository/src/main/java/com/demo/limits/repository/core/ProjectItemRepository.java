package com.demo.limits.repository.core;

import com.demo.limits.domain.core.EProject;
import com.demo.limits.domain.core.ProjectItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lnv.
 */
@Repository
@Transactional
public interface ProjectItemRepository extends JpaRepository<ProjectItem, Long> {


    @Query("select projectItem from ProjectItem projectItem where projectItem.id.collectionId=?1 and projectItem.id.itemType='SCENARIO' ")
    public List<ProjectItem> findAllByCollectionId(Long collectionId);

    @Query("select projectItem from EProject project, ProjectItem projectItem where projectItem.id.collectionId=project.id" +
            " and project.uuid =?1 and  projectItem.id.itemType='SCENARIO'")
    public List<ProjectItem> findAllByCollectionUuId(String collectionId);

    @Query("select eProject from EProject eProject, ProjectItem projectItem where projectItem.id.itemRefId=?1 " +
            "and eProject.id = projectItem.id.collectionId and  projectItem.id.itemType='SCENARIO'")
    public List<EProject> findProjectByScenarioId(Long scenarioId);

    @Query("select projectItem from ProjectItem projectItem where projectItem.id.itemRefId=?1 and projectItem.id.itemType='SCENARIO' ")
    public ProjectItem findByScenarioId(Long scenarioId);
}
