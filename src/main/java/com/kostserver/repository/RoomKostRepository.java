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

    @Query(value = "SELECT rk " + "FROM RoomKost rk " + "join Kost k " + "on rk.kost = k.id " +
            "join RoomFacilityRepository rf " + "on rk.roomFacilitiesId = rf.id " +
            "join BathroomFacility bf " + "on rk.bathroomFacilitiesId = bf.id " +
            "join BedroomFacility df " + "on rk.bedroomFacilitiesId = df.id " +
            "join AdditionalRoomFacility af " + "on rk.additionalRoomFacilities = af.id" +
            "WHERE rk.id =: id", nativeQuery = true)
    RoomKost getRoomDetailsById(@Param("id") Long id);

    @Query(value = "SELECT id, name, phone, createdDate " + "FROM Account " + "WHERE Account.id =: RoomKost.owner", nativeQuery = true)
//    @Query(value = "SELECT rk " + "FROM RoomKost " + "join Account a " + "on rk.owner = a.id " + "WHERE rk.id =: id ")
    RoomKost getOwner(@Param("id") Long id, @Param("name") String name, @Param("phone") String phone, @Param("createdDate") Date createdDate);

    @Query(value = "SELECT rk " +
            "FROM RoomKost rk " +
            "join Kost k " +
            "on rk.kost = k.id " +
            "left join Rating r " +
            "on r.roomKost = rk.id " +
            " WHERE (lower(rk.name) LIKE %:keyword%"
            + " OR lower(rk.label) LIKE %:keyword%"
            + " OR lower(k.kostType) LIKE %:keyword%"
            + " OR lower(k.address) LIKE %:keyword%)"
            + " OR lower(k.city) LIKE %:keyword%)"
            + " OR lower(k.province) LIKE %:keyword%)"
            + " OR lower(c.district) LIKE %:keyword%)"
            + " AND (rk.price>=:minPrice and rk.price<=:maxPrice)"
            + " AND (rk.rating >= :rating)", nativeQuery = true)
    Page<RoomKost> search(@Param("keyword") String keyword, Pageable pageable, @Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice, @Param("rating") Integer rating);

}
