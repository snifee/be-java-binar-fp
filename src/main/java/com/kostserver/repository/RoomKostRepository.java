package com.kostserver.repository;

import com.kostserver.dto.RoomDto;
import com.kostserver.model.EnumKostType;
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
//        @Query(value = "SELECT new RoomDto(rm.id, rm.name, rm.label, rm.price, rm.image_url,ks.address, ks.kostType,avg(rt.rating))"
//                        +
//                        "FROM RoomKost rm " +
//                        "JOIN room_image ri on ri.room_id = rm,id"+
//                        "JOIN Rating rt on rt.roomKost = rm.id " +
//                        "JOIN RoomKost ks on rm.kost = ks.id " +
//                        "WHERE lower(rm.name) LIKE %:keyword% " +
//                        "AND (rm.price>=:minPrice AND rm.price <=:maxPrice)" +
//                        "AND lower(rm.label) LIKE %:label% " +
//                        "AND lower(ks.kostType) LIKE %:type% " +
//                        "group by rm.id, ks.id",nativeQuery = false)
//        List<RoomDto> searchRoom(@Param("keyword") String keyword, @Param("label") String label,
//                        @Param("type") String type, @Param("minPrice") Double minPrice,
//                        @Param("maxPrice") Double maxPrice, Pageable pageable);

        @Query(value = "SELECT rm " +
                "FROM tbl_room rm " +
                "JOIN tbl_kost ks on ks.id = rm.kost " +
                "WHERE lower(rm.name) LIKE %:keyword% " +
                "AND (rm.price>=:minPrice AND rm.price <=:maxPrice)" +
                "AND lower(rm.label) LIKE %:label% " +
                "AND ks.kostType = :type ",nativeQuery = false)
        List<RoomKost> searchRoom(@Param("keyword") String keyword, @Param("label") String label,
                                 @Param("type") EnumKostType type, @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice, Pageable pageable);

}
