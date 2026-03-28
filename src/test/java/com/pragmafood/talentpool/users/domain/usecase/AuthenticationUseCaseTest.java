package com.pragmafood.talentpool.users.domain.usecase;

import com.pragmafood.talentpool.users.domain.api.AuthenticationServicePort;
import com.pragmafood.talentpool.users.domain.enums.ExceptionMessages;
import com.pragmafood.talentpool.users.domain.exception.InvalidCredentialsException;
import com.pragmafood.talentpool.users.domain.model.User;
import com.pragmafood.talentpool.users.domain.spi.PasswordEncoderPort;
import com.pragmafood.talentpool.users.domain.spi.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationUseCaseTest {
    private UserPersistencePort userPersistencePort;
    private PasswordEncoderPort passwordEncoderPort;
    private AuthenticationServicePort authenticationUseCase;

    @BeforeEach
    void setUp() {
        userPersistencePort = mock(UserPersistencePort.class);
        passwordEncoderPort = mock(PasswordEncoderPort.class);
        authenticationUseCase = new AuthenticationUseCase(userPersistencePort, passwordEncoderPort);
    }

    @Test
    void authenticate_successful() {
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);

        when(userPersistencePort.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoderPort.matches(password, encodedPassword)).thenReturn(true);

        User result = authenticationUseCase.authenticate(email, password);
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void authenticate_invalid_email_throwsException() {
        String email = "notfound@example.com";
        String password = "password";
        when(userPersistencePort.findByEmail(email)).thenReturn(Optional.empty());

        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () ->
                authenticationUseCase.authenticate(email, password)
        );
        assertEquals(ExceptionMessages.INVALID_CREDENTIALS.getMessage(), exception.getMessage());
    }

    @Test
    void authenticate_invalid_password_throwsException() {
        String email = "test@example.com";
        String password = "wrongpassword";
        String encodedPassword = "encodedPassword";
        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);

        when(userPersistencePort.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoderPort.matches(password, encodedPassword)).thenReturn(false);

        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () ->
                authenticationUseCase.authenticate(email, password)
        );
        assertEquals(ExceptionMessages.INVALID_CREDENTIALS.getMessage(), exception.getMessage());
    }
}
