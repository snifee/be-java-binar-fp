package com.kostserver.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kostserver.dto.request.UpdateBankAccountDto;
import com.kostserver.dto.request.UpdateUserProfileDto;
import com.kostserver.dto.request.UserVerificationDto;
import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.UserBank;
import com.kostserver.model.entity.UserProfile;
import com.kostserver.model.entity.UserValidation;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.UserBankRepo;
import com.kostserver.repository.UserProfileRepository;
import com.kostserver.repository.test.UserValidationRepo;
import com.kostserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserBankRepo userBankRepo;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserValidationRepo userValidationRepo;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private Cloudinary cloudinary;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    @Transactional
    public Map updateUserBank(UpdateBankAccountDto request) throws Exception{

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Account> account = accountRepository.findByEmail(email);

        UserBank userBank = account.get().getUserBank();

        if (request.getBank_name()!=null){
            userBank.setBankName(request.getBank_name());
        }

        if (request.getAccount_number()!=null){
            userBank.setAccountNumber(request.getAccount_number());
        }

        if (request.getAccount_name()!=null){
            userBank.setAccountName(request.getAccount_name());
        }

        userBankRepo.save(userBank);

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", HttpStatus.OK);
        response.put("message","user bank updated");

        return response;
    }

    @Override
    @Transactional
    public Map updateUserVerification(UserVerificationDto request) throws Exception {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Account> account = accountRepository.findByEmail(email);

        UserValidation userValidation = account.get().getUserValidation();

        if (!account.get().getEmail().equals(request.getEmail())){
            Optional<Account> emailHasUsed = accountRepository.findByEmail(request.getEmail());
            if (emailHasUsed.isPresent()){
                throw  new IllegalStateException("Email has used by another account");
            }

            account.get().setEmail(request.getEmail());
        }

        if (request.getPhoto()!=null){
            String imageType = request.getPhoto().getContentType();

            if (!(imageType.equals("image/jpeg")||imageType.equals("image/png"))){
                throw  new IllegalStateException("must use jpg or png image");
            }

            if (request.getPhoto().getSize()>20000000){
                throw  new IllegalStateException("image size too large");
            }

            Map img = cloudinary.uploader().upload(request.getPhoto().getBytes(), ObjectUtils.emptyMap());
            userValidation.setIdCardUrl(String.valueOf(img.get("url")));
        }

        if (request.getPhone()!=null){
            account.get().setPhone(request.getPhone());
        }

        if (request.getType()!=null){
            userValidation.setType(request.getType());
        }

        userValidationRepo.save(userValidation);

        Map<String,Object > response = new LinkedHashMap<>();

        response.put("status",HttpStatus.OK);
        response.put("message","user verification updated");

        return response;
    }

    @Override
    public Map getCurrentUser() throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Account> account = accountRepository.findByEmail(email);

        UserBank userBank = account.get().getUserBank();
        UserValidation userValidation = account.get().getUserValidation();
        UserProfile userProfile = account.get().getUserProfile();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status",HttpStatus.OK);
        response.put("message","current user");


        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id",account.get().getId());
        data.put("email",account.get().getEmail());
        data.put("phone",account.get().getPhone());
        data.put("role",account.get().getRoles().iterator().next().getName());
        data.put("verified",account.get().getVerified());
        data.put("fullname",userProfile.getFullname());
        data.put("birthdate",simpleDateFormat.format(userProfile.getBirthDate()));
        data.put("gender",userProfile.getGender());
        data.put("occupation",userProfile.getOccupation());
        data.put("photo",userProfile.getPhotoUrl());

        Map<String, Object> verification = new LinkedHashMap<>();
        verification.put("type",userValidation.getType());
        verification.put("photo",userValidation.getIdCardUrl());

        Map<String, Object> bank = new LinkedHashMap<>();
        bank.put("bank_name", userBank.getBankName());
        bank.put("account_number", userBank.getAccountNumber());
        bank.put("account_name", userBank.getAccountName());

        data.put("verification",verification);
        data.put("bank",bank);

        response.put("data",data);

        return response;
    }

    @Override
    public Map updateUserProfile(UpdateUserProfileDto request) throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Account> account = accountRepository.findByEmail(email);

        UserProfile userProfile = account.get().getUserProfile();

        if (request.getFullname()!=null){
            userProfile.setFullname(request.getFullname());
        }

        if (request.getOccupation()!=null){
            userProfile.setOccupation(request.getOccupation());
        }

        if (request.getGender()!=null){
            userProfile.setGender(request.getGender());
        }

        if (request.getBirthdate()!=null){
            userProfile.setBirthDate(simpleDateFormat.parse(request.getBirthdate()));
        }

        if (request.getPhoto()!=null){
            if (!request.getPhoto().getContentType().equals("image/jpeg")){

                throw new IllegalStateException("photo must jpeg format");
            }

            if (request.getPhoto().getSize()>200000000){
                throw new IllegalStateException("photo size to large");
            }

            Map img = cloudinary.uploader().upload(request.getPhoto().getBytes(),ObjectUtils.emptyMap());
            userProfile.setPhotoUrl(String.valueOf(img.get("url")));
        }

        userProfileRepository.save(userProfile);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status",HttpStatus.OK);
        response.put("message", "profile success updated");

        return response;
    }
}
