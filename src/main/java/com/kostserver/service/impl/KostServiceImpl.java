package com.kostserver.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kostserver.dto.AddKostDto;
import com.kostserver.model.EnumKostPaymentScheme;
import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.Kost;
import com.kostserver.model.entity.KostPaymentScheme;
import com.kostserver.repository.AccountRepository;
import com.kostserver.repository.KostPaymentSchemeRepository;
import com.kostserver.repository.KostRepository;
import com.kostserver.repository.KostRuleRepo;
import com.kostserver.service.KostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class KostServiceImpl implements KostService {

    @Autowired
    private  KostRepository kostRepository;
    @Autowired
    private KostPaymentSchemeRepository kostPaymentSchemeRepository;

    @Autowired
    private KostRuleRepo kostRuleRepo;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Cloudinary cloudinary;


    @Override
    public Map saveKost(AddKostDto request) throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> account = accountRepository.findByEmail(email);

        Kost kost = new Kost();

        kost.setOwner(account.get());
        kost.setKostName(request.getName());
        kost.setKostType(request.getType());
        kost.setDescription(request.getDescription());
        kost.setLongitude(request.getLocation().getLongitude());
        kost.setLatitude(request.getLocation().getLatitude());
        kost.setAddress(request.getLocation().getAddress());
        kost.setDistrict(request.getLocation().getDistrict());
        kost.setAddressNote(request.getLocation().getNote());
        kost.setAdditionalKostRule(request.getAdditional_rule());
        kost.setCity(request.getLocation().getCity());
        kost.setProvince(request.getLocation().getProvince());

        if (request.getPayment_scheme() != null){
            request.getPayment_scheme().forEach((scheme)->{
                kost.getKostPaymentScheme().add(kostPaymentSchemeRepository.getOne(scheme.getId()));
            });
        }

        if (request.getRules()!=null){
            request.getRules().forEach(rule ->{
                kost.getKostRule().add(kostRuleRepo.getOne(rule.getId()));
            });
        }

        log.info(request.getName());

        if (request.getImage_1() != null){
            Map img1 = cloudinary.uploader().upload(request.getImage_1().getBytes(), ObjectUtils.emptyMap());
            kost.setFrontPhotoUrl(String.valueOf(img1.get("url")));
            log.info(String.valueOf(img1.get("url")));
        }

        if (request.getImage_2() != null){
            Map img2 = cloudinary.uploader().upload(request.getImage_2().getBytes(), ObjectUtils.emptyMap());
            kost.setBackPhotoUrl(String.valueOf(img2.get("url")));
        }

        kostRepository.save(kost);

        account.get().getKosts().add(kost);
        accountRepository.save(account.get());

        Map<String,Object> response = new LinkedHashMap<>();

        response.put("status", HttpStatus.OK);
        response.put("message","kost saved");

        return response;
    }
}
