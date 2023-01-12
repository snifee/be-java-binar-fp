package com.kostserver.repository;

import com.kostserver.model.entity.KostLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KostLocationRepository extends JpaRepository<KostLocation, Long> {
}
