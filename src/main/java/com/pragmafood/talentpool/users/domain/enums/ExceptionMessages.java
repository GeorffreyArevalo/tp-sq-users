package com.pragmafood.talentpool.users.domain.enums;

public enum ExceptionMessages {
    
    EMAIL_ALREADY_EXISTS("Ya existe un usuario con el correo %s"),
    DOCUMENT_ALREADY_EXISTS("Ya existe un usuario con el documento %s"),
    USER_UNDER_AGE("El usuario debe ser mayor de edad"),
    USER_NOT_FOUND("No se encontró un usuario con el id %s");

    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
