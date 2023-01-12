package com.kostserver.repository;

import com.kostserver.model.entity.RoomFacility;
import com.kostserver.model.entity.RoomPriceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoomFacilityRepository extends JpaRepository<RoomFacility, Long> {

    public Optional<RoomFacility> findByFacilityName(String facilityName);

}
