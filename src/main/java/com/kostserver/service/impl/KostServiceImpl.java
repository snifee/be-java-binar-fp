package com.kostserver.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kostserver.dto.request.AddKostDto;
import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.Kost;
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
        kost.setLongitude(request.getLongitude());
        kost.setLatitude(request.getLatitude());
        kost.setAddress(request.getAddress());
        kost.setDistrict(request.getDistrict());
        kost.setAddressNote(request.getAdress_note());
        kost.setAdditionalKostRule(request.getAdditional_rule());
        kost.setCity(request.getCity());
        kost.setProvince(request.getProvince());

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

        if (request.getIndoor_photo()!= null && request.getIndoor_photo().startsWith("data:image/jpeg;base64,")){
            String imageString = request.getIndoor_photo();
            byte[] imageByte = imageString.getBytes();

            Map imgMap = cloudinary.uploader().upload(imageByte,ObjectUtils.emptyMap());
            kost.setIndoorPhotoUrl(String.valueOf(imgMap.get("url")));
            log.info(String.valueOf(imgMap.get("url")));
        }

        if (request.getOutdoor_photo() != null && request.getOutdoor_photo().startsWith("data:image/jpeg;base64,")){
            String imageString2 = request.getOutdoor_photo();
            byte[] imageByte2 = imageString2.getBytes();

            Map imgMap = cloudinary.uploader().upload(imageByte2, ObjectUtils.emptyMap());
            kost.setOutdoorPhotoUrl(String.valueOf(imgMap.get("url")));
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
