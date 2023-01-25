package com.kostserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kostserver.model.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

}
