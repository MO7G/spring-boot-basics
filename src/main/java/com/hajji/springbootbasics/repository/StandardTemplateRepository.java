package com.hajji.springbootbasics.repository;

import com.hajji.springbootbasics.model.StandardTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StandardTemplateRepository extends JpaRepository<StandardTemplate, Integer> {
    // Add this method to find a template by file ID and section ID
    Optional<StandardTemplate> findByFile_FileIdAndSection_SectionId(Integer fileId, Integer sectionId);
    boolean existsByFile_FileId(Integer fileId);
}
