package com.kostserver.repository;

import com.kostserver.model.entity.RoomPriceCategory;
import com.kostserver.model.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    public Optional<RoomType> findByType(String type);

}
