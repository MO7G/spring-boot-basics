package com.hajji.springbootbasics.repository;

import com.hajji.springbootbasics.model.DocumentRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRevisionRepository extends JpaRepository<DocumentRevision, Integer> {}
