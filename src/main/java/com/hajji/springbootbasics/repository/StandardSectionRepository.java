package com.hajji.springbootbasics.repository;

import com.hajji.springbootbasics.model.StandardSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardSectionRepository extends JpaRepository<StandardSection, Integer> {}
