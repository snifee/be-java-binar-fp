package com.kostserver.service.impl;

import com.kostserver.dto.request.BookingDto;
import com.kostserver.model.EnumKostPaymentScheme;
import com.kostserver.model.EnumTransactionStatus;
import com.kostserver.model.entity.*;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.AdditionalRoomFacilityRepo;
import com.kostserver.repository.RoomKostRepository;
import com.kostserver.repository.TransactionRepo;
import com.kostserver.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private RoomKostRepository roomKostRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AdditionalRoomFacilityRepo addionsFacilityRepo;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Transaction booking(BookingDto request) throws Exception{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> account = accountRepository.findByEmail(email);

        if (account.isEmpty()){
            throw new IllegalStateException("Usernot found");
        }

        Optional<RoomKost> room = roomKostRepository.findById(request.getRoom_id());
        if (room.isEmpty()){
            throw new IllegalStateException("Room not available!");
        }


        Transaction transaction = new Transaction();

        transaction.setAccount(account.get());
        transaction.setTransactionDate(new Date());
        transaction.setPaymentScheme(EnumKostPaymentScheme.getTypeFromCode(request.getPayment_scheme()));
        transaction.setStatus(EnumTransactionStatus.PENDING);
        transaction.setNumOfPeople(request.getCapacity());
        transaction.setRoomKost(room.get());
        transaction.setPrice(room.get().getPrice());


        if (request.getAddons_facilities() != null) {
            request.getAddons_facilities()
                    .forEach(f->{
                        Optional<AdditionalRoomFacility> facility = addionsFacilityRepo.findById(f.getId());

                        facility.ifPresent(additionalRoomFacility -> transaction.getAddonsFacilities().add(additionalRoomFacility));
                    });

            Double totalPrice = request.getAddons_facilities().stream()
                    .map(f -> {
                        Optional<AdditionalRoomFacility> af = addionsFacilityRepo.findById(f.getId());

                        if (af.isPresent()){
                            return af.get().getPrice();
                        }

                        return 0.0;
                    })
                    .reduce(0.0,Double::sum);

            transaction.setAddonsFacilitiesPrice(totalPrice);
        }

        EnumKostPaymentScheme paymentScheme = EnumKostPaymentScheme.getTypeFromCode(request.getPayment_scheme());

        if (request.getStart_date()!=null){
            Date startRent = dateFormat.parse(request.getStart_date());
            log.info(startRent.toString());
            transaction.setStartRent(startRent);

            LocalDate localDate = startRent.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Date endRentDate = null;
            if (paymentScheme == EnumKostPaymentScheme.BULANAN){

                localDate = localDate.plusDays(30);
                endRentDate = Date.from(localDate.atStartOfDay(ZoneId.of("Asia/Jakarta")).toInstant());

            }else if (paymentScheme == EnumKostPaymentScheme.HARIAN){
                localDate = localDate.plusDays(1);
                endRentDate = Date.from(localDate.atStartOfDay(ZoneId.of("Asia/Jakarta")).toInstant());

            }else if (paymentScheme == EnumKostPaymentScheme.MINGGUAN){
                localDate = localDate.plusDays(7);
                endRentDate = Date.from(localDate.atStartOfDay(ZoneId.of("Asia/Jakarta")).toInstant());

            }

            transaction.setEndRent(endRentDate);
        }

        transactionRepo.save(transaction);

        return transaction;

    }
}
