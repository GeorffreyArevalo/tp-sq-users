package com.pragmafood.talentpool.users.infrastructure.security.adapters;

import com.pragmafood.talentpool.users.domain.spi.PasswordEncoderPort;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BcryptPasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoder encoder;

    @Override
    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
