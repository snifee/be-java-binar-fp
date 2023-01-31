package com.kostserver.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EnumKostPaymentScheme {
    BULANAN,HARIAN,MINGGUAN;
    @JsonCreator
    public static EnumKostPaymentScheme getTypeFromCode(String value) {

        for (EnumKostPaymentScheme type : EnumKostPaymentScheme.values()) {

            String upper = value.toUpperCase();
            if (upper.equals(type.name())) {

                return type;
            }
        }

        return null;
    }
}
