package com.demo.limits.repository.core;

import com.demo.limits.domain.core.EClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<EClient, Long> {
    public EClient findByName(String name);

}