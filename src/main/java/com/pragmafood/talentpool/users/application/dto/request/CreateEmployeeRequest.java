package com.pragmafood.talentpool.users.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateEmployeeRequest(

    @NotBlank(message = "Name is required")
    String name,

    @NotBlank(message = "Last name is required")
    String lastName,

    @NotBlank(message = "Document ID is required")
    @Pattern(regexp = "^\\d+$", message = "Document ID must be numeric")
    String documentId,

    @NotBlank(message = "Phone number is required")
    @Size(max = 13, message = "Phone number must have at most 13 characters")
    @Pattern(regexp = "^\\+?\\d+$", message = "Phone number can only contain digits and optionally the + symbol")
    String phone,

    @NotBlank(message = "Email is required")
    @Email(message = "Email must have a valid format")
    String email,

    @NotBlank(message = "Password is required")
    String password
) { }
