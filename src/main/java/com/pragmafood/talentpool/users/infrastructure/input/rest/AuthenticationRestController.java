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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final AuthenticationHandler authenticationHandler;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationHandler.login(request));
    }
}
