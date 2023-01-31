package com.kostserver.service;

import com.kostserver.dto.request.BookingDto;
import com.kostserver.model.entity.Transaction;

public interface TransactionService {
    Transaction booking(BookingDto request) throws Exception;
}
