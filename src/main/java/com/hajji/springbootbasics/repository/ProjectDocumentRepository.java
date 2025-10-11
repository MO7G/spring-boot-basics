package com.hajji.springbootbasics.repository;

import com.hajji.springbootbasics.model.ProjectDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDocumentRepository extends JpaRepository<ProjectDocument, Integer> {
    List<ProjectDocument> findByProject_ProjectId(Integer projectId);
    void deleteByProject_ProjectId(Integer projectId);
    boolean existsByFile_FileId(Integer fileId);
}
