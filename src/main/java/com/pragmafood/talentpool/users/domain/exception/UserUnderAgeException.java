package com.pragmafood.talentpool.users.domain.exception;

import com.pragmafood.talentpool.users.domain.enums.StatusCodeException;

public class UserUnderAgeException extends UserBusinessException {

    public UserUnderAgeException(String message) {
        super(message, StatusCodeException.USER_UNDER_AGE, 400);
    }
}
