package com.kostserver.repository;

import com.kostserver.model.entity.Kost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KostRepository extends JpaRepository<Kost, Long> {



}
