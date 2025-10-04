package com.hajji.springbootbasics.repository;


import com.hajji.springbootbasics.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Integer> {
    Optional<ProjectStatus> findByStatusNameIgnoreCase(String statusName);
}
