package com.kostserver.repository;

import com.kostserver.model.entity.KostThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KostThumbnailRepository extends JpaRepository<KostThumbnail, Long> {
}
