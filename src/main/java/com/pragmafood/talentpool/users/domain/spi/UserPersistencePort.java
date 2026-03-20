package com.pragmafood.talentpool.users.domain.spi;

import java.util.Optional;

import com.pragmafood.talentpool.users.domain.model.User;

public interface UserPersistencePort {

    User saveUser(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByDocumentId(String documentId);

    Optional<User> findById(Long id);
}
