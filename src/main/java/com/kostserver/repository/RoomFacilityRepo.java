package com.kostserver.repository;

import com.kostserver.model.entity.RoomFacility;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomFacilityRepo extends CrudRepository<RoomFacility,Long> {

    Optional<RoomFacility> findById(Long id);
}
