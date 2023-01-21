package com.kostserver.repository;

import com.kostserver.model.entity.KostPaymentScheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KostPaymentSchemeRepository extends JpaRepository<KostPaymentScheme, Long> {

    Optional<KostPaymentScheme> findById(Long id);
}