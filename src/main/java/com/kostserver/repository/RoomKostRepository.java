package com.kostserver.repository;

import com.kostserver.model.entity.RoomKost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomKostRepository extends JpaRepository<RoomKost, Long> {
}
