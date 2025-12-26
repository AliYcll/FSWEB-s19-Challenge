package com.workintech.twitter.dto.responses;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Data
public class ErrorResponse {
    private String message;
    private OffsetDateTime timestamp;
    private int status;
}
