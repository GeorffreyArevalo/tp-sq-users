package com.pragmafood.talentpool.users.domain.enums;

public enum StatusCodeException {
    
    USER_ALREADY_EXISTS("40-UEX"),
    USER_NOT_FOUND("44-UNF"),
    INVALID_PASSWORD("40-IPW"),
    USER_UNDER_AGE("40-UUA"),
    INVALID_CREDENTIALS("41-ICR");

    private final String code;

    StatusCodeException(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
