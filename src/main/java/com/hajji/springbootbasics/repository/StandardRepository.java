package com.hajji.springbootbasics.repository;

import com.hajji.springbootbasics.model.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardRepository extends JpaRepository<Standard, Integer> {}
