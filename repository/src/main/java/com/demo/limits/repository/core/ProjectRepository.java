package com.demo.limits.repository.core;

import com.demo.limits.domain.core.EProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProjectRepository extends JpaRepository<EProject, Long> {
    public EProject findByName(String name);
    public EProject findByUuid(String uuid);

}