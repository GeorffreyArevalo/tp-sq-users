package com.pragmafood.talentpool.users.domain.api;

import com.pragmafood.talentpool.users.domain.model.User;

public interface AuthenticationServicePort {

    User authenticate(String email, String password);
}
