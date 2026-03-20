package com.pragmafood.talentpool.users.application.dto.response;

import java.time.LocalDate;

public record UserResponse (
    Long id,
    String name,
    String lastName,
    String documentId,
    String phone,
    LocalDate birthDate,
    String email,
    String role
){
}
