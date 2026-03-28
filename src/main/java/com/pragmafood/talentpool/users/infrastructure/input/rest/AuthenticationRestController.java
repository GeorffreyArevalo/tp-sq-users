package com.pragmafood.talentpool.users.infrastructure.input.rest;

import com.pragmafood.talentpool.users.application.dto.request.LoginRequest;
import com.pragmafood.talentpool.users.application.dto.response.AuthenticationResponse;
import com.pragmafood.talentpool.users.application.handler.auth.AuthenticationHandler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthenticationRestController {

    private final AuthenticationHandler authenticationHandler;

    @Operation(summary = "Authenticate user and return token", description = "Authenticates a user with email and password, returns a JWT or authentication response.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful authentication"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationHandler.login(request));
    }
}
