package com.kostserver.repository;

import com.kostserver.model.entity.RoomImage;
import com.kostserver.model.entity.RoomPriceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {

}
