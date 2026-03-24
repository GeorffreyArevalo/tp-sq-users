package com.pragmafood.talentpool.users.infrastructure.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragmafood.talentpool.users.infrastructure.exception.model.ExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ExceptionResponse<String> errorResponse = ExceptionResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .message("Forbidden")
                .details(accessDeniedException.getMessage())
                .statusCode("403")
                .httpStatus(403)
                .build();

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
