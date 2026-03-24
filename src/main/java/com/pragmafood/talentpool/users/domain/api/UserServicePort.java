package com.pragmafood.talentpool.users.domain.api;

import com.pragmafood.talentpool.users.domain.model.User;

public interface UserServicePort {

    User createOwner(User user);

    User createEmployee(User user);

    User getUserById(Long id);
}
