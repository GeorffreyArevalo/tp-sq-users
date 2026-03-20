package com.pragmafood.talentpool.users.infrastructure.output.jpa.repository;

import com.pragmafood.talentpool.users.infrastructure.output.jpa.entity.UserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmailIgnoreCase(String email);

    Optional<UserEntity> findByDocumentId(String documentId);
}
