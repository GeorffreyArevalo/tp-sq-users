package com.pragmafood.talentpool.users.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pragmafood.talentpool.users.application.dto.request.CreateOwnerRequest;
import com.pragmafood.talentpool.users.application.dto.response.UserResponse;
import com.pragmafood.talentpool.users.application.handler.user.UserHandler;
import com.pragmafood.talentpool.users.domain.exception.UserUnderAgeException;
import com.pragmafood.talentpool.users.infrastructure.exception.handler.GlobalExceptionHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserHandler userHandler;

    @InjectMocks
    private UserRestController userRestController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createOwner_shouldReturn201WhenRequestIsValid() throws Exception {
        CreateOwnerRequest request = new CreateOwnerRequest(
                "Juan", "Pérez", "123456789", "+573005698325",
                LocalDate.of(2000, 1, 1), "juan@example.com", "password123"
        );

        UserResponse response = new UserResponse(
                1L, "Juan", "Pérez", "123456789", "+573005698325",
                LocalDate.of(2000, 1, 1), "juan@example.com", "PROPIETARIO"
        );

        when(userHandler.createOwner(any(CreateOwnerRequest.class))).thenReturn(response);

        mockMvc.perform(post("/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.rol").value("PROPIETARIO"));
    }

    @Test
    void createOwner_shouldReturn400WhenNameIsBlank() throws Exception {
        CreateOwnerRequest request = new CreateOwnerRequest(
                "", "Pérez", "123456789", "+573005698325",
                LocalDate.of(2000, 1, 1), "juan@example.com", "password123"
        );

        mockMvc.perform(post("/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOwner_shouldReturn400WhenEmailIsInvalid() throws Exception {
        CreateOwnerRequest request = new CreateOwnerRequest(
                "Juan", "Pérez", "123456789", "+573005698325",
                LocalDate.of(2000, 1, 1), "not-an-email", "password123"
        );

        mockMvc.perform(post("/api/v1/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOwner_shouldReturn400WhenDocumentHasLetters() throws Exception {
        CreateOwnerRequest request = new CreateOwnerRequest(
                "Juan", "Pérez", "123abc", "+573005698325",
                LocalDate.of(2000, 1, 1), "juan@example.com", "password123"
        );

        mockMvc.perform(post("/api/v1/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOwner_shouldReturn400WhenPhoneExceeds13Characters() throws Exception {
        CreateOwnerRequest request = new CreateOwnerRequest(
                "Juan", "Pérez", "123456789", "+57300569832599",
                LocalDate.of(2000, 1, 1), "juan@example.com", "password123"
        );

        mockMvc.perform(post("/api/v1/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createOwner_shouldReturn400WhenUserIsUnderage() throws Exception {
        CreateOwnerRequest request = new CreateOwnerRequest(
                "Juan", "Pérez", "123456789", "+573005698325",
                LocalDate.of(2000, 1, 1), "juan@example.com", "password123"
        );

        when(userHandler.createOwner(any(CreateOwnerRequest.class)))
                .thenThrow(new UserUnderAgeException("El usuario debe ser mayor de edad"));

        mockMvc.perform(post("/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("El usuario debe ser mayor de edad"));
    }
}
