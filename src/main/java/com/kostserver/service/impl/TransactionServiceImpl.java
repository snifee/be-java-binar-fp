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
    public Transaction booking(BookingDto request, String email) throws Exception{
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

    @Override
    public List<Map<String,Object>> getUserTransactions(String email) throws Exception {

        Optional<Account> account = accountRepository.findByEmail(email);

        if (account.isEmpty()){
            throw new IllegalStateException("account not found");
        }

        List<Transaction> transactionList = transactionRepo.getAllTransactionAccount(account.get().getId());

        List<Map<String,Object>> data = new ArrayList<>();

        transactionList.forEach(t ->{
            Map<String,Object> transaction = new LinkedHashMap<>();

            transaction.put("id",t.getId());
            transaction.put("name",t.getRoomKost().getKost().getKostName());
            transaction.put("status",t.getStatus());
            transaction.put("label",t.getRoomKost().getLabel());
            if (!t.getRoomKost().getImageUrl().isEmpty()){
                transaction.put("thumbnail",t.getRoomKost().getImageUrl().get(0));
            }else {
                transaction.put("thumbnail",null);
            }
            transaction.put("address",t.getRoomKost().getKost().getAddress());
            transaction.put("type",t.getRoomKost().getKost().getKostType());

            if (!t.getRoomKost().getRating().isEmpty()){
                RoomKost room = t.getRoomKost();
                Integer totalRating = room.getRating().stream()
                        .map(r -> r.getRating())
                        .reduce(0,Integer::sum);

                Double avgRating = (double) (totalRating/room.getRating().size());

                transaction.put("rating",avgRating);
            }

            transaction.put("rating",null);

            data.add(transaction);
        });

        return data;
    }

    @Override
    public List<Map<String, Object>> getOwnerTransactions(String email) throws Exception {
        Optional<Account> account = accountRepository.findByEmail(email);

        if (account.isEmpty()){
            throw new IllegalStateException("account not found");
        }

        List<Transaction> transactionList = transactionRepo.getAllTransactionOwner(account.get().getId());

        List<Map<String,Object>> data = new ArrayList<>();

        transactionList.forEach(t ->{
            Map<String,Object> transaction = new LinkedHashMap<>();

            transaction.put("id",t.getId());
            transaction.put("name",t.getAccount().getUserProfile().getFullname());
            String accountCreatedDate = null;
            if (t.getAccount().getCreatedDate()!=null){
                accountCreatedDate = dateFormat.format(t.getAccount().getCreatedDate());
            }
            transaction.put("account_created_date",accountCreatedDate);
            transaction.put("room_name",t.getRoomKost().getName());
            transaction.put("room_label",t.getRoomKost().getLabel());
            if (!t.getRoomKost().getImageUrl().isEmpty()){
                transaction.put("room_thumbnail",t.getRoomKost().getImageUrl().get(0));
            }else {
                transaction.put("room_thumbnail",null);
            }
            transaction.put("room_address",t.getRoomKost().getKost().getAddress());
            transaction.put("room_type",t.getRoomKost().getKost().getKostType());
            transaction.put("price",t.getPrice());

            String transactionCreatedDate = null;
            if (t.getAccount().getCreatedDate()!=null){
                transactionCreatedDate = dateFormat.format(t.getCreatedDate());
            }
            transaction.put("created_date",transactionCreatedDate);

            data.add(transaction);
        });

        return data;
    }
}
