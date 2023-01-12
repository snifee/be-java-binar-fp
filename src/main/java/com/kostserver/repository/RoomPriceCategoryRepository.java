package com.kostserver.repository;

import com.kostserver.model.entity.RoomPriceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomPriceCategoryRepository extends JpaRepository<RoomPriceCategory, Long> {

    public Optional<RoomPriceCategory> findByPriceCategory(String priceCategory);


}
