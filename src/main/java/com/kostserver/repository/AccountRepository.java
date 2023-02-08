package com.kostserver.repository;

import com.kostserver.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    Optional<Account> findByPhone(String phone);

    Boolean existsByEmail(String email);

    Boolean existByPhone(String phone);

    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.verified = TRUE WHERE a.email = ?1")
    int enableVerified(String email);

}
