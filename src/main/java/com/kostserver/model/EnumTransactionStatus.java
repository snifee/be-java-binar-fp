package com.kostserver.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EnumTransactionStatus {
    PENDING,ONPROCCESS,APPROVED,REJECTED,ONGOING,ENDED;

    @JsonCreator
    public static EnumTransactionStatus getTypeFromCode(String value) {

        for (EnumTransactionStatus type : EnumTransactionStatus.values()) {

            String upper = value.toUpperCase();
            if (upper.equals(type.name())) {

                return type;
            }
        }

        return null;
    }
}
