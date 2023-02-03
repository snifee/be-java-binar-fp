package com.kostserver.repository;

import com.kostserver.dto.ItemRoomDto;
import com.kostserver.model.EnumKostType;
import com.kostserver.model.entity.Kost;
import com.kostserver.model.entity.RoomKost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
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

    @Query(value = "SELECT new com.kostserver.dto.ItemRoomDto(rm.id, rm.name, rm.label ,rm.price ,ks.address , ks.kostType, avg(rt.rating), 'null') "
            +
            "FROM tbl_room rm " +
            "LEFT JOIN tbl_rating rt on rt.roomKost = rm.id " +
            "JOIN tbl_kost ks on rm.kost = ks.id " +
            "WHERE lower(rm.name) LIKE %:keyword% " +
            "AND (rm.price>=:minPrice AND rm.price <=:maxPrice) " +
            "AND lower(rm.label) LIKE %:label% " +
            "AND (ks.kostType = :type OR :type is null) " +
            "group by rm.id, ks.id")
    List<ItemRoomDto> searchRoom(@Param("keyword") String keyword, @Param("label") String label,
                                 @Param("type") EnumKostType type, @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice, Pageable pageable);


    @Query("SELECT new com.kostserver.dto.ItemRoomDto(rm.id, rm.name, rm.label ,rm.price ,ks.address , ks.kostType, avg(rt.rating), 'null') " +
            "FROM tbl_room rm " +
            "LEFT JOIN tbl_rating rt on rt.roomKost = rm.id " +
            "JOIN tbl_kost ks on rm.kost = ks.id " +
            "WHERE ks.owner.id = :ownerId " +
            "group by rm.id, ks.id")
    List<ItemRoomDto> getListRoomKostByOwner(@Param("ownerId") Long ownerId);

}
