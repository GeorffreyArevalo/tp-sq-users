package com.pragmafood.talentpool.users.domain.usecase;

import com.pragmafood.talentpool.users.domain.api.AuthenticationServicePort;
import com.pragmafood.talentpool.users.domain.enums.ExceptionMessages;
import com.pragmafood.talentpool.users.domain.exception.InvalidCredentialsException;
import com.pragmafood.talentpool.users.domain.model.User;
import com.pragmafood.talentpool.users.domain.spi.PasswordEncoderPort;
import com.pragmafood.talentpool.users.domain.spi.UserPersistencePort;

public class AuthenticationUseCase implements AuthenticationServicePort {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;

    public AuthenticationUseCase(UserPersistencePort userPersistencePort,
                                 PasswordEncoderPort passwordEncoderPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public User authenticate(String email, String password) {
        User user = userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException(
                        ExceptionMessages.INVALID_CREDENTIALS.getMessage()
                ));

        if (!passwordEncoderPort.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException(
                    ExceptionMessages.INVALID_CREDENTIALS.getMessage()
            );
        }

        return user;
    }
}
