package com.hajji.springbootbasics.repository;

import com.hajji.springbootbasics.model.DocumentRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRevisionRepository extends JpaRepository<DocumentRevision, Integer> {
    void deleteByProjectDocument_ProjectDocumentId(Integer projectId);
    @Query("SELECT MAX(r.versionNumber) FROM DocumentRevision r WHERE r.projectDocument.projectDocumentId = :projectDocumentId")
    Optional<Integer> findMaxVersionByProjectDocumentId(Integer projectDocumentId);
}
