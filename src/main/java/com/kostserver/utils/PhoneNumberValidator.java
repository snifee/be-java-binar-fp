package com.kostserver.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.context.annotation.Configuration;

public class PhoneNumberValidator {
    public static boolean isValid(String phone) throws NumberParseException{
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

        Phonenumber.PhoneNumber phoneNumber = null;


        try{
            phoneNumber = phoneNumberUtil.parse(phone,"ID");
            System.out.println("\nType: "+ phoneNumberUtil.getNumberType(phoneNumber));
            return phoneNumberUtil.isValidNumber(phoneNumber);
        }catch (NumberParseException e){
            System.out.println("Unable to parse the given phone number: "+ phone);
            throw new NumberParseException(e.getErrorType(),"Phone Number is Not Valid");
        }


    }
}
