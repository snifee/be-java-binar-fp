package com.kostserver.repository;

import com.kostserver.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepo extends JpaRepository<Transaction,Long> {

    Optional<Transaction> findById(Long id);
}
