package com.pragmafood.talentpool.users.domain.spi;

public interface PasswordEncoderPort {

    String encode(String rawPassword);
}
