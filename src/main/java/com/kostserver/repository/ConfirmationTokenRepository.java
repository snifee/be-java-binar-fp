package com.kostserver.repository;

import com.kostserver.model.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {
    Optional<ConfirmationToken> findByToken(String token);

    Optional<ConfirmationToken> findById(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE tbl_confirmation_token c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);


    @Query(value = "SELECT ct.token FROM tbl_confirmation_token ct " +
            "WHERE ct.account_id = :id",nativeQuery = true)
    List<String> listTokenByAccountId(@Param("id")Long id);
}
