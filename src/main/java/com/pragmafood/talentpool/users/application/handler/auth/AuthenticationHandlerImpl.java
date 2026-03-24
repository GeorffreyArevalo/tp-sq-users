package com.pragmafood.talentpool.users.application.handler.auth;

import com.pragmafood.talentpool.users.application.dto.request.LoginRequest;
import com.pragmafood.talentpool.users.application.dto.response.AuthenticationResponse;
import com.pragmafood.talentpool.users.domain.api.AuthenticationServicePort;
import com.pragmafood.talentpool.users.domain.model.User;
import com.pragmafood.talentpool.users.domain.spi.TokenProviderPort;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationHandlerImpl implements AuthenticationHandler {

    private final AuthenticationServicePort authenticationServicePort;
    private final TokenProviderPort tokenProviderPort;

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        User user = authenticationServicePort.authenticate(request.email(), request.password());
        String token = tokenProviderPort.generateToken(user);
        return new AuthenticationResponse(token);
    }
}
