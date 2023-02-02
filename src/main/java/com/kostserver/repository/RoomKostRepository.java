package com.kostserver.repository;

import com.kostserver.model.entity.RoomKost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface RoomKostRepository extends JpaRepository<RoomKost, Long> {


    Optional<RoomKost> findById(Long id);

    @Query(value = "SELECT rk " + "FROM RoomKost rk " + "join Kost k " + "on rk.kost = k.id " +
            "join RoomFacilityRepository rf " + "on rk.roomFacilitiesId = rf.id " +
            "join BathroomFacility bf " + "on rk.bathroomFacilitiesId = bf.id " +
            "join BedroomFacility df " + "on rk.bedroomFacilitiesId = df.id " +
            "join AdditionalRoomFacility af " + "on rk.additionalRoomFacilities = af.id" +
            "WHERE rk.id =: id", nativeQuery = true)
    RoomKost getRoomDetailsById(@Param("id") Long id);


}
