package com.kostserver.service.impl;

import com.kostserver.model.entity.Account;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.KostRepository;
import com.kostserver.repository.RoomKostRepository;
import com.kostserver.repository.TransactionRepo;
import com.kostserver.service.OwnerStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class OwnerStatisticServiceImpl implements OwnerStatisticService {

    @Autowired
    private KostRepository kostRepository;

    @Autowired
    private RoomKostRepository roomKostRepository;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public Map<String, Object> ownerStatistic(String email) throws Exception {
        Optional<Account> account = accountRepository.findByEmail(email);

        Map<String, Object> data = new LinkedHashMap<>();

        if (account.isPresent()){
            Integer occupants = transactionRepo.sumOfOccupantsByOwner(account.get().getId());
            Integer emptyRoom = roomKostRepository.sumOfAvailableRoom(account.get().getId());
            Integer bookers = transactionRepo.countBookers(account.get().getId());

            data.put("occupants",occupants);
            data.put("empty_room",emptyRoom);
            data.put("bookers",bookers);
        }else {
            throw new IllegalStateException("no data found");
        }

        return data;
    }
}
