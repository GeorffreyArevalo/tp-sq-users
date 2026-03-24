package com.pragmafood.talentpool.users.application.handler.auth;

import com.pragmafood.talentpool.users.application.dto.request.LoginRequest;
import com.pragmafood.talentpool.users.application.dto.response.AuthenticationResponse;

public interface AuthenticationHandler {

    AuthenticationResponse login(LoginRequest request);
}
