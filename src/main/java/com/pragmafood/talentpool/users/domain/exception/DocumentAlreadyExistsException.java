package com.pragmafood.talentpool.users.domain.exception;

import com.pragmafood.talentpool.users.domain.enums.StatusCodeException;

public class DocumentAlreadyExistsException extends UserBusinessException {

    public DocumentAlreadyExistsException(String message) {
        super(message, StatusCodeException.USER_ALREADY_EXISTS, 400);
    }
}
