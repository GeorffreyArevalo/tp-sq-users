package com.pragmafood.talentpool.users.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public record CreateOwnerRequest(



    @NotBlank(message = "El nombre es obligatorio")
    String name,

    @NotBlank(message = "El apellido es obligatorio")
    String lastName,

    @NotBlank(message = "El documento de identidad es obligatorio")
    @Pattern(regexp = "^\\d+$", message = "El documento de identidad debe ser numérico")
    String documentId,

    @NotBlank(message = "El celular es obligatorio")
    @Size(max = 13, message = "El celular debe tener máximo 13 caracteres")
    @Pattern(regexp = "^\\+?\\d+$", message = "El celular solo puede contener dígitos y opcionalmente el símbolo +")
    String phone,

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate birthDate,

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener una estructura válida")
    String email,

    @NotBlank(message = "La clave es obligatoria")
    String password
    ) { }
