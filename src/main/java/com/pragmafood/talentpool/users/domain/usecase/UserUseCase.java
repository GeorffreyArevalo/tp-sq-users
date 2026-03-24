package com.pragmafood.talentpool.users.domain.usecase;

import com.pragmafood.talentpool.users.domain.api.UserServicePort;
import com.pragmafood.talentpool.users.domain.constants.BusinessConstants;
import com.pragmafood.talentpool.users.domain.enums.ExceptionMessages;
import com.pragmafood.talentpool.users.domain.exception.DocumentAlreadyExistsException;
import com.pragmafood.talentpool.users.domain.exception.EmailAlreadyExistsException;
import com.pragmafood.talentpool.users.domain.exception.UserNotFoundException;
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
        return validateAndSaveUser(user, Role.OWNER);
    }

    @Override
    public User createEmployee(User user) {
        return validateAndSaveUser(user, Role.EMPLOYEE);
    }

    @Override
    public User createClient(User user) {
        return validateAndSaveUser(user, Role.CLIENT);
    }

    private User validateAndSaveUser(User user, Role role) {
        userPersistencePort.findByEmail(user.getEmail())
                .ifPresent(savedUser -> {
                    throw new EmailAlreadyExistsException(
                            String.format(ExceptionMessages.EMAIL_ALREADY_EXISTS.getMessage(), savedUser.getEmail())
                    );
                });

        userPersistencePort.findByDocumentId(user.getDocumentId())
                .ifPresent(savedUser -> {
                    throw new DocumentAlreadyExistsException(
                            String.format(ExceptionMessages.DOCUMENT_ALREADY_EXISTS.getMessage(), savedUser.getDocumentId())
                    );
                });

        user.setPassword(passwordEncoderPort.encode(user.getPassword()));
        user.setRole(role);

        return userPersistencePort.saveUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userPersistencePort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(ExceptionMessages.USER_NOT_FOUND.getMessage(), id)
                ));
    }

    private void validateAge(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        int age = today.getYear() - birthDate.getYear();
        if (birthDate.plusYears(age).isAfter(today)) {
            age--;
        }
        if (age < BusinessConstants.LEGAL_AGE) {
            throw new UserUnderAgeException(
                String.format(ExceptionMessages.USER_UNDER_AGE.getMessage())
            );
        }
    }
}
