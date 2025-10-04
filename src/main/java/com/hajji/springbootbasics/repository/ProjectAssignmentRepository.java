package com.hajji.springbootbasics.repository;

import com.hajji.springbootbasics.model.ProjectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Integer> {
    Optional<ProjectAssignment> findByProject_ProjectIdAndUser_UserId(Integer projectId, Integer userId);
    List<ProjectAssignment> findByProject_ProjectId(Integer projectId);
    void  deleteByProject_ProjectId(Integer projectId);
}
