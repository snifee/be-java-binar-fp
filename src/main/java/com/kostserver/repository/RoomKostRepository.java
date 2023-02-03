package com.kostserver.repository;

import com.kostserver.dto.RatingDto;
import com.kostserver.dto.RoomDto;
import com.kostserver.model.EnumKostType;
import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.Kost;
import com.kostserver.model.entity.RoomKost;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomKostRepository extends JpaRepository<RoomKost, Long> {
        @Query(value = "SELECT new com.kostserver.dto.RoomDto(rm.id, rm.name, rm.label,rm.price,ks.address, ks.kostType, ROUND(avg(rt.rating), 1), 'null')"
                        +
                        "FROM tbl_room rm " +
                        "LEFT JOIN tbl_rating rt on rt.roomKost = rm.id " +
                        "JOIN tbl_kost ks on rm.kost = ks.id " +
                        "WHERE lower(rm.name) LIKE %:keyword% " +
                        "AND (rm.price>=:minPrice AND rm.price <=:maxPrice)" +
                        "AND lower(rm.label) LIKE %:label% " +
                        "AND (ks.kostType = :type OR :type is null)" +
                        "group by rm.id, ks.id")
        List<RoomDto> searchRoom(@Param("keyword") String keyword, @Param("label") String label,
                        @Param("type") EnumKostType type, @Param("minPrice") Double minPrice,
                        @Param("maxPrice") Double maxPrice, Pageable pageable);

        @Query(value = "select rm from tbl_room rm where rm.id=:id")
        RoomKost getRoom(@Param("id") Long id);

        @Query(value = "SELECT new com.kostserver.dto.RatingDto(ac.id, CASE WHEN rt.anonym IS NOT true THEN ac.userProfile.fullname ELSE '****' END,  CASE WHEN rt.anonym IS NOT true THEN ac.userProfile.photoUrl ELSE '****' END, rt.rating, rt.ulasan, ac.userProfile.occupation ,rt.anonym) FROM Account ac "
                        +
                        " JOIN tbl_rating rt on rt.account = ac.id AND rt.roomKost.id = :id")
        List<RatingDto> getRating(@Param("id") Long id, Pageable pageable);
}
