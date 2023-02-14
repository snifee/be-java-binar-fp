package com.kostserver.service.impl;

import com.kostserver.dto.request.BookingDto;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kostserver.dto.request.TransactionPayDto;
import com.kostserver.dto.request.UpdateTransactionStatusDto;
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
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    private Cloudinary cloudinary;

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

        if (room.get().getAvailableRoom()==0){
            throw new IllegalStateException("Room not ready to book, available room 0");
        }


        Transaction transaction = new Transaction();

        transaction.setAccount(account.get());
        transaction.setTransactionDate(new Date());
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

        if (request.getStart_date()!=null && paymentScheme!=null){

            Date startRent = dateFormat.parse(request.getStart_date());
//            log.info(startRent.toString());
            transaction.setStartRent(startRent);
            transaction.setPaymentScheme(paymentScheme);

            LocalDate localDate = startRent.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Date endRentDate = null;

            localDate = localDate.plusDays(paymentScheme.days);
            endRentDate = Date.from(localDate.atStartOfDay(ZoneId.of("Asia/Jakarta")).toInstant());

            transaction.setEndRent(endRentDate);
        }else {
            throw new IllegalStateException("start rent or payment scheme cannot be null");
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
            transaction.put("name",t.getRoomKost().getName());
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

    //test
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
            transaction.put("status",t.getStatus());

            String transactionCreatedDate = null;
            if (t.getAccount().getCreatedDate()!=null){
                transactionCreatedDate = dateFormat.format(t.getCreatedDate());
            }
            transaction.put("created_date",transactionCreatedDate);

            data.add(transaction);
        });

        return data;
    }

    @Override
    public Transaction updateOwnerTransactionsStatus(String email, UpdateTransactionStatusDto request) throws Exception {
        Optional<Transaction> transaction = transactionRepo.findById(request.getId());
        Optional<Account> ownerRequest = accountRepository.findByEmail(email);

        if (transaction.isEmpty()){
            throw new IllegalStateException("transaction not found");
        }

        Transaction updatedTransaction = transaction.get();

        updatedTransaction.setStatus(EnumTransactionStatus.getTypeFromCode(request.getStatus()));

        transactionRepo.save(updatedTransaction);

        String requestStatusLower = request.getStatus().toLowerCase();

        if (requestStatusLower.equals(EnumTransactionStatus.APPROVED.name().toLowerCase())){

            RoomKost roomKost = updatedTransaction.getRoomKost();

            Account roomKostOwner = roomKost.getKost().getOwner();

            if (!roomKostOwner.getEmail().equals(ownerRequest.get().getEmail())){
                throw new AuthorizationServiceException("user not allowed to update this transaction");
            }

            if (roomKost.getAvailableRoom()==0){
                throw new IllegalStateException("available room is 0");
            }

            Integer availableRoom = roomKost.getAvailableRoom();

            Integer newAvailableRoom = availableRoom - 1;

            roomKost.setAvailableRoom(newAvailableRoom);

            roomKostRepository.save(roomKost);
        }



        Map<String,Object> data = new LinkedHashMap<>();
        return updatedTransaction;
    }

    @Override
    public Map<String, Object> getTransactionById(String email,Long id) throws Exception {
        Map<String,Object> data = new LinkedHashMap<>();

        Optional<Account> requestAccount = accountRepository.findByEmail(email);

        Optional<Transaction> transaction = transactionRepo.findById(id);

        Account accountOwner = transaction.get().getRoomKost().getKost().getOwner();

        String tenantEmail = transaction.get().getAccount().getEmail();
        String ownerEmail = accountOwner.getEmail();

        if (!(tenantEmail.equals(email) || ownerEmail.equals(email))){
            throw new IllegalStateException("you not allowed access this transaction");
        }

        if (transaction.isEmpty()){
            throw new IllegalStateException("no transaction with that id found");
        }

        LocalDateTime localDate = transaction.get().getCreatedDate().toInstant().atZone(ZoneId.of("Asia/Jakarta")).toLocalDateTime();
        LocalDateTime tfEndDate = localDate.plusDays(1);

        data.put("id",transaction.get().getId());
        data.put("tf_end_date",tfEndDate);
        data.put("price",transaction.get().getPrice());

        return data;
    }

    @Override
    public Transaction transactionPay(String email, TransactionPayDto request) throws Exception {
        Optional<Account> account = accountRepository.findByEmail(email);

        Optional<Transaction> transaction = transactionRepo.findById(request.getId());

        if (transaction.isEmpty()){
            throw new IllegalStateException("no transaction found");
        }

        if (request.getImage()!=null){
            Map imgUrl = cloudinary.uploader().upload(request.getImage().getBytes(), ObjectUtils.emptyMap());

            transaction.get().setPaymentProof(String.valueOf(imgUrl.get("url")));
            transaction.get().setStatus(EnumTransactionStatus.ONPROCCESS);

            transactionRepo.save(transaction.get());
        }

        return transaction.get();
    }
}
