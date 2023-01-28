package com.kostserver.repository;

import com.kostserver.model.entity.KostRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KostRuleRepo extends JpaRepository<KostRule,Long> {
    Optional<KostRule> findById(Long id);
}
