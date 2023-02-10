package com.kostserver.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EnumKostPaymentScheme {
    BULANAN(30),HARIAN(1),MINGGUAN(7),PER_6BULAN(180),PER_3BULAN(90),TAHUNAN(365);

    public final Integer days;

    private EnumKostPaymentScheme(Integer days) {
        this.days = days;
    }

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
