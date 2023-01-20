package com.kostserver.repository;

import com.kostserver.model.entity.KostRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KostRuleRepo extends JpaRepository<KostRule,Long> {
}
