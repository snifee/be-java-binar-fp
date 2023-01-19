package com.kostserver.repository;

import com.kostserver.model.entity.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBankRepo extends JpaRepository<UserBank, Long> {
}
