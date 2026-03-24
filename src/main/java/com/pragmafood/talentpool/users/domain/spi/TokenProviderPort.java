package com.pragmafood.talentpool.users.domain.spi;

import com.pragmafood.talentpool.users.domain.model.User;

public interface TokenProviderPort {

    String generateToken(User user);
}
