package com.pragmafood.talentpool.users.infrastructure.exception.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ExceptionResponse {
    
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private String statusCode;
    private int httpStatus;

}
