package com.pragmafood.talentpool.users.infrastructure.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragmafood.talentpool.users.infrastructure.exception.model.ExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ExceptionResponse<String> errorResponse = ExceptionResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .message("Unauthorized")
                .details(authException.getMessage())
                .statusCode("401")
                .httpStatus(401)
                .build();

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
