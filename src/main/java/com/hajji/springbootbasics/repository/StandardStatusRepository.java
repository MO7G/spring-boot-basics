package com.hajji.springbootbasics.repository;

import com.hajji.springbootbasics.model.StandardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardStatusRepository extends JpaRepository<StandardStatus, Integer> {}
