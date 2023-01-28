package com.kostserver.repository;

import com.kostserver.model.entity.AdditionalRoomFacility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdditionalRoomFacilityRepo extends JpaRepository<AdditionalRoomFacility,Long> {

    Optional<AdditionalRoomFacility> findById(Long id);
}
