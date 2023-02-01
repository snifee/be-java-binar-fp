package com.kostserver.service;

import com.kostserver.dto.request.BookingDto;
import com.kostserver.model.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    Transaction booking(BookingDto request, String email) throws Exception;

    List<Map<String, Object>> getUserTransactions(String email) throws Exception;

    List<Map<String, Object>> getOwnerTransactions(String email) throws Exception;

}