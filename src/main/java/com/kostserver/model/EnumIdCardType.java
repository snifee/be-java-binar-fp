package com.kostserver.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EnumIdCardType {
    PASPORT, KTP, SIM;

    @JsonCreator
    public static EnumIdCardType getTypeFromCode(String value) {

        for (EnumIdCardType type : EnumIdCardType.values()) {

            String upper = value.toUpperCase();
            if (upper.equals(type.name())) {

                return type;
            }
        }

        return null;
    }
}
