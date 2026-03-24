package com.pragmafood.talentpool.users.domain.exception;

import com.pragmafood.talentpool.users.domain.enums.StatusCodeException;

public class InvalidCredentialsException extends UserBusinessException {

    public InvalidCredentialsException(String message) {
        super(message, StatusCodeException.INVALID_CREDENTIALS, 401);
    }
}
