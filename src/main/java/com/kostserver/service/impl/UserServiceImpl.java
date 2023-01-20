package com.kostserver.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kostserver.dto.UpdateBankAccountDto;
import com.kostserver.dto.UserVerificationDto;
import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.UserBank;
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

import java.io.File;
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

        Optional<Account> emailHasUsed = accountRepository.findByEmail(request.getEmail());

        if (emailHasUsed.isPresent()){
            throw  new IllegalStateException("Email has used by another account");
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
}
