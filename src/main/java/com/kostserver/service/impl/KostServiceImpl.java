package com.kostserver.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kostserver.dto.request.AddKostDto;
import com.kostserver.dto.request.UpdateKostDto;
import com.kostserver.model.entity.Account;
import com.kostserver.model.entity.Kost;
import com.kostserver.model.entity.KostPaymentScheme;
import com.kostserver.model.entity.KostRule;
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

import java.util.HashSet;
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
    public Map addKost(AddKostDto request) throws Exception {
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
                Optional<KostPaymentScheme> paymentScheme = kostPaymentSchemeRepository.findById(scheme.getId());
                if (paymentScheme.isPresent()){
                    kost.getKostPaymentScheme().add(paymentScheme.get());
                }
            });
        }

        if (request.getRules()!=null){
            request.getRules().forEach(rule ->{
                Optional<KostRule> rule1 = kostRuleRepo.findById(rule.getId());
                if (rule1.isPresent()){
                    kost.getKostRule().add(rule1.get());
                }
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

    @Override
    public Map updateKost(UpdateKostDto request) throws Exception {
        String requestEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Kost> kost = kostRepository.findById(request.getId());
        if (!kost.isPresent()){
            throw new IllegalStateException("kost with id="+request.getId()+" not found");
        }

        Account accountOwner = kost.get().getOwner();

        if (!accountOwner.getEmail().equals(requestEmail)){
            throw new IllegalStateException("you cannot access this kost data");
        }



        if (request.getName()!=null){
            kost.get().setKostName(request.getName());
        }

        if (request.getIndoor_photo()!=null){
            Map img = cloudinary.uploader().upload(request.getIndoor_photo().getBytes(),ObjectUtils.emptyMap());
            kost.get().setIndoorPhotoUrl(String.valueOf(img.get("url")));
        }

        if (request.getOutdoor_photo()!=null){
            Map img = cloudinary.uploader().upload(request.getOutdoor_photo().getBytes(),ObjectUtils.emptyMap());
            kost.get().setOutdoorPhotoUrl(String.valueOf(img.get("url")));
        }

        if (request.getType()!=null){
            kost.get().setKostType(request.getType());
        }

        if (request.getDescription()!=null){
            kost.get().setDescription(request.getDescription());
        }

        if (request.getLongitude()!=null){
            kost.get().setLatitude(request.getLatitude());
        }

        if (request.getLatitude()!=null){
            kost.get().setLatitude(request.getLatitude());
        }

        if (request.getAddress()!=null){
            kost.get().setAddress(request.getAddress());
        }

        if (request.getProvince()!=null){
            kost.get().setProvince(request.getProvince());
        }

        if (request.getCity()!=null){
            kost.get().setCity(request.getCity());
        }

        if (request.getDistrict()!=null){
            kost.get().setDistrict(request.getDistrict());
        }

        if (request.getAdress_note()!=null){
            kost.get().setAddressNote(request.getAdress_note());
        }

        if (request.getAdditional_rule()!=null){
            kost.get().setAdditionalKostRule(request.getAdditional_rule());
        }

        if (request.getPayment_scheme() != null){
            request.getPayment_scheme().forEach((scheme)->{
                Optional<KostPaymentScheme> paymentScheme = kostPaymentSchemeRepository.findById(scheme.getId());
                if (paymentScheme.isPresent()){
                    kost.get().setKostPaymentScheme(new HashSet<KostPaymentScheme>());
                    kost.get().getKostPaymentScheme().add(paymentScheme.get());
                }
            });
        }

        if (request.getRules()!=null){
            request.getRules().forEach(rule ->{
                Optional<KostRule> rule1 = kostRuleRepo.findById(rule.getId());
                if (rule1.isPresent()){
                    kost.get().setKostRule(new HashSet<KostRule>());
                    kost.get().getKostRule().add(rule1.get());
                }
            });
        }

        kostRepository.save(kost.get());

        Map<String ,Object> response = new LinkedHashMap<>();
        response.put("status",HttpStatus.OK);
        response.put("message","kost with id="+request.getId()+" UPDATED");

        return response;
    }
}
