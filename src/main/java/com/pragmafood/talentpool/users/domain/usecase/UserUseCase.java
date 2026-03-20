package com.pragmafood.talentpool.users.domain.usecase;

import com.pragmafood.talentpool.users.domain.api.UserServicePort;
import com.pragmafood.talentpool.users.domain.constants.BusinessConstants;
import com.pragmafood.talentpool.users.domain.enums.ExceptionMessges;
import com.pragmafood.talentpool.users.domain.exception.DocumentAlreadyExistsException;
import com.pragmafood.talentpool.users.domain.exception.EmailAlreadyExistsException;
import com.pragmafood.talentpool.users.domain.exception.UserUnderAgeException;
import com.pragmafood.talentpool.users.domain.model.Role;
import com.pragmafood.talentpool.users.domain.model.User;
import com.pragmafood.talentpool.users.domain.spi.PasswordEncoderPort;
import com.pragmafood.talentpool.users.domain.spi.UserPersistencePort;

import java.time.LocalDate;

public class UserUseCase implements UserServicePort {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;

    public UserUseCase(UserPersistencePort userPersistencePort, PasswordEncoderPort passwordEncoderPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public User createOwner(User user) {
        validateAge(user.getBirthDate());

        userPersistencePort.findByEmail( user.getEmail() )
        .ifPresent( savedUser -> {
            throw new EmailAlreadyExistsException(
                String.format(ExceptionMessges.EMAIL_ALREADY_EXISTS.getMessage(), savedUser.getEmail())
            );
        });

        userPersistencePort.findByDocumentId( user.getDocumentId() )
        .ifPresent( savedUser -> {
            throw new DocumentAlreadyExistsException(
                String.format(ExceptionMessges.DOCUMENT_ALREADY_EXISTS.getMessage(), savedUser.getDocumentId())
            );
        });

        user.setPassword(passwordEncoderPort.encode(user.getPassword()));
        user.setRole(Role.OWNER);

        return userPersistencePort.saveUser(user);
    }

    private void validateAge(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - birthDate.getYear();
        if (birthDate.plusYears(age).isAfter(today)) {
            age--;
        }
        if (age < BusinessConstants.LEGAL_AGE) {
            throw new UserUnderAgeException(
                String.format(ExceptionMessges.USER_UNDER_AGE.getMessage())
            );
        }
    }
}
