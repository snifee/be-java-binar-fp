package com.kostserver.repository;

import com.kostserver.model.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepo extends JpaRepository<Rating, Long> {


    @Query(value = "SELECT r FROM tbl_rating r WHERE r.roomKost = :id ")
    List<Rating> findAllByRoomKost(@Param("id") Long id);
}
