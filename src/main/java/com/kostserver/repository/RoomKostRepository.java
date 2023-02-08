package com.kostserver.repository;

import com.kostserver.dto.ItemRoomDto;
import com.kostserver.dto.RatingDto;
import com.kostserver.model.EnumKostType;
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

    @Query("SELECT new com.kostserver.dto.ItemRoomDto(rm.id, rm.name, rm.label ,rm.price ,ks.address , ks.kostType, avg(rt.rating), 'null') " +
            "FROM tbl_room rm " +
            "LEFT JOIN tbl_rating rt on rt.roomKost = rm.id " +
            "JOIN tbl_kost ks on rm.kost = ks.id " +
            "WHERE rm.kost.id = :kostId " +
            "group by rm.id, ks.id")
    List<ItemRoomDto> getListRoomKostByKostId(@Param("kostId") Long kostId);

    @Query(value = "SELECT new com.kostserver.dto.RatingDto(ac.id, CASE WHEN rt.anonym IS NOT true THEN ac.userProfile.fullname ELSE '****' END,  CASE WHEN rt.anonym IS NOT true THEN ac.userProfile.photoUrl ELSE '****' END, rt.rating, rt.ulasan, ac.userProfile.occupation ,rt.anonym) FROM Account ac "
            +
            " JOIN tbl_rating rt on rt.account = ac.id AND rt.roomKost.id = :id")
    List<RatingDto> getRating(@Param("id") Long id, Pageable pageable);


    @Query("SELECT SUM(rm.availableRoom) FROM tbl_room rm " +
            "JOIN tbl_kost ks on rm.kost = ks.id " +
            "WHERE ks.owner.id = :ownerId " )
    Integer sumOfAvailableRoom(@Param("ownerId") Long ownerId);
}
