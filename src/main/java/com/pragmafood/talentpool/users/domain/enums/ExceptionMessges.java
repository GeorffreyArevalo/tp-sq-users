package com.pragmafood.talentpool.users.domain.enums;

public enum ExceptionMessges {
    
    EMAIL_ALREADY_EXISTS("Ya existe un usuario con el correo %s"),
    DOCUMENT_ALREADY_EXISTS("Ya existe un usuario con el documento %s"),
    USER_UNDER_AGE("El usuario debe ser mayor de edad");

    private final String message;

    ExceptionMessges(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
