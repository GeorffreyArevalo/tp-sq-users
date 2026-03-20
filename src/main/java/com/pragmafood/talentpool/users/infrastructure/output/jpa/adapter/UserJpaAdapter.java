package com.pragmafood.talentpool.users.infrastructure.output.jpa.adapter;

import com.pragmafood.talentpool.users.domain.model.User;
import com.pragmafood.talentpool.users.domain.spi.UserPersistencePort;
import com.pragmafood.talentpool.users.infrastructure.output.jpa.entity.UserEntity;
import com.pragmafood.talentpool.users.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.pragmafood.talentpool.users.infrastructure.output.jpa.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements UserPersistencePort {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User saveUser(User user) {
        UserEntity entity = userEntityMapper.toEntity(user);
        UserEntity saved = userRepository.save(entity);
        return userEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByDocumentId(String documentId) {
        return userRepository.findByDocumentId(documentId)
                .map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(userEntityMapper::toDomain);
    }
}
