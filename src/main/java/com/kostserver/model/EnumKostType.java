package com.kostserver.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Locale;

public enum EnumKostType {
    PUTRI, PUTRA, CAMPURAN;

    @JsonCreator
    public static EnumKostType getTypeFromCode(String value) {

        for (EnumKostType type : EnumKostType.values()) {

            String upper = value.toUpperCase();
            if (upper.equals(type.name())) {

                return type;
            }
        }

        return null;
    }
}
