package com.pragmafood.talentpool.users.domain.exception;

import com.pragmafood.talentpool.users.domain.enums.StatusCodeException;

public class UserBusinessException extends RuntimeException {

    private final int code;
    private final StatusCodeException statusCode;

    public UserBusinessException(String message, StatusCodeException statusCode, int code) {
        super(message);
        this.statusCode = statusCode;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public StatusCodeException getStatusCode() {
        return statusCode;
    }
    
}
