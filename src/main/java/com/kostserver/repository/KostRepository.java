package com.kostserver.repository;

import com.kostserver.model.entity.Kost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KostRepository extends JpaRepository<Kost,Long> {

    Optional<Kost> findById(Long id);
}
