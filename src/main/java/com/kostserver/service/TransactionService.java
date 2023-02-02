package com.kostserver.service;

import com.kostserver.dto.request.BookingDto;
import com.kostserver.dto.request.TransactionPayDto;
import com.kostserver.dto.request.UpdateTransactionStatusDto;
import com.kostserver.model.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    Transaction booking(BookingDto request, String email) throws Exception;

    List<Map<String, Object>> getUserTransactions(String email) throws Exception;

    List<Map<String, Object>> getOwnerTransactions(String email) throws Exception;

    Transaction updateOwnerTransactionsStatus(String email, UpdateTransactionStatusDto request) throws Exception;

    Map<String,Object> getTransactionById(String email, Long id) throws Exception;

    Transaction transactionPay(String email, TransactionPayDto request) throws Exception;

}