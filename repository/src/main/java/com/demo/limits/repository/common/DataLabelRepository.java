package com.demo.limits.repository.common;

import com.demo.limits.domain.common.EDataLabel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DataLabelRepository extends JpaRepository<EDataLabel, EDataLabel.PK> {

    @Cacheable(value = "data-labels")
    EDataLabel findOne(EDataLabel.PK id);
}