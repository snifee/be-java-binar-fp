package com.kostserver.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kostserver.dto.request.UpdateBankAccountDto;
import com.kostserver.dto.request.UserVerificationDto;
import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.UserBank;
import com.kostserver.model.entity.UserProfile;
import com.kostserver.model.entity.UserValidation;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.UserBankRepo;
import com.kostserver.repository.test.UserValidationRepo;
import com.kostserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private Cloudinary cloudinary;

    @Override
    public Map updateUserBank(UpdateBankAccountDto request) throws Exception{

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Account> account = accountRepository.findByEmail(email);

        UserBank userBank = account.get().getUserBank();

        userBank.setBankName(request.getBank_name());

        userBank.setAccountName(request.getAccount_name());

        userBank.setAccountNumber(request.getAccount_number());

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

        if (!account.get().getEmail().equals(request.getEmail())){
            Optional<Account> emailHasUsed = accountRepository.findByEmail(request.getEmail());
            if (emailHasUsed.isPresent()){
                throw  new IllegalStateException("Email has used by another account");
            }
        }



        String imageType = request.getPhoto().getContentType();

        log.info(imageType);

        if (!(imageType.equals("image/jpeg")||imageType.equals("image/png"))){
            throw  new IllegalStateException("must use jpg or png image");
        }

        if (request.getPhoto().getSize()>20000000){
            throw  new IllegalStateException("image size too large");
        }

        account.get().setEmail(request.getEmail());

        account.get().setPhone(request.getPhone());

        UserValidation userValidation = account.get().getUserValidation();

        Map img = cloudinary.uploader().upload(request.getPhoto().getBytes(), ObjectUtils.emptyMap());
        userValidation.setIdCardUrl(String.valueOf(img.get("url")));

        userValidation.setType(request.getType());

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
        data.put("birthdate",userProfile.getBirthDate());
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
}
