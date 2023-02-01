package com.kostserver.repository;

import com.kostserver.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepo extends JpaRepository<Transaction,Long> {

    Optional<Transaction> findById(Long id);

    @Query("SELECT t FROM tbl_transaction t " +
            "WHERE t.account.id = :id")
    List<Transaction> getAllTransactionAccount(@Param("id") Long id);


    @Query(value = "SELECT * FROM tbl_transaction t " +
            "JOIN account a ON a.id = t.account_id " +
            "JOIN tbl_room r ON r.id = t.room_kost_id " +
            "JOIN tbl_kost k ON k.id = r.kost_id " +
            "WHERE k.owner_id = ?1",nativeQuery = true)
    List<Transaction> getAllTransactionOwner(Long id);
}
