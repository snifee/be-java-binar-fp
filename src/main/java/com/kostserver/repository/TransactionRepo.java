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
    List<Transaction> getAllTransactionFromAccount(@Param("id") Long id);


    @Query("SELECT t FROM Kost k " +
            "JOIN Account a.id = k.owner.id " +
            "JOIN tbl_room r  " +
            "JOIN tbl_transaction t " +
            "WHERE k.owner.id = :id")
    List<Transaction> getAllTransactionFromOwner(@Param("id") Long id);
}
