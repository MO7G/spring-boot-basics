package com.hajji.springbootbasics.repository;

import com.hajji.springbootbasics.model.DocumentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentStatusRepository extends JpaRepository<DocumentStatus, Integer> {
    Optional<DocumentStatus> findByStatusNameIgnoreCase(String StatusName);
}