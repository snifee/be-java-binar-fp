package com.kostserver.repository;

import com.kostserver.model.entity.Kost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface KostRepository extends JpaRepository<Kost,Long> {

    Optional<Kost> findById(Long id);


}
