package com.hajji.springbootbasics.repository;

import com.hajji.springbootbasics.model.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Integer> {}
