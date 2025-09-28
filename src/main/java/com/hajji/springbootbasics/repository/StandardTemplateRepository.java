package com.hajji.springbootbasics.repository;

import com.hajji.springbootbasics.model.StandardTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardTemplateRepository extends JpaRepository<StandardTemplate, Integer> {}
