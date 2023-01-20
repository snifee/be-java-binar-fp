package com.kostserver.repository;

import com.kostserver.model.entity.KostPaymentScheme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KostPaymentSchemeRepository extends JpaRepository<KostPaymentScheme, Long> {
}