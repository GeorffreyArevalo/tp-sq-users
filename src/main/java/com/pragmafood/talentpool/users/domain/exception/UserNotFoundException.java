package com.pragmafood.talentpool.users.domain.exception;

import com.pragmafood.talentpool.users.domain.enums.StatusCodeException;

public class UserNotFoundException extends UserBusinessException {

    public UserNotFoundException(String message) {
        super(message, StatusCodeException.USER_NOT_FOUND, 404);
    }
}
